package io.github.premsh.orgmanager.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.http.HttpStatus;

@JacksonXmlRootElement(localName = "Updated")
public class UpdatedDto extends SuccessResponse{
    public UpdatedDto(String message) {
        super(HttpStatus.ACCEPTED,message);
    }

    public UpdatedDto(String message, String entityId) {
        super(HttpStatus.ACCEPTED,message, entityId);
    }

    public UpdatedDto(String message, String entityId, String info) {
        super(HttpStatus.ACCEPTED,message, entityId, info);
    }
}
