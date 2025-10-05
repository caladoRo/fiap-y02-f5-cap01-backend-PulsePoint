package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.DTO.LoginRequest;
import com.vanar.restApiPulsePoint.DTO.LoginResponse;
import com.vanar.restApiPulsePoint.Model.User;
import com.vanar.restApiPulsePoint.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    // POST /login -> Retorna UUID válido e valor para expirar
    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = service.authenticate(request);

        LoginResponse response = new LoginResponse();

        response.setUuid(user.getUuid());
        response.setUuidExpire(user.getUuidExpire());

        return ResponseEntity.ok(response);
    }
}
