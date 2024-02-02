package com.quizapp.service;

import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;

import java.util.List;

public interface UserScoreService {

    List<UserScoreDTO> getUserScores(User user);
}
