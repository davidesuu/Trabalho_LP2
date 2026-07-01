package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.enums.StatusAproveitamento;
import com.exemplo.ufmaextensao.entity.Aproveitamento;
import com.exemplo.ufmaextensao.entity.Discente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AproveitamentoRepo extends JpaRepository<Aproveitamento, Integer> {
    List<Aproveitamento> findByStatus(StatusAproveitamento status);
    List<Aproveitamento> findByDiscente(Discente discente);
}