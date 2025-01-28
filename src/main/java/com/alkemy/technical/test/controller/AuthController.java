package com.alkemy.technical.test.controller;

import com.alkemy.technical.test.dtos.requests.LoginRequestDTO;
import com.alkemy.technical.test.dtos.responses.TokenResponseDTO;
import com.alkemy.technical.test.dtos.requests.RegisterRequestDTO;
import com.alkemy.technical.test.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody final RegisterRequestDTO registerRequestDTO){
        log.info("endpoint register");
        final TokenResponseDTO token = this.authService.register(registerRequestDTO);
        return ResponseEntity.ok(token);

    }

    @PostMapping("/login")
    public  ResponseEntity<TokenResponseDTO> authenticate(@RequestBody final LoginRequestDTO loginRequestDTO){
        log.info("Endpoint login");
        final TokenResponseDTO token = this.authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }

}
