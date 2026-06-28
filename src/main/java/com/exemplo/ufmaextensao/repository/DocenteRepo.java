package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepo extends JpaRepository<Docente, Integer> {
    public Optional<Docente> findDocenteById(Integer docente_id);

}
