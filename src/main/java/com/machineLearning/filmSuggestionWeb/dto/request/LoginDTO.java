package com.machineLearning.filmSuggestionWeb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotNull
    @NotBlank
    private String userName;
    @NotNull
    @NotBlank
    private String password;
}
