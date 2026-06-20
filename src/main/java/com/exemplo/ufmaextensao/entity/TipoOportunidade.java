package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Table(name = "Tipo Oportunidade")
@Data
public class TipoOportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipoOportunidade")
    private Integer id;

    @OneToMany(mappedBy = "oportunidade")
    @JoinColumn(name = "oportunidade")
    private List<Oportunidade> oportunidades;

}
