package com.fazliddin.appbranchservice.mapper;

import com.fazliddin.library.entity.Branch;
import com.fazliddin.library.payload.resp.BranchRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Murtazayev Muhammad
 * @since 22.01.2022
 */
@Mapper(componentModel = "spring")
public interface BranchMapper {
    @Mapping(target = "addressId", source = "address.id")
    BranchRespDto toBranchRespDto(Branch branch);
}
