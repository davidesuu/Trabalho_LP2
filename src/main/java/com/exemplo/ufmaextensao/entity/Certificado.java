package com.exemplo.ufmaextensao.entity;

import com.exemplo.ufmaextensao.Enum.StatusAssinatura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Certificado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Integer id; //ok

    @Column(name = "uuid_hash", nullable = false, unique = true, updatable = false) // nullable faz com que garante q
    // ue nao vai ter nenhum qrcode sem hash, unique garante que cada hash do qrcode é unico,
    // updatable garante que depois que eu criar o qrcode eu nao posso mais modifica-lo
    private String uuid_hash;

    @ManyToOne
    @JoinColumn(name = "id_discente")
    private Discente discente; //ok

    @ManyToOne
    @JoinColumn(name = "id_oportunidade")
    private Oportunidade oportunidade; //ok

    @Column(name = "horas")
    private Integer horas;//ok


    @Enumerated(EnumType.STRING)
    @Column(name = "status_assinatura")
    private StatusAssinatura statusAssinatura;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;







}

