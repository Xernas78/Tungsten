package dev.xernas.tungsten;

import dev.xernas.tungsten.io.Server;

import java.io.IOException;
import java.util.logging.Logger;

public class Tungsten {

    private static final int DEFAULT_PORT = 4242;
    private static final Logger LOGGER = Logger.getLogger(Tungsten.class.getSimpleName());

    public static void main(String[] args) {
        LOGGER.info("Starting Tungsten...");
        int port = DEFAULT_PORT;
        if (args.length < 1) {
            throw new IllegalArgumentException("Website directory is required");
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid port number, using default port " + DEFAULT_PORT);
            }
        }
        try {
            Server server = new Server(args[0], port);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}
