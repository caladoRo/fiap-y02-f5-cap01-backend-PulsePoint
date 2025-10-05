package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.Model.DadosForms;
import com.vanar.restApiPulsePoint.Service.ImcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/imc")
public class ImcController {

    private ImcService imcService;

    @PostMapping
    public Map<String, Object> calcular(@RequestBody DadosForms dados) throws Exception {
        return imcService.processIMC(dados.getWeight(), dados.getHeight());
    }
}
