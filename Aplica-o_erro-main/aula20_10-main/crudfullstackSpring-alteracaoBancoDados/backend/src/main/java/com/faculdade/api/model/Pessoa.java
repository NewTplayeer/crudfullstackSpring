package com.faculdade.api.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pessoa_curso",
        joinColumns = @JoinColumn(name = "pessoa_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos;

}