package com.exemplo.ufmaextensao.Controller;

import com.exemplo.ufmaextensao.DTO.InscricaoDTO;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.service.InscricaoService;
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
    public Inscricao criarNovaInscricao(@RequestBody InscricaoDTO dto) {
        return inscricaoService.criarInscricao(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao buscarPorId(@PathVariable Integer id) {
        return inscricaoService.buscar(id);
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.OK)
    public List<Inscricao> listarPendentes() {
        return inscricaoService.listarPendentes();
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

    @PatchMapping("/aprovar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao aprovarInscricao(@PathVariable Integer id) {
        return inscricaoService.aprovar(id);
    }

    @PatchMapping("/rejeitar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao rejeitarInscricao(@PathVariable Integer id) {
        return inscricaoService.rejeitar(id);
    }

    @PatchMapping("/cancelar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao cancelarInscricao(@PathVariable Integer id) {
        return inscricaoService.cancelarInscricao(id);
    }
}