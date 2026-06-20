package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.OportunidadeDTO;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  java.util.List;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepo oportunidadeRepo;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SecurityService securityService;

    /**
     * Essa função salva uma oportunidade no repositorio após validar as informações do Curso instanciado e validar permisão do usuario
     * @param oportunidadeDTO instancia do Curso a ser adicionado no repositorio
     * @param usuarioId Id do usuario que está criando o curso
     * @return retorna uma Oportunidade após salvar no repositorio
     */

    public Oportunidade criarOportunidade(OportunidadeDTO oportunidadeDTO, Integer usuarioId)
            throws RegraDeNegocioException {

        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "DOCENTE", "COORDENADOR");


        if (oportunidadeDTO == null) {
            throw new RegraDeNegocioException("Os dados da oportunidade não foram informados.");
        }
        if (oportunidadeDTO.getNome() == null || oportunidadeDTO.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser vazio.");
        }
        if (oportunidadeDTO.getDescricao() == null || oportunidadeDTO.getDescricao().isBlank()) {
            throw new RegraDeNegocioException("A descrição não pode ser vazia.");
        }

        if (oportunidadeDTO.getOportunidade() == null) {
            throw new RegraDeNegocioException("O tipo de oportunidade deve ser informado.");
        }
        if (oportunidadeDTO.getModalidade() == null) {
            throw new RegraDeNegocioException("A modalidade deve ser informada.");
        }
        if (oportunidadeDTO.getStatus() == null) {
            throw new RegraDeNegocioException("O status da oportunidade deve ser informado.");
        }

        if (oportunidadeDTO.getCarga_horaria() == null || oportunidadeDTO.getCarga_horaria() <= 0) {
            throw new RegraDeNegocioException("A carga horária deve ser maior que zero.");
        }
        if (oportunidadeDTO.getVagas() == null || oportunidadeDTO.getVagas() <= 0) {
            throw new RegraDeNegocioException("A quantidade de vagas deve ser maior que zero.");
        }
        if (oportunidadeDTO.getVagasOocupadas() == null) {
            throw new RegraDeNegocioException("A quantidade de vagas ocupadas deve ser informada.");
        }

        if (oportunidadeDTO.getIncio() == null) {
            throw new RegraDeNegocioException("A data de início deve ser informada.");
        }
        if (oportunidadeDTO.getFim() == null) {
            throw new RegraDeNegocioException("A data de fim deve ser informada.");
        }
        if (oportunidadeDTO.getFim().isBefore(oportunidadeDTO.getIncio())) {
            throw new RegraDeNegocioException("A data de fim não pode ser anterior à data de início.");
        }

        if (oportunidadeDTO.getAutor() == null || oportunidadeDTO.getAutor().isEmpty()) {
            throw new RegraDeNegocioException("A oportunidade deve ter pelo menos um autor.");
        }
        if (oportunidadeDTO.getResponsavel_oportunidade() == null) {
            throw new RegraDeNegocioException("O docente responsável deve ser informado.");
        }

        Oportunidade oportunidade = Oportunidade.builder()
                .nome(oportunidadeDTO.getNome())
                .descricao(oportunidadeDTO.getDescricao())
                .oportunidade(oportunidadeDTO.getOportunidade())
                .modalidade(oportunidadeDTO.getModalidade())
                .carga_horaria(oportunidadeDTO.getCarga_horaria())
                .vagas(oportunidadeDTO.getVagas())
                .vagasOocupadas(oportunidadeDTO.getVagasOocupadas())
                .status(oportunidadeDTO.getStatus())
                .incio(oportunidadeDTO.getIncio())
                .fim(oportunidadeDTO.getFim())
                .autor(oportunidadeDTO.getAutor())
                .responsavel_oportunidade(oportunidadeDTO.getResponsavel_oportunidade())
                .build();


        return oportunidadeRepo.save(oportunidade);
    }



}
