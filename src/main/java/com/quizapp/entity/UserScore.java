package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int score;


}
