package com.backend.trawisa.service;

import com.app.base.project.utils.LocalizationUtils;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.model.entity.TournamentEntity;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.model.entity.VenueEntity;
import com.backend.trawisa.repositories.TournamentRepo;
import com.backend.trawisa.security.jwt.JwtUtils;
import com.backend.trawisa.service_listener.HomeServiceListener;
import com.backend.trawisa.utils.CommonApiUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeServiceListener {

    private final TournamentRepo tournamentRepo;
    @Qualifier("getApiDataResponse")
    private final ApiDataResponse apiDataResponse;
    @Qualifier("getApiResponse")
    private final ApiResponse apiResponse;

    private final CommonApiUtils apiUtils;
    private final JwtUtils jwtUtils;
    private final String requestSuccessMsg;
    private final LocalizationUtils localization;

    public HomeServiceImpl(ApiDataResponse apiDataResponse, ApiResponse apiResponse, CommonApiUtils apiUtils, JwtUtils jwtUtils,
                           LocalizationUtils localization, TournamentRepo tournamentRepo) {
        this.apiDataResponse = apiDataResponse;
        this.apiResponse = apiResponse;
        this.apiUtils = apiUtils;
        this.jwtUtils = jwtUtils;
        this.localization = localization;
        this.tournamentRepo = tournamentRepo;
        this.requestSuccessMsg = localization.getMessage("requestSuccess");
    }


    @Override
    public ApiResponse getTournamentList(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String searchKeyword, String filter) {

//        UserEntity userEntity = jwtUtils.getCurrentUser();

        List<VenueEntity> tournamentEntityList = this.tournamentRepo.findLocationsWithinRadius(Double.parseDouble("12.935198891054318"),Double.parseDouble("77.619603695798000"),7);
//        List<TournamentEntity> tournamentEntityList = this.tournamentRepo.findAll();
        Print.log("tournamentEntityList => "+tournamentEntityList);
        apiDataResponse.setSuccessResponse(requestSuccessMsg, tournamentEntityList, HttpStatus.OK.value());
        return apiDataResponse;
    }
}
