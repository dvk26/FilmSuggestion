package com.machineLearning.filmSuggestionWeb.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.machineLearning.filmSuggestionWeb.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
//    @NotNull(message = "fullName ko thể bỏ trống!")
//    private String fullName;
    @NotNull(message = "userName ko thể bỏ trống!")
    private String userName;
    @NotNull(message = "email ko thể bỏ trống!")
    @Email(message = "email ko hợp lệ!")
    private String email;
    @NotNull
    private String password;
//    @Enumerated(EnumType.STRING)
//    private GenderEnum gender;
//    private Long age;
//    private String address;
    private String roleCode;
}
