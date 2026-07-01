package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.entity.TipoOportunidade;
import com.exemplo.ufmaextensao.repository.TipoOportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoOportunidadeService {

    @Autowired
    private TipoOportunidadeRepo tipoOportunidadeRepo;

    public TipoOportunidade buscarPorTipo(String tipo) throws RegraDeNegocioException {
        return tipoOportunidadeRepo.findByTipo(tipo)
                .orElseThrow(() -> new RegraDeNegocioException("Tipo de oportunidade não encontrado"));
    }
}
