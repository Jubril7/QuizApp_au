package com.quizapp.service;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<UserResponseDTO> signUp(UserDTO userDTO);
    void logout();
    AuthenticationResponse loginUser(LoginDTO loginDTO);
}
