package com.exemplo.ufmaextensao.Controller;

import com.exemplo.ufmaextensao.DTO.GrupoDTO;
import com.exemplo.ufmaextensao.entity.Grupo;
import com.exemplo.ufmaextensao.service.GrupoService;
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
                            @RequestParam Integer idDocente){
        return grupoService.criarGrupo(grupoDTO, idUsuario, idDocente);
    }


}
