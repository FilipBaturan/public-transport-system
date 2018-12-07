package construction_and_testing.public_transport_system.util;

import java.io.Serializable;

public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] content;

    private String format;

    public Image() {
    }

    public Image(byte[] content, String format) {
        this.content = content;
        this.format = format;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
