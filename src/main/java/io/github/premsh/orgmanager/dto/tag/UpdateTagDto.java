package io.github.premsh.orgmanager.dto.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Tag")
public class UpdateTagDto {
    private List<String> from;
    private List<String> to;
}
