package com.machineLearning.filmSuggestionWeb.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="films")
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genres;
    private Long year;
    private Double imdbRating;
    private Long time;
    private String overview;
    private Boolean isLiked;
    private Boolean isDisLiked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "film")
    private List<HistoryFilmEntity> historyFilm;
}
