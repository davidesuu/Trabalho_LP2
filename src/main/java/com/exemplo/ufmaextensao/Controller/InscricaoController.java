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
    public Inscricao criarNovaInscricao(@RequestBody InscricaoDTO dto,
                                        @RequestParam Integer oportunidadeId,
                                        @RequestParam Integer discenteId) {
        return inscricaoService.criarInscricao(dto, oportunidadeId, discenteId);
    }

    @GetMapping("/{id_inscricao}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao buscarPorId(@PathVariable Integer id_inscricao) {
        return inscricaoService.buscar(id_inscricao);
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

    @PatchMapping("/aprovar/{id_inscricao}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao aprovarInscricao(@PathVariable Integer id_inscricao,
                                      @RequestParam Integer id_docente) {
        return inscricaoService.aprovar(id_inscricao, id_docente);
    }

    @PatchMapping("/rejeitar/{id_inscricao}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao rejeitarInscricao(@PathVariable Integer id_inscricao,
                                       @RequestParam Integer id_docente) {
        return inscricaoService.rejeitar(id_inscricao, id_docente);
    }

    @PatchMapping("/cancelar/{id_inscricao}")
    @ResponseStatus(HttpStatus.OK)
    public Inscricao cancelarInscricao(@PathVariable Integer id_inscricao) {
        return inscricaoService.cancelarInscricao(id_inscricao);
    }
}