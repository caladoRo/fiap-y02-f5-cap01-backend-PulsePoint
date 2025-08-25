package com.vanar.restApiPulsePoint.Service;

import com.vanar.restApiPulsePoint.DTO.LoginRequest;
import com.vanar.restApiPulsePoint.DTO.UserRequest;
import com.vanar.restApiPulsePoint.Model.User;
import com.vanar.restApiPulsePoint.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(UserRequest userDTO) {

        if (repo.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        User user = new User();

        String newUuid = UUID.randomUUID().toString();
        user.setUuid(newUuid);

        user.setUuidExpire(String.valueOf(System.currentTimeMillis() + 3600000)); // expira em 1 hora

        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setActive(true);
        // TODO: em produção, fazer hash da senha aqui antes de salvar
        return repo.save(user);
    }

    public List<User> listAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = repo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        if (!user.isActive())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario inativo");
        }

        String newUuid = UUID.randomUUID().toString();
        user.setUuid(newUuid);

        // define um tempo de expiração no uuid_expire
        user.setUuidExpire(String.valueOf(System.currentTimeMillis() + 3600000)); // expira em 1 hora

        //Salva no banco
        repo.save(user);

        return user;
    }

    public boolean isUuidValid(String uuid) {
        try {
            User user = repo.findByUuid(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login expirado"));

            long expireAt = Long.parseLong(user.getUuidExpire());

            if (expireAt < System.currentTimeMillis())
            {
                throw (new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login expirado"));
            }

            return true; // ainda não expirou

        } catch (NumberFormatException e) {
            throw (new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Dados inválidos")); // dado inválido
        }
    }
}
