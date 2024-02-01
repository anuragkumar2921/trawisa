package com.backend.trawisa.model.response.venue;

import lombok.Data;

import java.util.List;

@Data
public class VenueListResponse {
     private int totalPages;
     private List<VenueResponse> venueList;
}
