package io.github.premsh.orgmanager.dto.response;

import org.springframework.http.HttpStatus;

public abstract class SuccessResponse {
    private HttpStatus status;
    private String message = null;
    private String entityId = null;
    private String info = null;

    public SuccessResponse(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }

    public SuccessResponse(HttpStatus status, String message, String entityId) {
        this.status = status;
        this.message = message;
        this.entityId = entityId;
    }

    public SuccessResponse(HttpStatus status, String message, String entityId, String info) {
        this.status = status;
        this.message = message;
        this.entityId = entityId;
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getInfo() {
        return info;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
