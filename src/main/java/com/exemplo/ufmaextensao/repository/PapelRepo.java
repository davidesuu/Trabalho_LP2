package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PapelRepo extends JpaRepository<Papel, Integer> {
    Optional<Papel> findByNome(String nome);

}
