package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Asset;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Asset")
public class AssetsDto {
    @JacksonXmlElementWrapper(localName = "Asset", useWrapping = false)
    private final List<AssetDto> assets;

    public AssetsDto(List<Asset> assets) {
        this.assets = assets.stream().map(AssetDto::new).collect(Collectors.toList());
    }
}
