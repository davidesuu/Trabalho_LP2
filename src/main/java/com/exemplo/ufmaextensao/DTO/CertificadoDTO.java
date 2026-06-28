package com.exemplo.ufmaextensao.DTO;

import com.exemplo.ufmaextensao.Enum.StatusAssinatura;
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
