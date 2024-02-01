package com.quizapp.dto;

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


}
