package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Data
public class Docente extends Usuario {
    @Column(name = "siape")
    private String siape;
    @Column(name = "departamento")
    private String departamento;

    @ManyToOne
    @JoinColumn(name = "id_oportunidade")
    private Oportunidade oportunidade;

    @OneToMany(mappedBy = "responsavel_grupo")
    private List<Grupo> grupos;
}
