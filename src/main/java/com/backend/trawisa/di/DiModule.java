package com.backend.trawisa.di;


import com.app.base.project.base_config.LocalizationConfig;
import com.app.base.project.utils.LocalizationUtils;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.apiResponse.ApiDataResponse;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.backend.trawisa.exception.ApiValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DiModule {


    @Primary
    @Bean
    public ApiResponse getApiResponse() {
        return new ApiResponse();
    }

    @Bean
    public ApiDataResponse getApiDataResponse() {
        return new ApiDataResponse();
    }

    @Bean
    public MultiLangMessage getMultiLangMessage() {
        return new MultiLangMessage();
    }

    @Bean
    public ApiValidationException getApiValidation() {
        return new ApiValidationException();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public LocalizationConfig getLocalization(){return  new LocalizationConfig();}

    @Bean
    public LocalizationUtils getLocalizationUtils(){return new LocalizationUtils();}

}
