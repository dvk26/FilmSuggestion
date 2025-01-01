package com.machineLearning.filmSuggestionWeb.model;

import com.machineLearning.filmSuggestionWeb.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String fullName;
    private String userName;
    private String email;
    private String password;
//    @Enumerated(EnumType.STRING)
//    private GenderEnum gender;
//    private Long age;
//    private String address;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<FilmEntity> films;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<HistoryEntity> history;
}

