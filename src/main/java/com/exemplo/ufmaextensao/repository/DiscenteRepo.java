package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Discente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscenteRepo extends JpaRepository<Discente, Integer> {
    public Optional<Discente> findByMatricula(String matricula);
    public Optional<Discente> findById(Integer id);
}
