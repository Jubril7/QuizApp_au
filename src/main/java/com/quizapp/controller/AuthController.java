package com.quizapp.controller;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginDTO loginDTO) {
        AuthenticationResponse response = userService.loginUser(loginDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    public void logout() {
        userService.logout();
    }
}
