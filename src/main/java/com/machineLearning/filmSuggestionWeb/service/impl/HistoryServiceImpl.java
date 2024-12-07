package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.HistoryRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.service.HistoryService;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(UserRepository userRepository, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public HistoryEntity save(String prompt) {
        HistoryEntity history = new HistoryEntity();
        history.setPrompt(prompt);
        history.setDate(LocalDateTime.now());

        String userName = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";
        UserEntity userLogin= userRepository.findByUserName(userName);
        if (!historyRepository.existsByPromptAndUser_Id(prompt, userLogin.getId())) {
            history.setUser(userLogin);
            history=historyRepository.save(history);
            return history;
        }
        return historyRepository.findByPromptAndUser_Id(prompt, userLogin.getId());
    }

    @Override
    public boolean existsByPromptAndUser_Id(String prompt, Long userId) {
        return historyRepository.existsByPromptAndUser_Id(prompt,userId);
    }

    @Override
    public HistoryEntity findByPromptAndUser_Id(String prompt, Long userId) {
        return historyRepository.findByPromptAndUser_Id(prompt,userId);
    }
}
