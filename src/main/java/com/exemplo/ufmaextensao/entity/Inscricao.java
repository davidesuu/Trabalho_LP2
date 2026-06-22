package com.exemplo.ufmaextensao.entity;

import com.exemplo.ufmaextensao.Enum.StatusInscricao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inscricao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_oportunidade")
    private Integer oportunidadeId;

    @Column(name = "id_discente")
    private Long discenteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_inscricao")
    private StatusInscricao status;

    @Column(name = "motivacao")
    private String motivacao;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public void ajeitar(){
        this.status = StatusInscricao.APROVADA;
    }

    public void rejeitar(){
        this.status = StatusInscricao.REJEITADA;
    }


}
