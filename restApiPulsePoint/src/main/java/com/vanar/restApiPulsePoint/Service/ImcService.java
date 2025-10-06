package com.vanar.restApiPulsePoint.Service;

import com.vanar.restApiPulsePoint.DTO.ImcResponse;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImcService {

    public ImcResponse processIMC(float weight, float height) {
        float imc = calcIMC(weight, height);
        String alert = alertIMC(imc);

        ImcResponse response = new ImcResponse();
        response.setHeight(height);
        response.setWeight(weight);
        response.setImc(imc);
        response.setImc_desc(alert);

        return response;
    }

    private float calcIMC(float weight, float height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Altura deve ser maior que zero.");
        }
        return weight / (height * height);
    }

    private String alertIMC(float imc) {
        if (imc < 18.5f) {
            return "Abaixo do peso";
        } else if (imc < 24.9f) {
            return "Peso normal";
        } else if (imc < 29.9f) {
            return "Sobrepeso";
        } else if (imc < 34.9f) {
            return "Obesidade grau I";
        } else if (imc < 39.9f) {
            return "Obesidade grau II";
        } else {
            return "Obesidade grau III (mórbida)";
        }
    }

}
