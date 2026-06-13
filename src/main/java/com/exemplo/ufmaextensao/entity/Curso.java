package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "curso")
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long cursoId;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codigo")
    private Integer codigo;

    @OneToMany(mappedBy = "curso")
    private List<Discente> discentes;
}
