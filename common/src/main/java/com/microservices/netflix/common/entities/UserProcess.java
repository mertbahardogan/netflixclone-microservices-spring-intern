package com.microservices.netflix.common.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_processes")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @ManyToOne()  //fetch = FetchType.EAGER
    @JoinColumn(name = "film_id")
    private Film film;
}
