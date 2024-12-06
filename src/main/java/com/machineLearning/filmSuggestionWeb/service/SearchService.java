package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;

import java.util.List;

public interface SearchService {

    public List<FilmEntity> getResponseFromModel(String prompt);
}
