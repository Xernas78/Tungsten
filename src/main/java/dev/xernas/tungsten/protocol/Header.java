package dev.xernas.tungsten.protocol;

public enum Header {

    CONTENT_TYPE_HTML("Content-Type", "text/html"),
    CONTENT_TYPE_JSON("Content-Type", "application/json"),
    CONTENT_TYPE_CSS("Content-Type", "text/css"),
    CONTENT_TYPE_JS("Content-Type", "application/javascript"),
    CONTENT_TYPE_TEXT("Content-Type", "text/plain");

    private final String key;
    private final String value;

    Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getHeader() {
        return key + ": " + value;
    }

    public static Header fromString(String header) {
        String[] parts = header.split(": ");
        for (Header h : values()) {
            if (h.getKey().equals(parts[0]) && h.getValue().equals(parts[1])) {
                return h;
            }
        }
        return null;
    }
}
