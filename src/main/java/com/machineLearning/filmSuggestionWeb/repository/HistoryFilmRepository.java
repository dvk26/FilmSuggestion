package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.HistoryFilmEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryFilmRepository extends JpaRepository<HistoryFilmEntity, Long>{
    
    public boolean existsBySearch_IdAndFilm_Id(Long searchId, Long FilmId);
    public List<HistoryFilmEntity> findbySearch_Id(Long searchId);
}
