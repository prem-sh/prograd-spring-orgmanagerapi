package io.github.premsh.orgmanager.dto.designation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Designation")
public class UpdateDesignationDto {
    @NotNull
    @Size(max = 50)
    @NotBlank
    private String name;
}
