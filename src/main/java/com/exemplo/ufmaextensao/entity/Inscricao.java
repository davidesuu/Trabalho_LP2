package com.exemplo.ufmaextensao.entity;

import com.exemplo.ufmaextensao.Enum.StatusInscricao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne
    @JoinColumn(name = "id_oportunidade")
    private Oportunidade oportunidade;

    @ManyToOne
    @JoinColumn(name = "id_discente")
    private Discente discente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_inscricao")
    private StatusInscricao status;

    @Column(name = "motivacao")
    private String motivacao;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    public void aprovar(){
        this.status = StatusInscricao.APROVADA;
    }

    public void rejeitar(){
        this.status = StatusInscricao.REJEITADA;
    }


}
