package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.Enum.StatusAssinatura;
import com.exemplo.ufmaextensao.entity.Certificado;
import com.exemplo.ufmaextensao.entity.Discente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificadoRepo extends JpaRepository<Certificado, Integer> {
    List<Certificado> findByStatus(StatusAssinatura status);
}

