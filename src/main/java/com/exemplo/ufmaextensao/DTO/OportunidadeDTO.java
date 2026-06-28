package com.exemplo.ufmaextensao.DTO;


import com.exemplo.ufmaextensao.Enum.Modalidade;
import com.exemplo.ufmaextensao.Enum.StatusOportunidade;
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
    private Integer vagasOcupadas;
    private LocalDate incio;
    private LocalDate fim;
}