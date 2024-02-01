package com.backend.trawisa.service;

import com.app.base.project.exception.ResourceNotFoundException;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.mapper.VenueMapper;
import com.backend.trawisa.model.request.CreateVenueRequest;
import com.backend.trawisa.model.request.UpdateVenueRequest;
import com.backend.trawisa.model.response.venue.VenueListResponse;
import com.backend.trawisa.model.response.venue.VenueResponse;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.model.entity.VenueEntity;
import com.backend.trawisa.repositories.VenueRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.VenueServiceListener;
import com.backend.trawisa.utils.CommonApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.IMAGES_FOLDER.VENUE;
import static com.app.base.project.utils.Utils.isNullOrEmpty;
import static com.backend.trawisa.utils.MyUtils.checkValidUser;

@Service
public class VenueServiceImpl implements VenueServiceListener {

    private final VenueRepo venueRepo;
    private VenueMapper venueMapper;

    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;

    private final MultiLangMessage langMessage;

    private final CommonApiUtils apiUtils;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final String requestSuccessMsg;

    private final FileServiceImpl fileService;

    @Autowired
    public VenueServiceImpl(VenueRepo venueRepo, ApiDataResponse apiDataResponse, ApiResponse apiResponse, MultiLangMessage langMessage,
                            CommonApiUtils apiUtils, JwtUtils jwtUtils, ModelMapper modelMapper, FileServiceImpl fileService,
                            VenueMapper venueMapper) {
        this.venueRepo = venueRepo;
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.langMessage = langMessage;
        this.apiUtils = apiUtils;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.venueMapper = venueMapper;
        this.requestSuccessMsg = this.langMessage.getLocalizeMessage("requestSuccess");
        this.fileService = fileService;
    }

    @Override
    public ApiResponse createVenue(CreateVenueRequest venueRequest) {

        UserEntity userEntity = jwtUtils.getCurrentUser();
        VenueEntity venueEntity = this.venueMapper.toVenueEntity(venueRequest);
        String[] latLong = venueRequest.getLatLong().split(",");

        venueEntity.setUserEntity(userEntity);
        venueRepo.save(venueEntity);
        apiResponse.setMessageResponse("Venue created successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse getVenueList(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String searchKeyword) {

        UserEntity userEntity = jwtUtils.getCurrentUser();
        Sort sort;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<VenueEntity> pagingVenueList;
        Print.log("Searc h keyword " + searchKeyword);
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            pagingVenueList = this.venueRepo.findAllByUserEntityAndNameContainingIgnoreCase(userEntity, pageable, searchKeyword);
        } else {
            pagingVenueList = this.venueRepo.findAllByUserEntity(userEntity, pageable);
        }
        List<VenueEntity> venueEntityList = pagingVenueList.getContent();
        VenueListResponse venueListResponse = new VenueListResponse();
        venueListResponse.setTotalPages(pagingVenueList.getTotalPages());
        List<VenueResponse> venueList = venueEntityList.stream().map((venue) -> this.modelMapper.map(venue, VenueResponse.class)).toList();
        venueListResponse.setVenueList(venueList);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, venueListResponse, HttpStatus.OK.value());
        return apiDataResponse;
    }

    @Override
    public ApiResponse getVenueDetail(int venueId) {
        VenueEntity venue = getVenueInfo(venueId);
        checkValidUser(venue.getUserEntity(), jwtUtils.getCurrentUser());
        VenueResponse venueDetail = this.modelMapper.map(venue, VenueResponse.class);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, venueDetail, HttpStatus.OK.value());
        return apiDataResponse;
    }

    @Override
    public ApiResponse updateVenueDetail(UpdateVenueRequest request) {
        VenueEntity venueEntity = getVenueInfo(request.getVenueId());
        checkValidUser(venueEntity.getUserEntity(), jwtUtils.getCurrentUser());
        modelMapper.map(request, venueEntity);
        venueRepo.save(venueEntity);
        apiResponse.setMessageResponse("Venue Updated successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse updateVenueImg(MultipartFile imgFile, int venueId) {
        UserEntity userEntity = jwtUtils.getCurrentUser();
        VenueEntity venue = getVenueInfo(venueId);
        checkValidUser(venue.getUserEntity(), userEntity);
        if (!isNullOrEmpty(venue.getVenueImg()))
            fileService.deleteFileFromS3(venue.getVenueImg());
        String venueImgName = this.fileService.uploadFileToS3(imgFile, VENUE + "/" + userEntity.getId());
        venue.setVenueImg(venueImgName);
        this.venueRepo.save(venue);
        apiResponse.setMessageResponse("Venue profile updated successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse deleteVenue(int venueId) {
        VenueEntity venueEntity = getVenueInfo(venueId);
        checkValidUser(venueEntity.getUserEntity(), jwtUtils.getCurrentUser());
        this.venueRepo.delete(venueEntity);
        apiResponse.setMessageResponse("Venue deleted successfully", HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public ApiResponse deleteVenueImage(int venueId) {
        VenueEntity venue = getVenueInfo(venueId);
        checkValidUser(venue.getUserEntity(), jwtUtils.getCurrentUser());
        if (!isNullOrEmpty(venue.getVenueImg())) {
            fileService.deleteFileFromS3(venue.getVenueImg());
            venue.setVenueImg(null);
            this.venueRepo.save(venue);
            apiResponse.setMessageResponse("Venue Image deleted successfully", HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setMessageResponse("Venue Image does not exist", HttpStatus.OK.value());
        return apiResponse;
    }


    private VenueEntity getVenueInfo(int venueId) {
        return this.venueRepo.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue", true));
    }
}
