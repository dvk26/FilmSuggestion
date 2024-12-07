package com.machineLearning.filmSuggestionWeb.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {
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
    private Long userId;
}
