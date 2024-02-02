package com.quizapp.service.serviceImpl;

import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserScoreImpl implements UserScoreService {

    private final UserScoreRepository userScoreRepository;

    @Override
    public List<UserScoreDTO> getUserScores(User user) {
        try {
            List<UserScore> userScores = userScoreRepository.findByUser(user);

            List<UserScoreDTO> userScoreDTOs = new ArrayList<>();
            for (UserScore userScore : userScores) {
                UserScoreDTO dto = new UserScoreDTO();
                dto.setScore(userScore.getScore());
                userScoreDTOs.add(dto);
            }

            return userScoreDTOs;
        } catch (Exception e) {
            throw new ServiceException("Error retrieving quiz scores", e);

        }

    }
}
