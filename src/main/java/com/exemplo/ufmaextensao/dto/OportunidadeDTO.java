package com.exemplo.ufmaextensao.dto;


import com.exemplo.ufmaextensao.enums.Modalidade;
import com.exemplo.ufmaextensao.enums.StatusOportunidade;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.TipoOportunidade;
import com.exemplo.ufmaextensao.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class OportunidadeDTO {
    String nome;
    private String descricao;
    private Modalidade modalidade;
    private Integer carga_horaria;
    private Integer vagas;
    private LocalDate inicio;
    private LocalDate fim;
}