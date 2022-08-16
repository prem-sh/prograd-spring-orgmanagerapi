package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@JacksonXmlRootElement(localName = "Error")
public class ErrorDto extends ErrorResponse{
    @JacksonXmlElementWrapper(localName = "ValidationErrors")
    private ValidationErrorDto[] validationError = null;

    public ErrorDto(HttpStatus status) {
        super(status);
    }
    public ErrorDto(HttpStatus status, Throwable exc) {
        super(status, exc);
    }
    public ErrorDto(HttpStatus status, String message, Throwable exc) {
        super(status, message, exc);
    }
    public void setValidationErrors(ValidationErrorDto[] validationErrors) {
        this.validationError = validationErrors;
    }
}
