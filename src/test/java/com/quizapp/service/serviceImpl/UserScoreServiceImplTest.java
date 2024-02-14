package com.quizapp.service.serviceImpl;

import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.repository.UserScoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserScoreServiceImplTest {
    @Test
    void getUserScores_Success() {

        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        UserScoreServiceImpl userScoreService = new UserScoreServiceImpl(userScoreRepository);

        User user = new User();

        List<UserScore> userScores = List.of(new UserScore(1L, user, 100));
        when(userScoreRepository.findByUser(user)).thenReturn(userScores);

        List<UserScoreDTO> result = userScoreService.getUserScores(user);

        assertEquals(userScores.size(), result.size());
        assertEquals(userScores.get(0).getScore(), result.get(0).getScore());
    }

    @Test
    void getUserScores_DataAccessException() {
        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        UserScoreServiceImpl userScoreService = new UserScoreServiceImpl(userScoreRepository);

        User user = new User();

        when(userScoreRepository.findByUser(user)).thenThrow(new DataAccessResourceFailureException("Data access exception"));

        assertThrows(org.hibernate.service.spi.ServiceException.class, () -> userScoreService.getUserScores(user));
    }
}