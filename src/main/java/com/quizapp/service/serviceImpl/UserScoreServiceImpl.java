package com.quizapp.service.serviceImpl;

import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserScoreServiceImpl implements UserScoreService {

    private final UserScoreRepository userScoreRepository;

    @Override
    public List<UserScoreDTO> getUserScores(User user) {
        try {
            List<UserScore> userScores = userScoreRepository.findByUser(user);

            return userScores.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving quiz scores", e);
        }
    }

    private UserScoreDTO mapToDTO(UserScore userScore) {
        UserScoreDTO dto = new UserScoreDTO();
        dto.setScore(userScore.getScore());
        return dto;
    }

}
