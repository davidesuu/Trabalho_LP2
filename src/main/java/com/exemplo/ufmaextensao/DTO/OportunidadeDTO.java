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
    private TipoOportunidade oportunidade;
    private Modalidade modalidade;
    private Integer carga_horaria;
    private Integer vagas;
    private Integer vagasOocupadas;
    private StatusOportunidade status;
    private LocalDate incio;
    private LocalDate fim;
    private List<Usuario> autor;
    private Docente responsavel_oportunidade;

}