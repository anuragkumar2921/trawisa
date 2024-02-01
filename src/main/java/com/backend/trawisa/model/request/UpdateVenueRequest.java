package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class UpdateVenueRequest extends CreateVenueRequest {
    private Integer venueId;
}
