package com.backend.trawisa.model.request;

import com.app.base.project.utils.RegexPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResendOtpRequest {

    @NotBlank
    @Pattern(regexp = RegexPattern.Email)
    private String email;


}
