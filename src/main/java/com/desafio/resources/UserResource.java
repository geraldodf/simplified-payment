package com.desafio.resources;

import com.desafio.dtos.UpdateUserDTO;
import com.desafio.models.User;
import com.desafio.services.UserService;
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
    public ArrayList<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOneById(@PathVariable("id") Long id) {
        return this.userService.getOneById(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return this.userService.create(user);
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
