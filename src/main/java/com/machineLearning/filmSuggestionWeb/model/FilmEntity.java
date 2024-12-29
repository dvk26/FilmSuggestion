package com.machineLearning.filmSuggestionWeb.model;


import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
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
    @Column(columnDefinition = "MEDIUMTEXT")
    private String overview;
    private Boolean isLiked;
    private Boolean isDisLiked;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "film")
    private List<HistoryFilmEntity> historyFilm;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().get();
    }
}
