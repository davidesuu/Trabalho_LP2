package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.AproveitamentoDTO;
import com.exemplo.ufmaextensao.entity.Aproveitamento;
import com.exemplo.ufmaextensao.service.AproveitamentoService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aproveitamento")
public class AproveitamentoController {

    @Autowired
    private AproveitamentoService aproveitamentoService;

    @PostMapping("/solicitar")
    @ResponseStatus(HttpStatus.CREATED)
    public Aproveitamento solicitar(@RequestBody AproveitamentoDTO dto,
                                    @RequestParam Integer discenteId) throws RegraDeNegocioException {
        return aproveitamentoService.solicitar(dto, discenteId);
    }

    @PutMapping("/{id}/aprovar")
    @ResponseStatus(HttpStatus.OK)
    public Aproveitamento aprovar(@PathVariable Integer id,
                                  @RequestParam Integer avaliadorId) throws RegraDeNegocioException {
        return aproveitamentoService.aprovar(id, avaliadorId);
    }

    @PutMapping("/{id}/indeferir")
    @ResponseStatus(HttpStatus.OK)
    public Aproveitamento indeferir(@PathVariable Integer id,
                                    @RequestParam Integer avaliadorId,
                                    @RequestParam String motivo) throws RegraDeNegocioException {
        return aproveitamentoService.indeferir(id, avaliadorId, motivo);
    }

    @PutMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public Aproveitamento cancelar(@PathVariable Integer id,
                                   @RequestParam Integer discenteId) throws RegraDeNegocioException {
        return aproveitamentoService.cancelar(id, discenteId);
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.OK)
    public List<Aproveitamento> listarPendentes() {
        return aproveitamentoService.listarPendentes();
    }

    @GetMapping("/discente/{discenteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Aproveitamento> listarPorDiscente(@PathVariable Integer discenteId) throws RegraDeNegocioException {
        return aproveitamentoService.listarPorDiscente(discenteId);
    }
}