package dev.xernas.tungsten.io;

import dev.xernas.tungsten.RequestParser;
import dev.xernas.tungsten.Tungsten;
import dev.xernas.tungsten.protocol.Header;
import dev.xernas.tungsten.protocol.Protocol;
import dev.xernas.tungsten.protocol.Status;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Client implements Runnable {

    private final Socket socket;
    private final String directory;
    private final Logger logger;

    public Client(Socket socket, String directory) {
        this.socket = socket;
        this.directory = directory;
        this.logger = Tungsten.getLogger();
    }

    @Override
    public void run() {
        try {
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendResponse(writer, new RequestParser(reader));
            writer.close();
            reader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream writer, RequestParser request) throws IOException {
        Status status = Status.OK;
        List<Header> headers = new ArrayList<>();

        switch (request.getExtension()) {
            case "css":
                headers.add(Header.CONTENT_TYPE_CSS);
                break;
            case "js":
                headers.add(Header.CONTENT_TYPE_JS);
                break;
            case "json":
                headers.add(Header.CONTENT_TYPE_JSON);
                break;
            case "txt":
                headers.add(Header.CONTENT_TYPE_TEXT);
                break;
            case "php":
                headers.add(Header.CONTENT_TYPE_PHP);
                break;
            case "jpeg":
                headers.add(Header.CONTENT_TYPE_JPEG);
                break;
            case "png":
                headers.add(Header.CONTENT_TYPE_PNG);
                break;
            case "gif":
                headers.add(Header.CONTENT_TYPE_GIF);
                break;
            case "svg":
                headers.add(Header.CONTENT_TYPE_SVG);
                break;
            case "ico":
                headers.add(Header.CONTENT_TYPE_ICO);
                break;
            default:
                headers.add(Header.CONTENT_TYPE_HTML);
                break;
        }
        String body = "<h1>Tungsten 404</h1><br><span>Resource not found at " + request.getPath() + "</span>";
        try(InputStream stream = new FileInputStream((directory + request.getPath()).replace("//", "/"))) {
            body = new String(stream.readAllBytes());
            headers.add(Header.CONTENT_LENGTH.setValue(String.valueOf(body.length())));
        }
        catch (FileNotFoundException e) {
            status = Status.NOT_FOUND;
        }

        List<String> lines = new ArrayList<>();
        lines.add(Protocol.HTTP.getProtocol() + " " + status.getCode() + " " + status.getMessage());
        headers.forEach(header -> lines.add(header.getHeader()));
        lines.add("");
        lines.add(body);
        writeLines(writer, lines);
    }

    private void writeLines(DataOutputStream writer, List<String> lines) throws IOException {
        for (String line : lines) {
            writer.writeBytes(line + "\r\n");
        }
        writer.flush();
    }
}
