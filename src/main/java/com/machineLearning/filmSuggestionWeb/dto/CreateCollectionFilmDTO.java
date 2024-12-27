package com.machineLearning.filmSuggestionWeb.dto;

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
public class CreateCollectionFilmDTO {
    private Long film_id;
    private Long collection_id;
}