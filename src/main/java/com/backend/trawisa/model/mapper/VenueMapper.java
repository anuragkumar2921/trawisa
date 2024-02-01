package com.backend.trawisa.model.mapper;

import com.backend.trawisa.model.entity.VenueEntity;
import com.backend.trawisa.model.request.CreateVenueRequest;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueEntity toVenueEntity(CreateVenueRequest request);

    @BeforeMapping
    default void mapLatLong(CreateVenueRequest request, @MappingTarget VenueEntity venueEntity) {
        if (request.getLatLong() != null && !request.getLatLong().isEmpty()){
            String[] latLong = request.getLatLong().split(",");
            venueEntity.setLatitude(new BigDecimal(latLong[0].trim()));
            venueEntity.setLongitude(new BigDecimal(latLong[1].trim()));
        }


    }


}
