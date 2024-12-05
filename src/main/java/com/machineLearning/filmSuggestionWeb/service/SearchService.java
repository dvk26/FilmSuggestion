package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.model.FilmEntity;

import java.util.List;

public interface SearchService {

    public List<FilmEntity> getResponseFromModel(String prompt);
}
