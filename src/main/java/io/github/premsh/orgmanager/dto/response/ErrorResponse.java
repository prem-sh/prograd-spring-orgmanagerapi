package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Getter
public abstract class ErrorResponse {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private String message = null;
    private String error = null;
    private String additionalInfo = null;

    public ErrorResponse(HttpStatus status){
        timestamp = new Date();
        this.status = status;
    }
    public ErrorResponse(HttpStatus status, Throwable exc){
        timestamp = new Date();
        this.status = status;
        this.message = "Unexpected error occured";
        this.additionalInfo = exc.getLocalizedMessage();
        this.error = exc.getClass().toString();
    }
    public ErrorResponse(HttpStatus status, String message, Throwable exc){
        timestamp = new Date();
        this.status = status;
        this.message = message;
        this.additionalInfo = exc.getLocalizedMessage();
        this.error = exc.getClass().getSimpleName();
    }
}
