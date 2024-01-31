package com.quizapp.service.serviceImpl;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.entity.User;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO signUp(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(createdUser.getId());
        userResponseDTO.setUsername(createdUser.getUsername());
        return userResponseDTO;

    }

    @Override
    public void logout() {

    }

//    @Override
//    public AuthenticationResponse loginUser(LoginDTO loginDTO) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDTO.getUsername(),
//                        loginDTO.getPassword()
//                )
//        );
//        User users1 = userRepository.findByUsernameIgnoreCase(loginDTO.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginDTO.getUsername()));
//        String jwtToken = jwtService.generateToken(users1);
//        revokeAllToken(users1);
//
//        saveUserToken(users1, jwtToken);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
}
