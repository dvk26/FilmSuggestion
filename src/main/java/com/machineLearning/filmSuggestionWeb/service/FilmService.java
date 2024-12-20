package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;

import java.util.List;
import java.util.Map;

public interface FilmService {
    public FilmEntity extractFilmFromResponse(Map<String, Object> film);
    public void likeFilm(Long id);
    public void disLikeFilm(Long id);
    public List<FilmEntity> saveAll(List<FilmEntity> filmEntities);
}
