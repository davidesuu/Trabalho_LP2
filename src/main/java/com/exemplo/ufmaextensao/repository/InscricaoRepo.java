package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.Enum.StatusInscricao;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepo extends JpaRepository<Inscricao, Integer> {
    List<Inscricao> findByDiscenteId(Integer discenteId);
    List<Inscricao> findByOportunidadeId(Integer oportunidadeId);
    List<Inscricao> findByOportunidadeAndStatus(Oportunidade oportunidade, StatusInscricao status);
}