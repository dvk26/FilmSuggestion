package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    public boolean existsByUserName(String userName);
    public UserEntity findByUserName(String userName);
    public UserEntity findByRefreshTokenAndUserName(String refreshToken, String userName);
}
