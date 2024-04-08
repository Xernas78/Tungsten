package dev.xernas.tungsten.protocol;

public enum Protocol {

    HTTP("1.1"),
    HTTPS("-");

    private final String version;

    Protocol(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static Protocol fromString(String protocol) {
        if (protocol.equals("HTTP")) {
            return HTTP;
        } else if (protocol.equals("HTTPS")) {
            return HTTPS;
        }
        return null;
    }
}
