package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Organizations")
public class OrganizationsDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Organization")
    private final List<OrganizationDto> organization;

    public OrganizationsDto(List<Organization> organizations) {
        this.organization = organizations.stream().map(OrganizationDto::new).collect(Collectors.toList());
    }

    public List<OrganizationDto> getOrganization() {
        return organization;
    }
}
