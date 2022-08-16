package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Getter;

@JacksonXmlRootElement(localName = "Organisation")
@Getter
public class OrganizationCompactDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long orgId;
    @JacksonXmlProperty(isAttribute = true)
    private final String orgName;

    public OrganizationCompactDto(Organization org) {
        this.orgId = org.getId();
        this.orgName = org.getName();
    }
}
