package io.github.premsh.orgmanager.dto.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.models.Tag;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JacksonXmlRootElement(localName = "Tag")
public class TagDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long tagId;
    @JacksonXmlProperty(isAttribute = true)
    private final String tagname;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    private final OrganizationCompactDto organization;
    private final Metadata metadata;

    public TagDto(Tag tag) {
        this.tagId = tag.getId();
        this.tagname = tag.getTag();
        this.deleted = tag.getIsDeleted();
        this.organization = new OrganizationCompactDto(tag.getOrganization());
        this.metadata = new Metadata(
                tag.getCreatedBy(),
                tag.getCreatedAt(),
                tag.getUpdatedBy(),
                tag.getUpdatedAt(),
                tag.getDeletedBy(),
                tag.getDeletedAt()
        );
    }
}
