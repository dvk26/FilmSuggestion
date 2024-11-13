package com.machineLearning.filmSuggestionWeb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResLoginDTO {

    @JsonProperty("access_token")
    private String accessToken;
    private UserDTO user;
}
