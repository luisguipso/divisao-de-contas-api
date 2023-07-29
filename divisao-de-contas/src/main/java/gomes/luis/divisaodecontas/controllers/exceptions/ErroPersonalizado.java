package gomes.luis.divisaodecontas.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class ErroPersonalizado implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErroPersonalizado(){

    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ErroPersonalizado setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ErroPersonalizado setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ErroPersonalizado setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErroPersonalizado setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErroPersonalizado setPath(String path) {
        this.path = path;
        return this;
    }
}
