package com.exemplo.ufmaextensao.dto;

import lombok.Data;

@Data
public class AproveitamentoDTO {
    private String descricao;
    private String instituicao;
    private Integer horas;
    private String certificadoPath;
}