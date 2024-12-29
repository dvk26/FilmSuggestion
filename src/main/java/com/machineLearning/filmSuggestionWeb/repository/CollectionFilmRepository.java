package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.CollectionFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Thêm import đúng cho @Param
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionFilmRepository extends JpaRepository<CollectionFilmEntity, Long> {

        @Query("SELECT cf FROM CollectionFilmEntity cf " +
                        "JOIN cf.film f " +
                        "JOIN cf.collection c " +
                        "WHERE f.user.id = :userId AND c.id = :collectionId")
        List<CollectionFilmEntity> findByCollectionIdAndUserId(
                        @Param("userId") Long userId,
                        @Param("collectionId") Long collectionId);

        @Query("SELECT cf.id FROM CollectionFilmEntity cf " +
                        "JOIN cf.film f " +
                        "JOIN cf.collection c " +
                        "WHERE f.id = :filmId AND c.id = :collectionId")
        Long findIdbyCollection_Id_Film_Id(
                        @Param("filmId") Long filmId,
                        @Param("collectionId") Long collectionId);
                
        @Query("SELECT cf.id FROM CollectionFilmEntity cf " +
                        "JOIN cf.film f " +
                        "WHERE f.id = :filmId")
        List<Long> findAllByFilmId(
                        @Param("filmId") Long filmId
        );
        

        boolean existsByFilm_IdAndCollection_Id(Long filmId, Long collectionId);

        List<CollectionFilmEntity> findByCollection_Id(long collectionId);
        @Query("SELECT c.id FROM CollectionFilmEntity cf " +
                "JOIN cf.collection c " +
                "JOIN c.user u " +
                "WHERE u.id = :userId " +
                "AND cf.film.id= :filmId ")

        List<Long> findAllByCollectionIdByFilmIdAndUserId(
                @Param("filmId") Long filmId, 
                @Param("userId") Long userId
        );

}
