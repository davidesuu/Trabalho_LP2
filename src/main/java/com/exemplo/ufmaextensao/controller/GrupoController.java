package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.GrupoDTO;
import com.exemplo.ufmaextensao.entity.Grupo;
import com.exemplo.ufmaextensao.service.GrupoService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }


    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo criarGrupo(@RequestBody GrupoDTO grupoDTO,
                            @RequestParam Integer usuarioId,
                            @RequestParam Integer docenteId) throws RegraDeNegocioException {
        return grupoService.criarGrupo(grupoDTO, usuarioId, docenteId);
    }


    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Grupo adicionarMembro(@RequestParam Integer grupoId,
                                @RequestParam Integer discenteId,
                                @RequestParam Integer docenteId) throws RegraDeNegocioException{
        return grupoService.adicionarMembro(grupoId, discenteId, docenteId);
    }


    @PostMapping("/promover")
    @ResponseStatus(HttpStatus.OK)
    public Grupo promoverMembro(@RequestParam Integer grupoId,
                                @RequestParam Integer discenteId,
                                @RequestParam Integer docenteId,
                                @RequestParam String cargo) throws  RegraDeNegocioException{
        return grupoService.promoverMembro(grupoId, discenteId, docenteId, cargo);
    }


    @PostMapping("/remover")
    @ResponseStatus(HttpStatus.OK)
    public Grupo removerMembro(@RequestParam Integer grupoId,
                                @RequestParam Integer discenteId,
                                @RequestParam Integer docenteId) throws RegraDeNegocioException{

        return grupoService.removerMembro(grupoId, discenteId, docenteId);
    }


    @PostMapping("/rebaixar")
    @ResponseStatus(HttpStatus.OK)
    public Grupo rebaixarMembro(@RequestParam Integer grupoId,
                                @RequestParam Integer discenteId,
                                @RequestParam Integer docenteId,
                                @RequestParam String cargo) throws RegraDeNegocioException{
        return grupoService.rebaixarMembro(grupoId, discenteId, docenteId, cargo);
    }
}
