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

    // POST /users -> cria novo usuário
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserRequest body) {

        User created = service.create(body);

        return ResponseEntity
                .created(URI.create("/users/" + created.getId()))
                .body(created);
    }

    // GET /users -> lista todos (sem expor password graças ao @JsonIgnore)
    @GetMapping
    public List<User> list(@RequestHeader String uuid) {

        service.isUuidValid(uuid);

        service.isAdmin(uuid);

        return service.listAll();
    }

    // GET /users/{id} -> busca por id
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id, @RequestHeader String uuid) {

        service.isUuidValid(uuid);

        service.isAdmin(uuid);

        return service.getById(id);
    }

    @GetMapping("/logged")
    public User getByUuid(@RequestHeader String uuid) {

        service.isUuidValid(uuid);

        return service.getByUuid(uuid);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(
            @RequestHeader String uuid,
            @RequestBody UserRequest updateDTO ) {

        // Valida UUID
        User loggedUser = service.getByUuid(uuid);
        if (loggedUser == null) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }

        // Apenas o próprio usuário ou admin pode alterar
        //if (!loggedUser.getId().equals(id) && !"admin".equalsIgnoreCase(loggedUser.getRole())) {
        //    return ResponseEntity.status(403).body("Você não tem permissão para atualizar este usuário");
        //}

        boolean updated = service.updateUser(uuid, updateDTO);
        if (!updated) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestHeader String uuid) {

        service.isUuidValid(uuid);

        // Recupera o usuário pelo UUID
        User loggedUser = service.getByUuid(uuid);
        if (loggedUser == null) {
            return ResponseEntity.status(401).body("Token inválido ");
        }

        // Só permite deletar se for admin
        //if (!"admin".equalsIgnoreCase(loggedUser.getRole())) {
        //    return ResponseEntity.status(403).body("Você não tem permissão para deletar este usuário");
        //}

        boolean deleted = service.deleteUser(loggedUser.getId());
        if (!deleted) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

}