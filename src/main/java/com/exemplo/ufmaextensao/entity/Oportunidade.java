package com.exemplo.ufmaextensao.entity;


import com.exemplo.ufmaextensao.Enum.Modalidade;
import com.exemplo.ufmaextensao.Enum.StatusOportunidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Oportunidade")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Oportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oportunidade")
    private Integer id;
    @Column(name = "Titulo")
    private String titulo;
    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_oportunidade")
    private TipoOportunidade tipoOportunidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobilidade")
    private Modalidade modalidade;

    @Column(name = "carga_horaria")
    private Integer carga_horaria;
    @Column(name = "vagas")
    private Integer vagas;
    @Column(name = "vagas_ocupadas")
    private Integer vagasOcupadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOportunidade status;

    @Column(name = "data_inicio")
    private LocalDate incio;
    @Column(name = "data_fim")
    private LocalDate fim;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel_oportunidade;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
}