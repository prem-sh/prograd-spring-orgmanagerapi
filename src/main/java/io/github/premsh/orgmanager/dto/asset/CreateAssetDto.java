package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@JacksonXmlRootElement(localName = "Asset")
public class CreateAssetDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    private Long count;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double purchaseCost;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double currentValue;

    private Set<String> tags;
}
