package dev.xernas.tungsten.io;

import dev.xernas.tungsten.Tungsten;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class Server {

    private boolean running = false;
    private final Logger logger;
    private final int port;

    public Server(int port) {
        logger = Tungsten.getLogger();
        this.port = port;
    }

    public void start() throws IOException {
        running = true;
        logger.info("Server started on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                Thread connection = new Thread(new Client(serverSocket.accept()));
                connection.start();
            }
        };
    }

    public void stop() {
        running = false;
        logger.info("Server stopped");
    }

}
