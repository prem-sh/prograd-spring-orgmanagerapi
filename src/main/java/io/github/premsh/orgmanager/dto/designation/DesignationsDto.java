package io.github.premsh.orgmanager.dto.designation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Designation;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "Designation")
@Getter
public class DesignationsDto {
    @JacksonXmlElementWrapper(localName = "Department", useWrapping = false)
    private final List<DesignationDto> designation;
    public DesignationsDto(List<Designation> designations) {
        this.designation = designations.stream().map(DesignationDto::new).collect(Collectors.toList());
    }
}
