package com.exemplo.ufmaextensao.Controller;

import com.exemplo.ufmaextensao.DTO.GrupoDTO;
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
                            @RequestParam Integer idUsuario,
                            @RequestParam Integer idDocente) throws RegraDeNegocioException {
        return grupoService.criarGrupo(grupoDTO, idUsuario, idDocente);
    }


    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Grupo adicionarMembro(@RequestParam Integer idGrupo,
                                @RequestParam Integer idDiscente,
                                @RequestParam Integer idDocente) throws RegraDeNegocioException{
        return grupoService.adicionarMembro(idGrupo, idDiscente, idDocente);
    }


    @PostMapping("/promover")
    @ResponseStatus(HttpStatus.OK)
    public Grupo promoverMembro(@RequestParam Integer idGrupo,
                                @RequestParam Integer idDiscente,
                                @RequestParam Integer idDocente,
                                @RequestParam String cargo) throws  RegraDeNegocioException{
        return grupoService.promoverMembro(idGrupo, idDiscente, idDocente, cargo);
    }


    @PostMapping("/remover")
    @ResponseStatus(HttpStatus.OK)
    public Grupo removerMembro(@RequestParam Integer idGrupo,
                                @RequestParam Integer idDiscente,
                                @RequestParam Integer idDocente) throws RegraDeNegocioException{

        return grupoService.removerMembro(idGrupo, idDiscente, idDocente);
    }


    @PostMapping("/rebaixar")
    @ResponseStatus(HttpStatus.OK)
    public Grupo rebaixarMembro(@RequestParam Integer idGrupo,
                                @RequestParam Integer idDiscente,
                                @RequestParam Integer idDocente,
                                @RequestParam String cargo) throws RegraDeNegocioException{
        return grupoService.rebaixarMembro(idGrupo, idDiscente, idDocente, cargo);
    }
}
