
package com.machineLearning.filmSuggestionWeb.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
