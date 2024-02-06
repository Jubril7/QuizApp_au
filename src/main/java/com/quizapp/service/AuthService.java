package com.quizapp.service;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    UserResponseDTO signUp(UserDTO userDTO);
    public AuthenticationResponse login(LoginDTO loginDTO, HttpServletResponse response) throws IOException;

    void logout();


}
