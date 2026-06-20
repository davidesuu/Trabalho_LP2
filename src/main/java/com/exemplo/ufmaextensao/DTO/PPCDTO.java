package com.exemplo.ufmaextensao.DTO;

import com.exemplo.ufmaextensao.entity.Curso;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class PPCDTO {
    private Integer anoVigencia;
    private Float cargaHorariaTotal;
}
