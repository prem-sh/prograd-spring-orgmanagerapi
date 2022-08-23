package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.asset.AssetDto;
import io.github.premsh.orgmanager.dto.asset.AssetsDto;
import io.github.premsh.orgmanager.dto.asset.CreateAssetDto;
import io.github.premsh.orgmanager.dto.asset.UpdateAssetDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/{id}")
    public ResponseEntity<AssetDto> getAssetById(@PathVariable Long orgId, @PathVariable Long id) {
        return assetService.getAssetById(orgId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<AssetsDto> getAllAssets(@PathVariable Long orgId) {
        return assetService.getAllAssets(orgId);
    }

    @GetMapping("/filter/{searchText}")
    public ResponseEntity<AssetsDto> filterAssets(@PathVariable Long orgId, @PathVariable String searchText) {
        return assetService.filterAssets(orgId, searchText);
    }
    @GetMapping("/tagfilter")
    public ResponseEntity<AssetsDto> filterAssets(@PathVariable Long orgId, @RequestParam String[] tags) {
        return assetService.searchByTags(orgId, tags);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createAsset(@PathVariable Long orgId, @Valid @RequestBody CreateAssetDto dto) {
        return assetService.createAsset(orgId, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateAsset(@PathVariable Long orgId, @Valid @RequestBody UpdateAssetDto dto, @PathVariable Long id) {
        return assetService.updateAsset(orgId, dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteAsset(@PathVariable Long orgId, @PathVariable Long id) {
        return assetService.deleteAsset(orgId, id);
    }
}
