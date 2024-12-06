package com.machineLearning.filmSuggestionWeb.service;

import java.util.List;

import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;

public interface HistoryFilmService {
    
    public void saveListFilmSearched(HistoryEntity searchEntity, List<FilmEntity> listFilm);
    public List<FilmDTO> getListFilmSearched(HistoryEntity search);
}
