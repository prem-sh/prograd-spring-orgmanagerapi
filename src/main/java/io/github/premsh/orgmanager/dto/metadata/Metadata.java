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
        @JacksonXmlProperty(isAttribute = true)
        Long by = null;
        @JacksonXmlProperty(isAttribute = true)
        Date at = null;
    }

    private final Action created = new Action();
    private final Action updated = new Action();
    private final Action deleted = new Action();

    public Metadata(Long createdBy, Date createdAt, Long updatedBy, Date updatedAt, Long deletedBy, Date deletedAt) {
        if(createdBy != null) created.setBy(createdBy);
        if(createdAt != null) created.setAt(createdAt);
        if(updatedBy != null) updated.setBy(updatedBy);
        if(updatedAt != null) updated.setAt(updatedAt);
        if(deletedBy != null) deleted.setBy(deletedBy);
        if(deletedAt != null) deleted.setAt(deletedAt);
    }
}