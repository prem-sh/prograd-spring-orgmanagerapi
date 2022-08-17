package io.github.premsh.orgmanager.dto.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.user.UserCompactDto;
import io.github.premsh.orgmanager.models.User;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "Metadata")
public class Metadata {

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Action{
        UserCompactDto by = null;
        Date at = null;
    }

    private final Action creation = new Action();
    private final Action updation = new Action();
    private final Action deletion = new Action();

    public Metadata(User createdBy, Date createdAt, User updatedBy, Date updatedAt, User deletedBy, Date deletedAt) {
        if(createdBy != null) creation.setBy(new UserCompactDto(createdBy));
        if(createdAt != null) creation.setAt(createdAt);
        if(updatedBy != null) updation.setBy(new UserCompactDto(updatedBy));
        if(updatedAt != null) updation.setAt(updatedAt);
        if(deletedBy != null) deletion.setBy(new UserCompactDto(deletedBy));
        if(deletedAt != null) deletion.setAt(deletedAt);
    }
}