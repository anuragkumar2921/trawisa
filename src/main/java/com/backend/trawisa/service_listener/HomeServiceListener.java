package com.backend.trawisa.service_listener;

import com.app.base.project.utils.apiResponse.ApiResponse;

public interface HomeServiceListener {

    ApiResponse getTournamentList(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection,String searchKeyword,String filter);
}
