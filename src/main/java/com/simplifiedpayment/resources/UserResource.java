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
    public ResponseEntity<User> getOneById(@PathVariable("id") Long id) {
        User user = this.userService.getOneById(id);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/document")
    public ResponseEntity<User> getOneByDocument(@RequestParam("document") String document) {
        User user = this.userService.getOneByDocument(document);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserDTO userDto) {
        User user = this.userService.create(userDto);
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UpdateUserDTO userDTO) {
        User createdUser = this.userService.update(id, userDTO);
        if (createdUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean deleted = this.userService.delete(id);
        if(!deleted) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
