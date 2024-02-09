package com.quizapp.dto;

import com.quizapp.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String phoneNumber;

    private String password;

    private Role role;


}
