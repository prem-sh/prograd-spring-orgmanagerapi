package io.github.premsh.orgmanager.dto.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Tag;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Tags")
public class TagsDto {
    @JacksonXmlElementWrapper(useWrapping = false, localName = "Tag")
    private final List<TagDto> tags;

    public TagsDto(List<Tag> tags) {
        this.tags = tags.stream().map(TagDto::new).collect(Collectors.toList());
    }
}
