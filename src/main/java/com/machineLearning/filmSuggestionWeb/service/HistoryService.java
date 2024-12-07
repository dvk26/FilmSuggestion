package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;

import java.util.List;

public interface HistoryService {
    public HistoryEntity save(String prompt);
    public boolean existsByPromptAndUser_Id(String prompt, Long userId);
    public HistoryEntity findByPromptAndUser_Id(String prompt, Long userId);
}
