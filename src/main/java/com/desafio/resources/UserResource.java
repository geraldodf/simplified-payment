package com.desafio.resources;

import com.desafio.dtos.CreateUserDTO;
import com.desafio.dtos.UpdateUserDTO;
import com.desafio.models.User;
import com.desafio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ArrayList<User>> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneById(@PathVariable("id") Long id) {
        return this.userService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserDTO userDto) {
        return this.userService.create(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UpdateUserDTO userDTO) {
        return this.userService.update(id, userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        return this.userService.delete(id);
    }
}
