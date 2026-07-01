package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.PPCDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.service.PPCService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PPC")
public class PPCController {

    @Autowired
    private PPCService ppcService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<PPC> listar(@RequestParam Integer cursoId)
    throws RegraDeNegocioException{
        List<PPC> ppcs = ppcService.listarPPCsPorCurso(cursoId);
        return ppcs;
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public PPC criarPPC(@RequestBody PPCDTO ppcDTO,
                        @RequestParam Integer cursoId,
                        @RequestParam Integer usuarioId)
    throws RegraDeNegocioException{
        return ppcService.criarPPC(ppcDTO, cursoId, usuarioId);
    }
}
