package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Asset")
public class UpdateAssetDto {

    @Size(max = 50)
    private String name;
    private Long count;
    private Double purchaseCost;
    private Double currentValue;
}
