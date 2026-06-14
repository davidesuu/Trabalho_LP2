package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ppc")
@Data
public class PPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Column(name = "ano_vigencia")
    private Integer anoVigencia;
    @Column(name = "carga_horaria")
    private Integer cargaHorariaTotal;
}
