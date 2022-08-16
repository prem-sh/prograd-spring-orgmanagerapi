package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

@JacksonXmlRootElement(localName = "ValidationError")
@Getter
public class ValidationErrorDto{
    private String field;
    private String rejectedValue;
    private String message;

    public ValidationErrorDto(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
