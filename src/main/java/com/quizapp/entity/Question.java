package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

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
    @Column(nullable = false)
    private String option1;
    @Column(nullable = false)
    private String option2;
    @Column(nullable = false)
    private String option3;
    @Column(nullable = false)
    private String option4;
    @Column(nullable = false)
    private String answer;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String difficulty;

}
