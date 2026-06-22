package com.exemplo.ufmaextensao.Controller;

import com.exemplo.ufmaextensao.DTO.OportunidadeDTO;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.service.OportunidadeService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/* o controller é a parte da frente das camadas do sistema
 *  é esse que vai receber primeiramente os pedidos quando chamarem as rotas expostas  */

@RestController
@RequestMapping("/oportunidade") //define as rotas, para todas as aquisisçoes "http://localhost:8080/oportunidade"
public class OportunidadeController {

    @Autowired
    private OportunidadeService oportunidadeService;

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegraDeNegocio (RegraDeNegocioException e) {
        return e.getMessage();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Oportunidade criarNOvaOportunidade(
            @RequestBody OportunidadeDTO oportunidadeDTO,
            @RequestParam Integer idUsuario) {
        return oportunidadeService.criarOportunidade(oportunidadeDTO, idUsuario);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Oportunidade buscarPorId(@PathVariable Integer id) {
        return oportunidadeService.buscar(id);
    }

    @GetMapping("/publicadas")
    @ResponseStatus(HttpStatus.OK)
    public List<Oportunidade> listarPublicadas() {
        return oportunidadeService.listarPublicadas();
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.OK)
    public List<Oportunidade> listarPendentes(){
        return oportunidadeService.listarPendentes();
    }

    @PutMapping("/publicar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void publicarOportunidade(
            @PathVariable Integer id,
            @RequestBody Docente docente) {
        oportunidadeService.publicarOportunidade(id, docente);
    }

    @PatchMapping("/rejeitar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void rejeitarOPortunidade(
            @PathVariable Integer id,
            @RequestBody Docente docente){
        oportunidadeService.rejeitarOportunidade(id, docente);
    }
}
