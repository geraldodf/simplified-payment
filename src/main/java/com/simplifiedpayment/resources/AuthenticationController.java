package com.simplifiedpayment.resources;

import com.simplifiedpayment.data.dtos.AuthenticationDTO;
import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.LoginResponseDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.infra.security.TokenService;
import com.simplifiedpayment.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CreateUserDTO createUserDTO) {
        if (this.userService.getOneByEmail(createUserDTO.email()) != null) return ResponseEntity.badRequest().build();

        this.userService.create(createUserDTO);

        return ResponseEntity.ok().build();
    }
}
