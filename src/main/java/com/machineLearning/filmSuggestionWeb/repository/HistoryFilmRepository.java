package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.HistoryFilmEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryFilmRepository extends JpaRepository<HistoryFilmEntity, Long>{
    
    public boolean existsByHistory_IdAndFilm_Id(Long historyId, Long filmId);
    public List<HistoryFilmEntity> findByHistory_Id(Long historyId);
}
