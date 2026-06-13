package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "discente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Data
public class Discente extends Usuario{
    @Column(name = "matricula")
    private String matricula;
    @Column(name = "semestre")
    private Integer semestre;

    @ManyToMany(mappedBy = "discentes")
    private List<Grupo> grupos;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    //@Column(name = "vinculo")
    //private Vinculo vinculo;
    //private boolean hasSpecialPermission;
}
