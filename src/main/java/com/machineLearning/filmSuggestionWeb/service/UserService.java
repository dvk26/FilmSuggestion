package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;

import java.util.List;

public interface UserService {
    public void createUser(UserDTO userDTO);
    public void updateUser(UserDTO userDTO);
    public List<UserDTO>  fetchAllUsers();
    public UserDTO findById(Long id);
    public UserEntity findByUserName(String username);

    public void updateUserToken(String token, String userName);
    public UserEntity findByRefreshTokenAndUserName(String refreshToken, String userName);
}
