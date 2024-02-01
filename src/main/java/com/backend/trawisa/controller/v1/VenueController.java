package com.backend.trawisa.controller.v1;


import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.CreateVenueRequest;
import com.backend.trawisa.model.request.UpdateVenueRequest;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service.VenueServiceImpl;
import com.backend.trawisa.utils.validation.VenueValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.app.base.project.constant.BaseFinalConstant.PAGINATION.*;
import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(VENUE)
public class VenueController {

    private final JwtUtils jwtUtils;
    private final VenueServiceImpl venueService;

    private final VenueValidation venueValidation;

    @Autowired
    public VenueController(JwtUtils jwtUtils, VenueServiceImpl venueService, VenueValidation venueValidation) {
        this.jwtUtils = jwtUtils;
        this.venueService = venueService;
        this.venueValidation = venueValidation;
    }

    @PostMapping(CREATE_VENUE)
    public ResponseEntity<ApiResponse> createVenue(@Valid  @RequestBody CreateVenueRequest createVenueRequest){
        ApiResponse apiResponse = this.venueService.createVenue(createVenueRequest);
        return ResponseHandler.sendResponse(apiResponse);

    }


    @GetMapping(GET_VENUE_List)
    public ResponseEntity<ApiResponse> getVenueList(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION, required = false) String sortDirection,
            @RequestParam(value = "search", defaultValue = "", required = false) String search
    ) {
        ApiResponse apiResponse = venueService.getVenueList(pageNumber, pageSize, sortBy, sortDirection,search);
        return ResponseHandler.sendResponse(apiResponse);
    }


    @GetMapping(GET_VENUE_DETAIL)
    public ResponseEntity<ApiResponse> getVenueDetail(
            @RequestParam(value = "venueId") int venueId
    ) {
        ApiResponse apiResponse = this.venueService.getVenueDetail(venueId);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @PostMapping(UPDATE_VENUE_DETAIL)
    public ResponseEntity<ApiResponse> updateVenue(@RequestBody UpdateVenueRequest createVenueRequest) throws ApiValidationException {
        venueValidation.isUpdateVenueValid(createVenueRequest);
        ApiResponse apiResponse = this.venueService.updateVenueDetail(createVenueRequest);
        return ResponseHandler.sendResponse(apiResponse);

    }

    @PutMapping(UPDATE_VENUE_IMG)
    public ResponseEntity<ApiResponse> updateVenueImg(
            @RequestParam(value = "profileImage", required = true) MultipartFile profileImage,
            @RequestParam(value = "venueId") Integer venueId
    ) throws ApiValidationException {
        venueValidation.isUpdateVenueImgReqValid(profileImage, venueId);

        ApiResponse apiResponse = this.venueService.updateVenueImg(profileImage, venueId);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(DELETE_VENUE + "/{venueId}")
    public ResponseEntity<ApiResponse> deleteVenue(@PathVariable(name = "venueId") Integer venueId) throws ApiValidationException {
        venueValidation.isDeleteVenueValid(venueId);
        ApiResponse apiResponse = this.venueService.deleteVenue(venueId);
        return ResponseHandler.sendResponse(apiResponse);
    }

    @DeleteMapping(DELETE_VENUE_IMG + "/{venueId}")
    public ResponseEntity<ApiResponse> deleteVenueImg(@PathVariable(name = "venueId") Integer venueId) throws ApiValidationException {
        venueValidation.isDeleteVenueValid(venueId);
        ApiResponse apiResponse = this.venueService.deleteVenueImage(venueId);
        return ResponseHandler.sendResponse(apiResponse);
    }

}
