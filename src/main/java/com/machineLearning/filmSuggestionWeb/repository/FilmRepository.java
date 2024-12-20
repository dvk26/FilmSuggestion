package com.machineLearning.filmSuggestionWeb.repository;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity,Long> {
    public boolean existsByTitleAndUser_Id(String title, Long userId);
    public FilmEntity findByTitleAndUser_Id(String title, Long userId);

    public List<FilmEntity> findByIsLikedOrderByCreatedAt(Boolean isLiked);
    public List<FilmEntity> findByIsDisLikedOrderByCreatedAt(Boolean isDisLiked);
}


