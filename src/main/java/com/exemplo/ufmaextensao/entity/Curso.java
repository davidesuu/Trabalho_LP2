package com.exemplo.ufmaextensao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "curso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codigo")
    private Integer codigo;

    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<PPC> ppcs;

    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<Discente> discentes;
}
