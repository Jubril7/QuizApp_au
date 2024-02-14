package com.quizapp.service.serviceImpl;

import com.quizapp.dto.AuthenticationResponse;
import com.quizapp.dto.LoginDTO;
import com.quizapp.dto.UserDTO;
import com.quizapp.dto.UserResponseDTO;
import com.quizapp.entity.User;
import com.quizapp.enums.Role;
import com.quizapp.repository.TokenRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void signUp() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Brad");
        userDTO.setLastName("Traversy");
        userDTO.setUsername("Bradt");
        userDTO.setPhoneNumber("1234567890");
        userDTO.setPassword("password123");
        userDTO.setRole(Role.valueOf("ROLE_USER"));

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(userDTO.getUsername());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO responseDTO = authService.signUp(userDTO);

        assertEquals(savedUser.getId(), responseDTO.getId());
        assertEquals(savedUser.getUsername(), responseDTO.getUsername());

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(savedUser.getId(), responseDTO.getId());
        assertEquals(savedUser.getUsername(), responseDTO.getUsername());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User createdUser = userCaptor.getValue();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(userDTO.getPassword(), createdUser.getPassword()));
    }

    @Test
    void login() throws IOException {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("Brad");
        loginDTO.setPassword("Traversy");

        User user = new User();
        user.setId(1L);
        user.setUsername(loginDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(loginDTO.getPassword()));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any(User.class))).thenReturn("jwtToken");

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findFirstByUsername(anyString())).thenReturn(user);

        AuthenticationResponse authenticationResponse = authService.login(loginDTO, response);

        assertNotNull(authenticationResponse);
        assertNotNull(authenticationResponse.getToken());


        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtil, times(1)).generateToken(any(User.class));
    }

    @Test
    void logout() {
        String username = "zlatan";
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));

        AuthServiceImpl authService = mock(AuthServiceImpl.class);
        authService.logout();

        // Verify that the user was found in the repository
        verify(userRepository).findByUsernameIgnoreCase(username);

        // Verify that authService.revokeAllToken(user) was called
        verify(authService).revokeAllToken(user);
    }
}