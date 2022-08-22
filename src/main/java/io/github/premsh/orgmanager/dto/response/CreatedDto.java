package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.http.HttpStatus;

@JacksonXmlRootElement(localName = "Created")
public class CreatedDto extends SuccessResponse{

    public CreatedDto(String message) {
        super(HttpStatus.CREATED,message);
    }

    public CreatedDto(String message, String entityId) {
        super(HttpStatus.CREATED, message, entityId);
    }

    public CreatedDto(String message, String entityId, String info) {
        super(HttpStatus.CREATED, message, entityId, info);
    }

    public CreatedDto(HttpStatus status, String message) {
        super(status, message);
    }
}
