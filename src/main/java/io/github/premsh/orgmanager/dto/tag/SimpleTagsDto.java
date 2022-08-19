package io.github.premsh.orgmanager.dto.tag;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Tag;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Tags")
public class SimpleTagsDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Tag")
    private final List<SimpleTagDto> tags;

    public SimpleTagsDto(List<Tag> tags) {
        this.tags = tags.stream().map(SimpleTagDto::new).collect(Collectors.toList());
    }
}