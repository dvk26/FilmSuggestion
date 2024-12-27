package com.machineLearning.filmSuggestionWeb.model;

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
@Table(name = "collections")
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @ManyToMany
    // @JoinTable(
    //         name = "film_collection",
    //         joinColumns = @JoinColumn(name = "collection_id"),
    //         inverseJoinColumns = @JoinColumn(name = "film_id")
    // )
    private List<FilmEntity> films;



    @OneToMany(mappedBy = "collection")
    private List<CollectionFilmEntity> collectionfilm;

}