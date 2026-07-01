package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepo extends JpaRepository<Grupo, Integer> {
    public List<Grupo> findByDiretoriaContaining(Discente discente);
    List<Grupo> findByResponsavel(Docente docente);
    List<Grupo> findByDiscentesContaining(Discente discente);
}
