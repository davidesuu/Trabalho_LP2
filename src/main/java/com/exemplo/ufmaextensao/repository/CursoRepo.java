package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Integer> {
    public Optional<Curso> findCursoById(Integer id);

    public List<Curso> findAll();
    Optional<Curso> findCursoByCodigo(Integer codigo);
}
