package io.github.premsh.orgmanager.dto.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import io.github.premsh.orgmanager.models.Tag;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Tag")
public class SimpleTagDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long tagId;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    @JacksonXmlText
    private final String tagname;

    public SimpleTagDto(Tag tag) {
        this.tagId = tag.getId();
        this.deleted = tag.getIsDeleted();
        this.tagname = tag.getTag();
    }
}
