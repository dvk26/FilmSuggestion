package com.machineLearning.filmSuggestionWeb.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="histories")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    private String prompt;
    private LocalDateTime date;

    @OneToMany(mappedBy="history")
    private List<HistoryFilmEntity> historyFilm;
}
