package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Asset")
public class CreateAssetDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private Long count;

    @NotNull
    private Double purchaseCost;

    @NotNull
    private Double currentValue;
}
