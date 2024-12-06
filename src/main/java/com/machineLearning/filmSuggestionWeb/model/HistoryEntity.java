package com.machineLearning.filmSuggestionWeb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user")
    private UserEntity user;
    private String promptSearch;
    private LocalDateTime dateSearch;

    @OneToMany(mappedBy="history")
    List<HistoryFilmEntity> historyFilm;
}
