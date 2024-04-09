package dev.xernas.tungsten.protocol;

public enum Header {

    CONTENT_TYPE_HTML("Content-Type", "text/html"),
    CONTENT_TYPE_JSON("Content-Type", "application/json"),
    CONTENT_TYPE_CSS("Content-Type", "text/css"),
    CONTENT_TYPE_JS("Content-Type", "application/javascript"),
    CONTENT_TYPE_TEXT("Content-Type", "text/plain"),
    CONTENT_TYPE_PHP("Content-Type", "application/x-httpd-php"),
    CONTENT_TYPE_JPEG("Content-Type", "image/jpeg"),
    CONTENT_TYPE_PNG("Content-Type", "image/png"),
    CONTENT_TYPE_GIF("Content-Type", "image/gif"),
    CONTENT_TYPE_SVG("Content-Type", "image/svg+xml"),
    CONTENT_TYPE_ICO("Content-Type", "image/x-icon"),

    CONTENT_LENGTH("Content-Length", "0");

    private final String key;
    private String value;

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

    public Header setValue(String value) {
        this.value = value;
        return this;
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
