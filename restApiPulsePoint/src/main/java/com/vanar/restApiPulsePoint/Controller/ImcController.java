package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.DTO.ImcResponse;
import com.vanar.restApiPulsePoint.DTO.UserRequest;
import com.vanar.restApiPulsePoint.Model.User;
import com.vanar.restApiPulsePoint.Service.ImcService;
import com.vanar.restApiPulsePoint.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imc")
public class ImcController {

    private ImcService imcService;
    private UserService service;

    public ImcController(UserService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Object> calculate(@RequestHeader String uuid,
                                 @RequestBody UserRequest updateDTO)
    {
        // Valida UUID
        User loggedUser = service.getByUuid(uuid);
        if (loggedUser == null) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }

        try {
            ImcResponse resultado = imcService.processIMC(updateDTO.getWeight(), updateDTO.getHeight());

            updateDTO.setImc(resultado.getImc());
            updateDTO.setImc_desc(resultado.getImc_desc());

            boolean updated = service.updateUser(uuid, updateDTO);
            if (!updated) {
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }

            return ResponseEntity.ok(resultado);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao calcular IMC: " + e.getMessage());
        }
    }
}
