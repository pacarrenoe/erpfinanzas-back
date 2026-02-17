package cl.home.erpfinanzas.web.response;

public class Status {

    private String code;
    private String message;

    public Status() {
    }

    public Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
