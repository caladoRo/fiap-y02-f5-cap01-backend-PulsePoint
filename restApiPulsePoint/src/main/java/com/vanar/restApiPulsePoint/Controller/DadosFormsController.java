package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.DAO.DadosFormsDAO;
import com.vanar.restApiPulsePoint.Model.DadosForms;
import com.vanar.restApiPulsePoint.Service.ImcService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dados-forms")
public class DadosFormsController {

    @Autowired
    private DadosFormsDAO dao;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid DadosForms dados){
        try {
            dao.save(dados);
            return ResponseEntity.ok("Salvo");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao persistir os dados");
        }

    }


}
