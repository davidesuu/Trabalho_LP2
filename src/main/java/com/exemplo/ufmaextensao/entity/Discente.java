package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "discente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Data
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Discente extends Usuario {
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "semestre")
    private Integer semestre;

    @Column(name = "CH")
    private Float banco_de_horas;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Column(name = "ch_total_cumprida")
    private Integer ch_total_cumprida;
}
