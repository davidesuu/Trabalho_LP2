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
    @Column(name = "descri��o")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_oportunidade")
    private TipoOportunidade oportunidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobalidade")
    private Modalidade modalidade;

    @Column(name = "carga_horaria")
    private Integer carga_horaria;
    @Column(name = "vagas")
    private Integer vagas;
    @Column(name = "vagasOcupadas")
    private Integer vagasOcupadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOportunidade status;

    @Column(name = "data_inicio")
    private LocalDate incio;
    @Column(name = "data_fim")
    private LocalDate fim;

    @ManyToMany(mappedBy = "Usuario")
    @JoinColumn(name = "autor")
    private List<Usuario> autor;

    @OneToMany(mappedBy = "Docentes")
    @JoinColumn(name = "responsavel")
    private Docente responsavel_oportunidade;


}