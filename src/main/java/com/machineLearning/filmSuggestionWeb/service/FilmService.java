package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;

import java.util.List;
import java.util.Map;

public interface FilmService {
    public FilmEntity saveFilm(Map<String, Object> film);
}
