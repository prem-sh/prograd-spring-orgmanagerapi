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

    @JacksonXmlText
    private final String tagname;

    public SimpleTagDto(Tag tag) {
        this.tagId = tag.getId();
        this.tagname = tag.getTagName();
    }
}
