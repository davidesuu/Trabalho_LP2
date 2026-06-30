package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_autor")
    private String nomeAutor;

    @Column(name = "matricula_autor")
    private String matriculaAutor;

    @Column(name = "nome_afetado")
    private String nomeAfetado;

    @Column(name = "matricula_afetado")
    private String matriculaAfetado;

    @Column(name = "nome_grupo")
    private String nomeGrupo;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "operacao")
    private String operacao;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
