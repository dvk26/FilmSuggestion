package com.machineLearning.filmSuggestionWeb.service;

import com.machineLearning.filmSuggestionWeb.dto.CollectionDTO;
import com.machineLearning.filmSuggestionWeb.model.CollectionEntity;

import java.util.List;

public interface CollectionService {
    public void createCollection(CollectionDTO collectionDTO);
    public void updateCollection(CollectionDTO collectionDTO);
    public List<CollectionDTO> fetchAllCollectionsByUserId(Long userId);
    public void deleteCollection(Long id);
}