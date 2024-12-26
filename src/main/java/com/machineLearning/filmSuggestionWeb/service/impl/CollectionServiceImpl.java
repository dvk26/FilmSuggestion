package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.config.MapperConfig;
import com.machineLearning.filmSuggestionWeb.dto.CollectionDTO;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import com.machineLearning.filmSuggestionWeb.model.CollectionEntity;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.repository.CollectionRepository;
import com.machineLearning.filmSuggestionWeb.repository.FilmRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.service.CollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;

    public CollectionServiceImpl(CollectionRepository collectionRepository, 
                                  UserRepository userRepository,
                                  FilmRepository filmRepository, 
                                  MapperConfig mapperConfig, 
                                  ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCollection(CollectionDTO collectionDTO) {
        CollectionEntity collectionEntity = modelMapper.map(collectionDTO, CollectionEntity.class);

        if (collectionDTO.getUserId() == null) {
            throw new GeneralAllException("User ID không được bỏ trống.");
        }

        // Gắn user vào collection
        collectionEntity.setUser(userRepository.findById(collectionDTO.getUserId())
                .orElseThrow(() -> new GeneralAllException("Không tìm thấy user với ID " + collectionDTO.getUserId())));
        collectionRepository.save(collectionEntity);
    }

    @Override
    public void updateCollection(CollectionDTO collectionDTO) {
        if (collectionDTO.getId() == null) {
            throw new GeneralAllException("Không tìm thấy collection với ID " + collectionDTO.getId());
        }

        CollectionEntity collectionEntity = collectionRepository.findById(collectionDTO.getId())
                .orElseThrow(() -> new GeneralAllException("Không tìm thấy collection với ID " + collectionDTO.getId()));

        collectionEntity.setName(collectionDTO.getName());
        collectionRepository.save(collectionEntity);
    }

    @Override
    public void deleteCollection(Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new GeneralAllException("Không tồn tại collection với ID " + id);
        }
        collectionRepository.deleteById(id);
    }

    @Override
    public List<CollectionDTO> fetchAllCollectionsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new GeneralAllException("Không tồn tại user với ID " + userId);
        }

        List<CollectionEntity> collections = collectionRepository.findByUserId(userId);
        List<CollectionDTO> result = new ArrayList<>();

        for (CollectionEntity collection : collections) {
            CollectionDTO dto = modelMapper.map(collection, CollectionDTO.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public void addFilmToCollection(Long collectionId, Long filmId) {
        CollectionEntity collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new GeneralAllException("Không tìm thấy collection với ID " + collectionId));

        FilmEntity film = filmRepository.findById(filmId)
                .orElseThrow(() -> new GeneralAllException("Không tìm thấy film với ID " + filmId));

        collection.getFilms().add(film);
        collectionRepository.save(collection);
    }

    @Override
    public CollectionDTO findById(Long id) {
        CollectionEntity collectionEntity = collectionRepository.findById(id)
                .orElseThrow(() -> new GeneralAllException("Không tìm thấy collection với ID " + id));
        return modelMapper.map(collectionEntity, CollectionDTO.class);
    }
}
