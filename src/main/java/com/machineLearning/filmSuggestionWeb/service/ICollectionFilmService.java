package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.CreateCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.model.CollectionFilmEntity;

import java.util.List;

public interface CollectionFilmService {
    public Boolean CreateCollectionFilms(CreateCollectionFilmDTO createCollectionFilmDTO);
    public List<CollectionFilmDTO> GetAllBy_UserId_CollectionId(long userId, long collectionId);
    public Boolean RemoveCollectionFilmsById(long Id);
    public Boolean RemoveCollectionFilmsByCollectionId(long CollectionId);
}