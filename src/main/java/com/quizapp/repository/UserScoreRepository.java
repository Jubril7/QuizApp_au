package com.quizapp.repository;

import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {

    List<UserScore> findByUser(User user);
}
