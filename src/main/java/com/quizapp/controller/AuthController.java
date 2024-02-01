package com.quizapp.controller;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.service.AuthService;
import com.quizapp.service.Jwt.UserDetailsServiceImpl;
import com.quizapp.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;


    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        UserResponseDTO createdUser = authService.signUp(userDTO);
        if (createdUser == null){
            return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt);

    }


    @PostMapping("logout")
    public void logout() {
        authService.logout();
    }
}
