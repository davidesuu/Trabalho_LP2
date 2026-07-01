package com.exemplo.ufmaextensao.dto;

import com.exemplo.ufmaextensao.enums.StatusAssinatura;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificadoDTO {
    Integer id;
    String uuid_hash;
    Integer horas;
    LocalDate dataEmissao;
    StatusAssinatura statusAssinatura;
}
