package com.exemplo.ufmaextensao.entity;

import com.exemplo.ufmaextensao.enums.StatusAproveitamento;
import com.exemplo.ufmaextensao.enums.StatusAproveitamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "aproveitamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aproveitamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aproveitamento")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_discente")
    private Discente discente;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "instituicao")
    private String instituicao;

    @Column(name = "horas")
    private Integer horas;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAproveitamento status;

    @Column(name = "certificado_path")
    private String certificadoPath;

    @ManyToOne
    @JoinColumn(name = "id_avaliador")
    private Usuario avaliador;

    @Column(name = "motivo_rejeicao")
    private String motivoRejeicao;

    @CreationTimestamp
    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "data_avaliacao")
    private LocalDate dataAvaliacao;
}