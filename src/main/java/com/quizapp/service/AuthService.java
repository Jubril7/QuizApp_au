package com.quizapp.service;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    UserResponseDTO signUp(UserDTO userDTO);
    AuthenticationResponse login(LoginDTO loginDTO, HttpServletResponse response) throws IOException;

    void logout();


}
