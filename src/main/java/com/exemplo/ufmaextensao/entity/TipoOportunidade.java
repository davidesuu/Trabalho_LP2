package com.exemplo.ufmaextensao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Table(name = "tipo_oportunidade")
@Data
public class TipoOportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipoOportunidade")
    private Integer id;

    @Column
    private String tipo; //evento, oficina, curso e projeto...

    @JsonIgnore
    @OneToMany(mappedBy = "tipoOportunidade")
    @Column(name = "oportunidade")
    private List<Oportunidade> oportunidades;

}