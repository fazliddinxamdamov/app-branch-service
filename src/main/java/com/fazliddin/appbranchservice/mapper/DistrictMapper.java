package com.fazliddin.appbranchservice.mapper;

import ai.ecma.lib.entity.District;
import ai.ecma.lib.payload.DistrictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * This interface not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper {

    @Mapping(target = "regionId", source = "region.id")
    DistrictDTO toDTO(District district);

    List<DistrictDTO> toDTOList(List<District> districts);
}
