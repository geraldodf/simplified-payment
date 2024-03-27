package com.desafio.resources;

import com.desafio.dtos.CreateUserDTO;
import com.desafio.dtos.UpdateUserDTO;
import com.desafio.models.User;
import com.desafio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOneById(@PathVariable("id") Long id) {
        return this.userService.getOneById(id);
    }

    @PostMapping
    public User create(@RequestBody @Valid CreateUserDTO userDto) {
        return this.userService.create(userDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody UpdateUserDTO userDTO) {
       this.userService.update(id, userDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }
}
