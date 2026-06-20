package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepo extends JpaRepository<Grupo, Integer> {

}
