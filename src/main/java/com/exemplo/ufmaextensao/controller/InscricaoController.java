package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.InscricaoDTO;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.service.InscricaoService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricao")
public class InscricaoController {
    // colocar pra retornar grupo.service da função pra retoranr o objeto pra ver o que aconteceu pra facilitar nos testes

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Inscricao criarNovaInscricao(@RequestBody InscricaoDTO dto,
                                        @RequestParam Integer oportunidadeId,
                                        @RequestParam Integer discenteId) throws RegraDeNegocioException {
        return inscricaoService.criarInscricao(dto, oportunidadeId, discenteId);
    }

    @GetMapping("/{inscricaoId}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao buscarPorId(@PathVariable Integer inscricaoId) {
        return inscricaoService.buscar(inscricaoId);
    }

    @GetMapping("/pendentes/{oportunidadeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inscricao> listarPendentes(
            @PathVariable Integer oportunidadeId
    ) {
        return inscricaoService.listarPendentes(oportunidadeId);
    }

    @GetMapping("/discente/{discenteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inscricao> listarPorDiscente(@PathVariable Integer discenteId) {
        return inscricaoService.listarDiscente(discenteId);
    }

    @GetMapping("/discente/{discenteId}/ativas")
    @ResponseStatus(HttpStatus.OK)
    public List<Inscricao> listarAprovadasEPendentesPorDiscente(@PathVariable Integer discenteId) {
        return inscricaoService.listarAprovadasEPendentesPorDiscente(discenteId);
    }

    @GetMapping("/oportunidade/{oportunidadeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inscricao> listarPorOportunidade(@PathVariable Integer oportunidadeId) {
        return inscricaoService.listarPorOportunidade(oportunidadeId);
    }

    @PatchMapping("/aprovar/{inscricaoId}/{docenteId}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao aprovarInscricao(@PathVariable Integer inscricaoId,
                                      @PathVariable Integer docenteId) throws RegraDeNegocioException {
        return inscricaoService.aprovar(inscricaoId, docenteId);
    }

    @PatchMapping("/rejeitar/{inscricaoId}/{docenteId}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao rejeitarInscricao(@PathVariable Integer inscricaoId,
                                       @PathVariable Integer docenteId) throws RegraDeNegocioException {
        return inscricaoService.rejeitar(inscricaoId, docenteId);
    }

    @PatchMapping("/cancelar/{inscricaoId}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao cancelarInscricao(@PathVariable Integer inscricaoId) throws RegraDeNegocioException {
        return inscricaoService.cancelarInscricao(inscricaoId);
    }
}