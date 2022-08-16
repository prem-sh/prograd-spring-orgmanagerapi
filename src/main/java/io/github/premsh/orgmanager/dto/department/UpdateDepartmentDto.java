package io.github.premsh.orgmanager.dto.department;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Department")
public class UpdateDepartmentDto {
    @NotNull
    @Size(max = 50)
    @NotBlank
    private String name;
}
