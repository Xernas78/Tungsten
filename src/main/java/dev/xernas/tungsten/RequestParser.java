package dev.xernas.tungsten;

import dev.xernas.tungsten.protocol.Header;
import dev.xernas.tungsten.protocol.Method;
import dev.xernas.tungsten.protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private final Method method;
    private final Protocol protocol;
    private final List<Header> headers;
    private final String path;

    public RequestParser(BufferedReader reader) throws IOException {
        // Parse info
        String info = reader.readLine();
        String[] parts = info.split(" ");
        if (parts.length != 3) throw new IOException("Request not supported");
        // Parse method
        this.method = parseMethod(parts[0]);
        if (this.method != Method.GET) throw new IOException("Method not supported");
        // Parse protocol
        this.protocol = Protocol.fromString(parts[2].split("/")[0]);
        if (this.protocol != Protocol.HTTP) throw new IOException("Protocol not supported");
        // Parse headers
        this.headers = parseHeaders(reader);
        //Parse path
        this.path = parsePath(parts[1]);
    }

    private Method parseMethod(String method) throws IOException {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            throw new IOException("Method not supported");
        }
    }

    private List<Header> parseHeaders(BufferedReader reader) throws IOException {
        List<Header> headers = new ArrayList<>();
        String header = reader.readLine();
        Header firstHeader = Header.fromString(header);
        if (firstHeader != null) headers.add(firstHeader);
        while (!header.isEmpty()) {
            header = reader.readLine();
            if (header.isEmpty()) break;
            Header h = Header.fromString(header);
            if (h != null) headers.add(h);
        }
        return headers;
    }

    private String parsePath(String path) {
        if (path.isEmpty()) {
            path = "/index.html";
        }
        if (path.endsWith("/")) {
            path += "index.html";
        } else if (path.endsWith("/index")) {
            path += ".html";
        } else if (path.endsWith(".htm")) {
            path += "l";
        }
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public String getPath() {
        return path;
    }

    public String getFile() {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public String getDirectory() {
        return path.substring(0, path.lastIndexOf("/"));
    }

    public String getExtension() {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
