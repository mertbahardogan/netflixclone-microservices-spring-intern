package com.microservices.netflix.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "films")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "userProcesses" })
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

    @JsonIgnore
    @Column(name = "created")
    private OffsetDateTime created;

    @JsonIgnore
    @Column(name = "created_by")
    private int createdBy;

    @JsonIgnore
    @Column(name = "edited")
    private OffsetDateTime edited;

    @Column(name = "edited_by")
    private int editedBy;

    @JsonIgnore
    @Column(name = "deleted")
    private OffsetDateTime deleted;

    @OneToMany(mappedBy = "film")
    private List<UserProcess> userProcesses;
}