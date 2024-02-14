package com.quizapp.service.serviceImpl;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.entity.Token;
import com.quizapp.entity.User;
import com.quizapp.repository.TokenRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.AuthService;
import com.quizapp.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO signUp(UserDTO userDTO) {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();

        User createdUser = userRepository.save(user);
        return UserResponseDTO.builder()
                .id(createdUser.getId())
                .username(createdUser.getUsername())
                .build();
    }

    public AuthenticationResponse login(LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            User user = (User) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(user);
            revokeAllToken(user);
            saveUserToken(user, jwt);
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .build();
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                throw new BadCredentialsException("Incorrect username or password!");
            } else if (e instanceof DisabledException) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            }
            return null;
        }
    }


    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            userRepository.findByUsernameIgnoreCase(username).ifPresent(this::revokeAllToken);
        }
    }


    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .users(savedUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);

    }

    private void revokeAllToken(User user) {
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(user.getId());
        if (tokenList.isEmpty()) {
            return;
        }
        for (Token token : tokenList) {
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.save(token);
        }
    }
}
