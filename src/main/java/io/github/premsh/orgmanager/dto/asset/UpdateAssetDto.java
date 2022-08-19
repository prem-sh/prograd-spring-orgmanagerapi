package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@JacksonXmlRootElement(localName = "Asset")
public class UpdateAssetDto {

    @Size(max = 50)
    private String name;
    @DecimalMin(value = "0")
    private Long count;
    @DecimalMin(value = "0.0")
    private Double purchaseCost;
    @DecimalMin(value = "0.0")
    private Double currentValue;

    private Set<String> tags;
    private Set<String> addTags;
    private Set<String> removeTags;
}
