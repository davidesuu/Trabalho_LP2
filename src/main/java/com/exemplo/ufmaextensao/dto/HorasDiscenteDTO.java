package com.exemplo.ufmaextensao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorasDiscenteDTO {
    String nome;
    Float horasCompletas;
    Float horasTotal;
}
