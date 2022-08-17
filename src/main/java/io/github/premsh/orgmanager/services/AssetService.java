package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.asset.AssetDto;
import io.github.premsh.orgmanager.dto.asset.AssetsDto;
import io.github.premsh.orgmanager.dto.asset.CreateAssetDto;
import io.github.premsh.orgmanager.dto.asset.UpdateAssetDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface AssetService {
    ResponseEntity<AssetDto> getAssetById(Long orgId, Long id);
    ResponseEntity<AssetsDto> getAllAssets(Long orgId);
    ResponseEntity<AssetsDto> filterAssets(Long orgId, String searchText);
    ResponseEntity<CreatedDto> createAsset(Long orgId, CreateAssetDto dto);
    ResponseEntity<UpdatedDto> updateAsset(Long orgId, UpdateAssetDto dto, Long id);
    ResponseEntity<DeletedDto> deleteAsset(Long orgId, Long id);
}
