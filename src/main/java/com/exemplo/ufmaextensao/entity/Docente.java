package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Data
public class Docente extends Usuario{
    @Column(name = "siape")
    private String siape;
    @Column(name = "departamento")
    private String departamento;

    @OneToMany(mappedBy = "responsavel")
    private List<Grupo> grupos;
}
