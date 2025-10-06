package com.vanar.restApiPulsePoint.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImcResponse {

    @NotBlank
    @Email
    private float weight;

    @NotBlank
    private float height;

    @NotBlank
    private float imc;

    @NotBlank
    private String imc_desc;
}
