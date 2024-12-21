package com.machineLearning.filmSuggestionWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveCollectionFilmDTO {
    private Long film_id;
    private Long collection_id;
}
