package com.vanar.restApiPulsePoint.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    @NotBlank
    private String uuid;

    @NotBlank
    private String uuidExpire;
}
