package com.machineLearning.filmSuggestionWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="HistorySearch")
public class HistoryFilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="history")
    private HistoryEntity history;
    @ManyToOne
    @JoinColumn(name="film")
    private FilmEntity film;
}
