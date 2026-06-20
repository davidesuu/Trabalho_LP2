package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OportunidadeRepo extends JpaRepository<Oportunidade,Integer> {
}
