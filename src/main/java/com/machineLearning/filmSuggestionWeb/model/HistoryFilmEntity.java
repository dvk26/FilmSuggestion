package com.machineLearning.filmSuggestionWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="history_film")
public class HistoryFilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="history_id")
    private HistoryEntity history;
    @ManyToOne
    @JoinColumn(name="film_id")
    private FilmEntity film;
}
