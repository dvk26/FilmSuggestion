package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.service.FilmImageService;
import org.springframework.beans.factory.annotation.Value;

public class FilmImageServiceImpl implements FilmImageService {

    @Value("{tmdb.url}")
    private String url;
    @Value("{tmdb.api_key}")
    private String apiKey;
    
    @Override
    public String getImageFromTitleAndYear(String title, Long year) {
        return null;
    }
}
