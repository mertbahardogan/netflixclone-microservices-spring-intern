package com.microservices.netflix.film.service.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "summary")
    private String summary;

    @NotNull
    @Column(name = "category")
    private String category;

    @NotNull
    @Column(name = "cover_photo")
    private String coverPhoto;

    @NotNull
    @Column(name = "time")
    private String time;

    @NotNull
    @Column(name = "speak_language")
    private String speakLanguage;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created")
    private TimeZone created; //??

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "edited")
    private TimeZone edited;

    @Column(name = "edited_by")
    private int editedBy;

    @Column(name = "deleted")
    private TimeZone deleted;

}
