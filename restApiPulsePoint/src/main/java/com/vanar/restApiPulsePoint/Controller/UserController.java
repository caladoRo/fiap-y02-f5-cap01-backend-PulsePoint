package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.DTO.LoginRequest;
import com.vanar.restApiPulsePoint.DTO.UserRequest;
import com.vanar.restApiPulsePoint.Model.User;
import com.vanar.restApiPulsePoint.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET /users -> lista todos (sem expor password graças ao @JsonIgnore)
    @GetMapping
    public List<User> list() {
        return service.listAll();
    }

    // GET /users/{id} -> busca por id
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id, @RequestHeader String uuid) {

        service.isUuidValid(uuid);

        return service.getById(id);
    }

    // POST /users -> cria novo usuário
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserRequest body) {

        User created = service.create(body);

        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(created);
    }

}