package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.TipoOportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoOportunidadeRepo extends JpaRepository<TipoOportunidade, Integer>{
    public Optional<TipoOportunidade> findByTipo(String tipo);

}
