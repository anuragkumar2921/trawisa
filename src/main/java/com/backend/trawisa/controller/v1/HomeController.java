package com.backend.trawisa.controller.v1;

import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import com.backend.trawisa.service.HomeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.app.base.project.constant.BaseFinalConstant.PAGINATION.*;
import static com.app.base.project.constant.BaseFinalConstant.PAGINATION.SORT_DIRECTION;
import static com.backend.trawisa.constant.ApiConstant.*;

@RestController
@RequestMapping(HOME)
public class HomeController {

    private final HomeServiceImpl homeService;

    public HomeController(HomeServiceImpl homeService) {
        this.homeService = homeService;
    }

    @GetMapping(TOURNAMENT_LIST)
    public ResponseEntity<ApiResponse> getTournamentList(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION, required = false) String sortDirection,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "filter", defaultValue = "", required = false) String filter
    ) {
        ApiResponse apiResponse = homeService.getTournamentList(pageNumber, pageSize, sortBy, sortDirection,search,filter);
        return ResponseHandler.sendResponse(apiResponse);
    }

}
