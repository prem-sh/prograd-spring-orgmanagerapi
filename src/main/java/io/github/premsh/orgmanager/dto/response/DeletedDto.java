package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.http.HttpStatus;

@JacksonXmlRootElement(localName = "Deleted")
public class DeletedDto extends SuccessResponse{
    public DeletedDto(String message) {
        super(HttpStatus.ACCEPTED,message);
    }

    public DeletedDto(String message, String entityId) {
        super(HttpStatus.ACCEPTED, message, entityId);
    }

    public DeletedDto(String message, String entityId, String info) {
        super(HttpStatus.ACCEPTED, message, entityId, info);
    }
}
