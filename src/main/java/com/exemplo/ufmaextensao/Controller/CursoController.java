package com.exemplo.ufmaextensao.Controller;

import ch.qos.logback.core.model.Model;
import com.exemplo.ufmaextensao.DTO.CursoDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.service.CursoService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;


    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Curso> listarCursos() {
        return cursoService.obterCursos();
    }


    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Curso criarNovoCurso(
            @RequestBody CursoDTO cursoDTO,
            @RequestParam Integer idUsuario){
        return cursoService.criarCurso(cursoDTO, idUsuario);
    }
}
