package com.quizapp.controller;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.exception.UserRegistrationException;
import com.quizapp.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        try {
            UserResponseDTO createdUser = authService.signUp(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserRegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
            return authService.login(loginDTO,response);
    }

    @PostMapping("logout")
    public void logout() {
        authService.logout();
    }
}
