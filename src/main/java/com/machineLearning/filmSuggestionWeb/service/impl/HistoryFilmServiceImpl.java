package com.machineLearning.filmSuggestionWeb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
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
    
    private final HistoryFilmRepository historyFilmRepository;
    private final FilmRepository filmRepository;

    public HistoryFilmServiceImpl( HistoryFilmRepository historyFilmRepository, FilmRepository filmRepository) {
        this.historyFilmRepository = historyFilmRepository;

        this.filmRepository = filmRepository;
    }

    @Override
    public void saveListFilmSearched(HistoryEntity historyEntity, List<FilmEntity> listFilm) {
      for (FilmEntity filmEntity : listFilm) {
        HistoryFilmEntity entity = new HistoryFilmEntity();
        entity.setHistory(historyEntity);
        entity.setFilm(filmEntity);
        historyFilmRepository.save(entity);
      }
    }

    @Override
    public List<FilmEntity> getListFilmSearched(HistoryEntity history) {
        List<FilmEntity> films = new ArrayList<>();
        List<HistoryFilmEntity> historyFilms = historyFilmRepository.findByHistory_Id(history.getId());
        for (HistoryFilmEntity historyFilmEntity : historyFilms) {
          films.add(historyFilmEntity.getFilm());
        }
        return films;
    }
}
