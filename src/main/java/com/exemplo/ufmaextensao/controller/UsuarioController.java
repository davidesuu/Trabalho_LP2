package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.DiscenteDTO;
import com.exemplo.ufmaextensao.dto.DocenteDTO;
import com.exemplo.ufmaextensao.dto.HorasDiscenteDTO;
import com.exemplo.ufmaextensao.dto.LoginDTO;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.service.DiscenteService;
import com.exemplo.ufmaextensao.service.DocenteService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import com.exemplo.ufmaextensao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private DocenteService docenteService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Usuario login(@RequestBody LoginDTO loginDTO) throws RegraDeNegocioException {
        return usuarioService.autenticarUsuario(loginDTO);
    }

    @PostMapping("/criarDiscente")
    @ResponseStatus(HttpStatus.CREATED)
    public Discente criarNovoDiscente(@RequestBody DiscenteDTO discenteDTO,
                                      @RequestParam Integer usuarioId,
                                      @RequestParam Integer cursoId){
        return discenteService.criarDiscente(discenteDTO, usuarioId, cursoId);
    }

    @PostMapping("/criarDocente")
    @ResponseStatus(HttpStatus.CREATED)
    public Docente criarNovoDocente(@RequestBody DocenteDTO docenteDTO,
                                    @RequestParam Integer usuarioId){
        return docenteService.criarDocente(docenteDTO, usuarioId);
    }

    @GetMapping("/listarUsuarios")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/painelHoras/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public HorasDiscenteDTO listarHorasDiscente(
            @PathVariable Integer usuarioId
    )
    {
        return discenteService.mostrarPainelDeHoras(usuarioId);
    }
}