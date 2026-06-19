package com.exemplo.ufmaextensao.Controller;

import com.exemplo.ufmaextensao.DTO.PPCDTO;
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

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listar")
    public List<PPC> listar(@RequestParam Integer idCurso)
    {
        List<PPC> ppcs = ppcService.listarPPCsPorCurso(idCurso);
        return ppcs;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/criar")
    public PPC criarPPC(@RequestBody PPCDTO ppcDTO,
                        @RequestParam Integer idCurso,
                        @RequestParam Integer idUsuario)
    {
        return ppcService.criarPPC(ppcDTO, idCurso, idUsuario);
    }
}
