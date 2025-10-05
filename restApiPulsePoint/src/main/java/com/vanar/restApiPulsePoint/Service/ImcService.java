package com.vanar.restApiPulsePoint.Service;

import com.vanar.restApiPulsePoint.DAO.DadosFormsDAO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImcService {

    private DadosFormsDAO dao;

    public ImcService(DadosFormsDAO dao) {
        this.dao = dao;
    }

    public Map<String, Object> processIMC(float weight, float height) throws Exception {
        try {
            float imc = dao.calc_imc(weight, height);
            String alert = dao.alert_imc(weight, height);

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("imc", imc);
            resultado.put("alert", alert);
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
