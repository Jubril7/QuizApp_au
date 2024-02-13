package com.quizapp.entity;

import com.quizapp.enums.Role;
import com.quizapp.enums.Type;
import jakarta.persistence.*;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String questionText;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    @Column(nullable = false)
    private String answer;

    //could turn out to be an enum
    @Column(nullable = false)
    private String category;

    //could be enum too
    @Column(nullable = false)
    private String difficulty;

    @Enumerated(EnumType.STRING)
    private Type questionType;

}
