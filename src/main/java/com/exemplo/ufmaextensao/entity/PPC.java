package com.exemplo.ufmaextensao.entity;

import jakarta.persistence.Entity;

@Entity
public class PPC {
    private Long id;
    private Long cursoId;
    private Integer anoVigencia;
    private Integer cargaHorariaTotal;
}
