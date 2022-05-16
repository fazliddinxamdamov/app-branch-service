package com.fazliddin.appbranchservice.service;

import com.fazliddin.appbranchservice.common.MessageService;
import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.appbranchservice.mapper.BranchMapper;
import com.fazliddin.library.entity.Address;
import com.fazliddin.library.entity.Branch;
import com.fazliddin.library.entity.District;
import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.CustomPage;
import com.fazliddin.library.payload.req.BranchReqDto;
import com.fazliddin.library.payload.resp.BranchRespDto;
import com.fazliddin.library.repository.AddressRepository;
import com.fazliddin.library.repository.BranchRepository;
import com.fazliddin.library.repository.DistrictRepository;
import com.fazliddin.library.services.CommonServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final AddressRepository addressRepository;
    private final DistrictRepository districtRepository;
    private final BranchMapper branchMapper;

    @Override
    public ApiResult<CustomPage<BranchRespDto>> get(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Branch> branches = branchRepository.findAll(pageRequest);
        return ApiResult.successResponse(makeCustomPage(branches));
    }

    @Override
    public ApiResult<CustomPage<BranchRespDto>> getByRegion(Integer page, Integer size, Long regionId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Branch> branches = branchRepository.findAllByRegionId(pageRequest, regionId);
        return ApiResult.successResponse(makeCustomPage(branches));
    }

    @Override
    public ApiResult<CustomPage<BranchRespDto>> getByDistrict(Integer page, Integer size, Long districtId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Branch> branches = branchRepository.findAllByDistrictId(pageRequest, districtId);
        return ApiResult.successResponse(makeCustomPage(branches));
    }

    @Override
    public ApiResult<BranchRespDto> getNearly(Double lat, Double lon) {
        List<Branch> branches = branchRepository.findAll();
        List<Branch> nearlyBranches = new ArrayList<>();
//        List<Branch> nearlyBranches = branches.stream().filter(branch -> CommonService.distance(lat, lon, branch.getAddress().getLat(), branch.getAddress().getLon()) <= branch.getMaxDeliveryDistance()).collect(Collectors.toList());

        for (Branch branch : branches) {
            if (CommonServices.distance(lat, lon, branch.getAddress().getLat(), branch.getAddress().getLon()) <= branch.getMaxDeliveryDistance()){
                nearlyBranches.add(branch);
            }
        }

        Branch nearlyBranch = null;
        for (Branch branch : nearlyBranches) {
            if (Objects.isNull(nearlyBranch)) {
                nearlyBranch = branch;
            } else {
                double distance = CommonServices.distance(lat, lon, branch.getAddress().getLat(), branch.getAddress().getLon());
                double nearlyDistance = CommonServices.distance(lat, lon, nearlyBranch.getAddress().getLat(), nearlyBranch.getAddress().getLon());
                if (distance <= nearlyDistance) {
                    nearlyBranch = branch;
                }
            }
        }
        if (Objects.isNull(nearlyBranch))
            throw RestException.restThrow(MessageService.getMessage("NEAR_BRANCH_NOT_FOUND"), HttpStatus.BAD_REQUEST);
        return ApiResult.successResponse(branchMapper.toBranchRespDto(nearlyBranch));
    }

    @Override
    public ApiResult<BranchRespDto> get(Long id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> RestException.notFound("BRANCH"));
        return ApiResult.successResponse(branchMapper.toBranchRespDto(branch));
    }

    @Override
    public ApiResult<?> create(BranchReqDto dto) {
        Address address;
        if (dto.getAddressId() != null) {
            address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> RestException.notFound("ADDRESS"));
        } else {
            District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(() -> RestException.notFound("DISTRICT"));
            address = addressRepository.save(new Address(dto.getAddressName(), dto.getAddressLat(), dto.getAddressLon(), district));
        }
        Branch branch = branchRepository.save(new Branch(dto.getName(), address, dto.getMaxDeliveryDistance(), dto.getMaxDeliveryTime(), dto.isAutoDistribution(), dto.isActive()));
        return ApiResult.successResponse(branchMapper.toBranchRespDto(branch), MessageService.successSave("BRANCH"));
    }

    @Override
    public ApiResult<?> edit(Long id, BranchReqDto dto) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> RestException.notFound("BRANCH"));
        Address address;
        if (dto.getAddressId() != null) {
            address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> RestException.notFound("ADDRESS"));
        } else {
            District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(() -> RestException.notFound("DISTRICT"));
            address = addressRepository.save(new Address(dto.getAddressName(), dto.getAddressLat(), dto.getAddressLon(), district));
        }
        branch.setName(dto.getName());
        branch.setAddress(address);
        branch.setActive(dto.isActive());
        branch.setAutoDistribution(dto.isAutoDistribution());
        branch.setMaxDeliveryDistance(dto.getMaxDeliveryDistance());
        branch.setMaxDeliveryTime(dto.getMaxDeliveryTime());
        branchRepository.save(branch);
        return ApiResult.successResponse(branchMapper.toBranchRespDto(branch));
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            branchRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.successDelete("BRANCH"));
        } catch (Exception e) {
            throw RestException.restThrow(MessageService.cannotDelete("BRANCH"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public CustomPage<BranchRespDto> makeCustomPage(Page<Branch> branches) {
        return new CustomPage<>(
                branches.getContent().stream().map(branchMapper::toBranchRespDto).collect(Collectors.toList()),
                branches.getNumberOfElements(),
                branches.getNumber(),
                branches.getTotalElements(),
                branches.getTotalPages(),
                branches.getSize()
        );
    }

}
