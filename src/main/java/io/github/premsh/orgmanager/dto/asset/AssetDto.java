package io.github.premsh.orgmanager.dto.asset;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.tag.TagsDto;
import io.github.premsh.orgmanager.models.Asset;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@JacksonXmlRootElement(localName = "Asset")
public class AssetDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long assetId;
    @JacksonXmlProperty(isAttribute = true)
    private final String name;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    private final long count ;
    private final double purchaseCost;
    private final double currentValue;
    private final OrganizationCompactDto organization;
    private final Metadata metadata;
    @JacksonXmlProperty(localName = "Tags")
    private final TagsDto tags;

    public AssetDto(Asset asset) {
        this.assetId = asset.getId();
        this.name = asset.getName();
        this.deleted = asset.getIsDeleted();
        this.count = asset.getCount();
        this.purchaseCost = asset.getPurchaseCost();
        this.currentValue = asset.getCurrentValue();
        this.organization = new OrganizationCompactDto(asset.getOrganization());
        this.tags = new TagsDto(new ArrayList<>(asset.getTags()));
        this.metadata = new Metadata(
                asset.getCreatedBy(),
                asset.getCreatedAt(),
                asset.getUpdatedBy(),
                asset.getUpdatedAt(),
                asset.getDeletedBy(),
                asset.getDeletedAt()
        );
    }
}
