package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.constants.RoleConstants;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.asset.AssetDto;
import io.github.premsh.orgmanager.dto.asset.AssetsDto;
import io.github.premsh.orgmanager.dto.asset.CreateAssetDto;
import io.github.premsh.orgmanager.dto.asset.UpdateAssetDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Asset;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.models.Tag;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.AssetsRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService{
    @Autowired
    private AssetsRepo assetsRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepo tagRepo;
    @Override
    public ResponseEntity<AssetDto> getAssetById(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new AssetDto(assetsRepo.findById(orgId, id).orElseThrow(()-> new EntityNotFoundException("Asset not found"))),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AssetsDto> getAllAssets(Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new AssetsDto(assetsRepo.findAll(orgId)),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AssetsDto> searchByTags(Long orgId, String[] tags) {
        Set<Asset> assets = new HashSet<>();
        List<String> tagSet =  Arrays.stream(tags).map(String::toUpperCase).collect(Collectors.toList());
        assets = assetsRepo.findAllByTags(orgId, tagSet);
        assets = assets.stream().filter(
                asset -> {
                    for (String t: tags) {
                        boolean contains = false;
                        for (Tag tag: asset.getTags()) {
                            if(tag.getTagName().equalsIgnoreCase(t)) {
                                contains=true;
                                break;
                            }
                        }
                        if(!contains) return false;
                    }
                    return true;
                }
        ).collect(Collectors.toSet());

        return new ResponseEntity<>(
                new AssetsDto(assets.stream().toList()), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AssetsDto> filterAssets(Long orgId, String searchText) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new AssetsDto(assetsRepo.filter(orgId,searchText)),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createAsset(Long orgId, CreateAssetDto dto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_CREATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Organization org = organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"));
        Asset a = new Asset();
        a.setName(dto.getName());
        a.setOrganization(org);
        a.setCount(dto.getCount());
        a.setPurchaseCost(dto.getPurchaseCost());
        a.setCurrentValue(dto.getCurrentValue());
        a.setCreatedBy(auth.getUserId());
        a.setUpdatedBy(auth.getUserId());
        if(dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream().map(
                    t -> tagService.facilitateTag(orgId, t, auth.getUserId(), org)
            ).collect(Collectors.toSet());
            a.setTags(tags);
        }
        Asset newasset = assetsRepo.save(a);

        return new ResponseEntity<>(new CreatedDto("Asset created successfully",String.valueOf(newasset.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateAsset(Long orgId, UpdateAssetDto dto, Long id) {

        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_UPDATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Organization org = organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"));

        if (!assetsRepo.existsById(orgId, id)) throw new EntityNotFoundException("Asset not found");
        Asset a = assetsRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Asset not found"));
        if(dto.getName()!=null) a.setName(dto.getName());
        if(dto.getCount()!=null) a.setCount(dto.getCount());
        if(dto.getPurchaseCost()!=null) a.setPurchaseCost(dto.getPurchaseCost());
        if(dto.getCurrentValue()!=null) a.setCurrentValue(dto.getCurrentValue());
        a.setUpdatedBy(principalService.getUser().getId());
        if(dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream().map(
                    t -> tagService.facilitateTag(orgId, t, auth.getUserId(), org)
            ).collect(Collectors.toSet());
            a.setTags(tags);
        }
        if(dto.getRemoveTags() != null) {
            Set<Tag> removeTags = dto.getRemoveTags().stream().map(
                    t -> tagRepo.findByName(orgId, t).orElseThrow(()->new EntityNotFoundException("Tag : %s not found".formatted(t)))
            ).collect(Collectors.toSet());
            Set<Tag> tags = a.getTags().stream().filter(t->{
                if(removeTags.contains(t)) {
                    return false;
                }
                else return true;
            }).collect(Collectors.toSet());
            a.setTags(tags);
        }
        if(dto.getAddTags() != null) {
            Set<Tag> tags = dto.getAddTags().stream().map(
                    t -> tagService.facilitateTag(orgId, t, auth.getUserId(), org)
            ).collect(Collectors.toSet());
            tags.addAll(a.getTags());
            a.setTags(tags);
        }
        assetsRepo.save(a);
        return new ResponseEntity<>(new UpdatedDto("Asset Updated successfully",String.valueOf(a.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteAsset(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.ASSET_DELETE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Asset a = assetsRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Asset not found"));

        a.removeAllTags();
        assetsRepo.delete(a);
        return new ResponseEntity<>(new DeletedDto("Asset Deleted successfully",String.valueOf(id)), HttpStatus.CREATED);
    }
}
