package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.request.CreateVenueRequest;
import com.backend.trawisa.model.request.UpdateVenueRequest;
import org.springframework.web.multipart.MultipartFile;

public interface VenueServiceListener {

    ApiResponse createVenue(CreateVenueRequest venueRequest);
    ApiResponse getVenueList(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection,String search);
    ApiResponse getVenueDetail(int venueId);
    ApiResponse updateVenueDetail(UpdateVenueRequest venueRequest);
    ApiResponse updateVenueImg(MultipartFile imgFile,int venueId);
    ApiResponse deleteVenue(int venueId);
    ApiResponse deleteVenueImage(int venueId);
}
