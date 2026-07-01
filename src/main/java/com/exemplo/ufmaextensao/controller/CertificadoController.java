package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.dto.CertificadoDTO;
import com.exemplo.ufmaextensao.entity.Certificado;
import com.exemplo.ufmaextensao.service.CertificadoService;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificado")
public class CertificadoController {
    @Autowired
    private CertificadoService certificadoService;



    @PutMapping("/assinar")
    public ResponseEntity<String> assinar(
            @RequestParam Integer docenteId,
            @RequestBody CertificadoDTO certificadoDTO) throws RegraDeNegocioException{
        try {
            certificadoService.assinarCertificado(certificadoDTO, docenteId);
            return ResponseEntity.ok("certificado assinado com sucesso");
        } catch (RegraDeNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Certificado>> listarPendentes() throws RegraDeNegocioException{
        List<Certificado> pendentes = certificadoService.listarCerticadosPendentes(null);
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/assinados")
    public ResponseEntity<List<Certificado>> listarAssinados() throws RegraDeNegocioException{
        List<Certificado> assinados = certificadoService.listarCerticadosAssinados(null);
        return ResponseEntity.ok(assinados);
    }

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<?> validarCertificado(@PathVariable String codigo) throws RegraDeNegocioException{
        try {
            Certificado certificadoValido = certificadoService.validarCertificado(codigo);
            return ResponseEntity.ok(certificadoValido);
        } catch (RegraDeNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }







}
