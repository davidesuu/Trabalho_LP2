package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.AproveitamentoDTO;
import com.exemplo.ufmaextensao.enums.StatusAproveitamento;
import com.exemplo.ufmaextensao.entity.Aproveitamento;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.AproveitamentoRepo;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AproveitamentoService {

    @Autowired
    private AproveitamentoRepo aproveitamentoRepo;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DiscenteRepo discenteRepo;

    /**
     * Cria uma nova solicitação de aproveitamento
     * @param dto dados da solicitação
     * @param discenteId id do discente solicitante
     * @return aproveitamento criado com status PENDENTE
     */
    public Aproveitamento solicitar(AproveitamentoDTO dto, Integer discenteId) throws RegraDeNegocioException {
        Discente discente = discenteRepo.findById(discenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new RegraDeNegocioException("Descrição é obrigatória");
        }
        if (dto.getInstituicao() == null || dto.getInstituicao().isBlank()) {
            throw new RegraDeNegocioException("Instituição é obrigatória");
        }
        if (dto.getHoras() == null || dto.getHoras() <= 0) {
            throw new RegraDeNegocioException("Horas devem ser maiores que zero");
        }
        if (dto.getCertificadoPath() == null || dto.getCertificadoPath().isBlank()) {
            throw new RegraDeNegocioException("Link do certificado é obrigatório");
        }

        Aproveitamento aproveitamento = Aproveitamento.builder()
                .discente(discente)
                .descricao(dto.getDescricao())
                .instituicao(dto.getInstituicao())
                .horas(dto.getHoras())
                .certificadoPath(dto.getCertificadoPath())
                .status(StatusAproveitamento.PENDENTE)
                .build();

        return aproveitamentoRepo.save(aproveitamento);
    }

    /**
     * Aprova uma solicitação e soma as horas no discente
     * @param aproveitamentoId id da solicitação
     * @param avaliadorId id do coordenador ou admin que aprova
     */
    @Transactional
    public Aproveitamento aprovar(Integer aproveitamentoId, Integer avaliadorId) throws RegraDeNegocioException {
        Usuario avaliador = usuarioService.obterUsuarioPorId(avaliadorId);
        securityService.validarPermissao(avaliador, "COORDENADOR", "ADMIN");

        Aproveitamento aproveitamento = buscar(aproveitamentoId);

        if (aproveitamento.getStatus() != StatusAproveitamento.PENDENTE) {
            throw new RegraDeNegocioException("Apenas solicitações pendentes podem ser aprovadas");
        }

        aproveitamento.setStatus(StatusAproveitamento.APROVADO);
        aproveitamento.setAvaliador(avaliador);
        aproveitamento.setDataAvaliacao(LocalDate.now());

        Discente discente = aproveitamento.getDiscente();
        discente.setCh_total_cumprida(discente.getCh_total_cumprida() + aproveitamento.getHoras());
        discenteRepo.save(discente);

        return aproveitamentoRepo.save(aproveitamento);
    }

    /**
     * Indefere uma solicitação com motivo
     * @param aproveitamentoId id da solicitação
     * @param avaliadorId id do coordenador ou admin que indefere
     * @param motivo motivo do indeferimento
     */
    public Aproveitamento indeferir(Integer aproveitamentoId, Integer avaliadorId, String motivo) throws RegraDeNegocioException {
        Usuario avaliador = usuarioService.obterUsuarioPorId(avaliadorId);
        securityService.validarPermissao(avaliador, "COORDENADOR", "ADMIN");

        Aproveitamento aproveitamento = buscar(aproveitamentoId);

        if (aproveitamento.getStatus() != StatusAproveitamento.PENDENTE) {
            throw new RegraDeNegocioException("Apenas solicitações pendentes podem ser indeferidas");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new RegraDeNegocioException("Motivo do indeferimento é obrigatório");
        }

        aproveitamento.setStatus(StatusAproveitamento.INDEFERIDO);
        aproveitamento.setAvaliador(avaliador);
        aproveitamento.setMotivoRejeicao(motivo);
        aproveitamento.setDataAvaliacao(LocalDate.now());

        return aproveitamentoRepo.save(aproveitamento);
    }

    /**
     * Cancela uma solicitação pendente pelo próprio discente
     * @param aproveitamentoId id da solicitação
     * @param discenteId id do discente que cancela
     */
    public Aproveitamento cancelar(Integer aproveitamentoId, Integer discenteId) throws RegraDeNegocioException {
        Aproveitamento aproveitamento = buscar(aproveitamentoId);

        if (!aproveitamento.getDiscente().getId().equals(discenteId)) {
            throw new RegraDeNegocioException("Discente não é o dono dessa solicitação");
        }
        if (aproveitamento.getStatus() != StatusAproveitamento.PENDENTE) {
            throw new RegraDeNegocioException("Apenas solicitações pendentes podem ser canceladas");
        }

        aproveitamento.setStatus(StatusAproveitamento.CANCELADO);
        return aproveitamentoRepo.save(aproveitamento);
    }

    /**
     * Lista todas as solicitações pendentes
     */
    public List<Aproveitamento> listarPendentes() {
        return aproveitamentoRepo.findByStatus(StatusAproveitamento.PENDENTE);
    }

    /**
     * Lista todas as solicitações de um discente
     */
    public List<Aproveitamento> listarPorDiscente(Integer discenteId) throws RegraDeNegocioException {
        Discente discente = discenteRepo.findById(discenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));
        return aproveitamentoRepo.findByDiscente(discente);
    }

    public Aproveitamento buscar(Integer id) throws RegraDeNegocioException {
        return aproveitamentoRepo.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Solicitação não encontrada"));
    }
}