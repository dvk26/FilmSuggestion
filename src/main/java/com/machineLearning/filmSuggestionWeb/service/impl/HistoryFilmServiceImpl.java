package com.machineLearning.filmSuggestionWeb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryFilmEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.FilmRepository;
import com.machineLearning.filmSuggestionWeb.repository.HistoryRepository;
import com.machineLearning.filmSuggestionWeb.repository.HistoryFilmRepository;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import com.machineLearning.filmSuggestionWeb.service.HistoryFilmService;

@Service
public class HistoryFilmServiceImpl implements HistoryFilmService {
    
    private final HistoryFilmRepository listFilmSearchedRepository;
    private final FilmRepository filmRepository;

    public HistoryFilmServiceImpl(HistoryFilmRepository listFilmSearchedRepository, FilmRepository filmRepository) {
        this.listFilmSearchedRepository = listFilmSearchedRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public void saveListFilmSearched(HistoryEntity searchEntity, List<FilmEntity> listFilm) {
      for (FilmEntity filmEntity : listFilm) {
        HistoryFilmEntity entity = new HistoryFilmEntity();
        entity.setSearch_id(searchEntity);
        entity.setFilm__id(filmEntity);
        listFilmSearchedRepository.save(entity);
        listFilmSearchedRepository.flush();
      }
    }

    @Override
    public List<FilmEntity> getListFilmSearched(HistoryEntity search) {
        List<FilmEntity> films = new ArrayList<>();
        List<HistoryFilmEntity> filmSearcheds = listFilmSearchedRepository.findbySearch_Id(search.getId());

        for (HistoryFilmEntity listFilmSearchedEntity : filmSearcheds) {
          films.add(listFilmSearchedEntity.getFilm__id());
        }
        return films;
    }
}
