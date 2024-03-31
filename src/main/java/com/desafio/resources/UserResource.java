package com.desafio.resources;

import com.desafio.data.dtos.CreateUserDTO;
import com.desafio.data.dtos.ReadUserDTO;
import com.desafio.data.dtos.UpdateUserDTO;
import com.desafio.data.models.User;
import com.desafio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<ReadUserDTO> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOneById(@PathVariable("id") Long id) {
        return this.userService.getOneById(id);
    }

    @GetMapping("/email")
    public User getOneByEmail(@RequestParam("email") String email) {
        return this.userService.getOneByEmail(email);
    }

    @GetMapping("/document")
    public User getOneByDocument(@RequestParam("document") String document) {
        return this.userService.getOneByDocument(document);
    }

    @PostMapping
    public User create(@RequestBody @Valid CreateUserDTO userDto) {
        return this.userService.create(userDto);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody UpdateUserDTO userDTO) {
        return this.userService.update(id, userDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }
}
