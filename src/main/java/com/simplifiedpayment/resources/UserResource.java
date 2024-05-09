package com.simplifiedpayment.resources;

import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.ReadUserDTO;
import com.simplifiedpayment.data.dtos.UpdateUserDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<ReadUserDTO> getAll() {
        logger.info("Requisição para buscar todos os usuários.");
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneById(@PathVariable("id") Long id) {
        logger.info("Requisição para buscar usuário por ID: {}", id);
        User user = this.userService.getOneById(id);
        if (user == null) {
            logger.error("Usuário com ID {} não encontrado.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/document")
    public ResponseEntity<User> getOneByDocument(@RequestParam("document") String document) {
        logger.info("Requisição para buscar usuário por documento: {}", document);
        User user = this.userService.getOneByDocument(document);
        if (user == null) {
            logger.warn("Usuário com documento {} não encontrado.", document);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserDTO userDto) {
        logger.info("Requisição para criar um novo usuário.");
        User user = this.userService.create(userDto);
        if (user == null) {
            logger.error("Falha ao criar o usuário.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        logger.info("Usuário criado com sucesso. ID: {}", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UpdateUserDTO userDTO) {
        logger.info("Requisição para atualizar usuário com ID: {}", id);
        User updatedUser = this.userService.update(id, userDTO);
        if (updatedUser == null) {
            logger.error("Usuário com ID {} não encontrado para atualização.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("Usuário atualizado com sucesso. ID: {}", updatedUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        logger.info("Requisição para deletar usuário com ID: {}", id);
        boolean deleted = this.userService.delete(id);
        if (!deleted) {
            logger.error("Usuário com ID {} não encontrado para deleção.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        logger.info("Usuário deletado com sucesso. ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
