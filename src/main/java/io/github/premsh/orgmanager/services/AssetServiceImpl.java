package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.asset.AssetDto;
import io.github.premsh.orgmanager.dto.asset.AssetsDto;
import io.github.premsh.orgmanager.dto.asset.CreateAssetDto;
import io.github.premsh.orgmanager.dto.asset.UpdateAssetDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Asset;
import io.github.premsh.orgmanager.repository.AssetsRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AssetServiceImpl implements AssetService{
    @Autowired
    private AssetsRepo assetsRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Override
    public ResponseEntity<AssetDto> getAssetById(Long orgId, Long id) {
        return new ResponseEntity<>(
                new AssetDto(assetsRepo.findById(orgId, id).orElseThrow(()-> new EntityNotFoundException("Asset not found"))),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AssetsDto> getAllAssets(Long orgId) {
        return new ResponseEntity<>(
                new AssetsDto(assetsRepo.findAll()),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AssetsDto> filterAssets(Long orgId, String searchText) {
        return new ResponseEntity<>(
                new AssetsDto(assetsRepo.filter(searchText)),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createAsset(Long orgId, CreateAssetDto dto) {
        Asset a = new Asset();
        a.setName(dto.getName());
        a.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        a.setCount(dto.getCount());
        a.setPurchaseCost(dto.getPurchaseCost());
        a.setCurrentValue(dto.getCurrentValue());
        a.setCreatedBy(principalService.getUser());
        a.setUpdatedBy(principalService.getUser());
        Asset newasset = assetsRepo.save(a);
        return new ResponseEntity<>(new CreatedDto("Asset created successfully",String.valueOf(newasset.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateAsset(Long orgId, UpdateAssetDto dto, Long id) {
        Asset a = assetsRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Asset not found"));
        if(dto.getName()!=null) a.setName(dto.getName());
        if(dto.getCount()!=null) a.setCount(dto.getCount());
        if(dto.getPurchaseCost()!=null) a.setPurchaseCost(dto.getPurchaseCost());
        if(dto.getCurrentValue()!=null) a.setCurrentValue(dto.getCurrentValue());
        a.setUpdatedBy(principalService.getUser());
        assetsRepo.save(a);
        return new ResponseEntity<>(new UpdatedDto("Asset Updated successfully",String.valueOf(a.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteAsset(Long orgId, Long id) {
        Asset a = assetsRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Asset not found"));
        a.setIsDeleted(true);
        a.setDeletedBy(principalService.getUser());
        a.setDeletedAt(new Date());
        assetsRepo.save(a);
        return new ResponseEntity<>(new DeletedDto("Asset Deleted successfully",String.valueOf(a.getId())), HttpStatus.CREATED);
    }
}
