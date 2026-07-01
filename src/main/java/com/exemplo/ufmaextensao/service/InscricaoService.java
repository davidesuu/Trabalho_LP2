package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.InscricaoDTO;
import com.exemplo.ufmaextensao.Enum.StatusInscricao;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import com.exemplo.ufmaextensao.repository.InscricaoRepo;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import com.exemplo.ufmaextensao.service.OportunidadeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepo inscricaoRepo;
    @Autowired
    private OportunidadeService oportunidadeService;
    @Autowired
    private DiscenteRepo discenteRepo;
    @Autowired
    private DocenteService docenteService;

    /**
     * Essa função auxilia na busca
     * @param inscricaoId Id da inscrição que se quer buscar
     * @return A inscrição do id recebido
     * @throws RegraDeNegocioException
     */
    public Inscricao buscar(Integer inscricaoId) throws RegraDeNegocioException {
        return inscricaoRepo.findById(inscricaoId).orElseThrow(() -> new RegraDeNegocioException("Inscrição não encontrada"));
    }

    /**
     * Essa função aprova uma inscrição
     * @param inscricaoId ID da inscrição que vai ser aprovado
     * @param idDocente id do docente responsavel pela oportunidade
     * @return inscricao aprovada
     * @throws RegraDeNegocioException
     */
    public Inscricao aprovar(Integer inscricaoId, Integer idDocente) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(inscricaoId);
        Docente docente = docenteService.buscarPorId(idDocente);
        Oportunidade oportunidade = inscricao.getOportunidade();
        if (!oportunidade.getResponsavel_oportunidade().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por essa oportunidade");
        }
        if (oportunidade.getVagas().equals(oportunidade.getVagasOcupadas())){
            throw new RegraDeNegocioException("Esta oportunidade não tem vagas livres");
        }
        inscricao.aprovar();
        oportunidade.setVagasOcupadas(oportunidade.getVagasOcupadas() + 1);
        inscricaoRepo.save(inscricao);
        oportunidadeService.salvarOportunidade(oportunidade);
        return inscricao;
    }

    /**
     * Essa função rejeita uma inscrição
     * @param id_inscricao ID da inscrição que vai ser rejeitada
     * @param idDocente id do docente responsavel pela oportunidade
     * @return inscricao rejeitada
     * @throws RegraDeNegocioException
     */
    public Inscricao rejeitar(Integer id_inscricao, Integer idDocente) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id_inscricao);
        Docente docente = docenteService.buscarPorId(idDocente);
        Oportunidade oportunidade = inscricao.getOportunidade();
        if (!oportunidade.getResponsavel_oportunidade().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por essa oportunidade");
        }
        inscricao.rejeitar();
        inscricaoRepo.save(inscricao);
        oportunidadeService.salvarOportunidade(oportunidade);
        return inscricao;
    }

    /**
     * Essa função lista as inscrições pendentes
     * @param oportunidadeId id da oportunidade requisitada
     * @return Uma lista com todas as inscrições pendentes
     * @throws RegraDeNegocioException
     */
    public List<Inscricao> listarPendentes(Integer oportunidadeId) throws RegraDeNegocioException {
        Oportunidade oportunidade = oportunidadeService.buscarPorId(oportunidadeId);
        return inscricaoRepo.findByOportunidadeAndStatus(oportunidade,StatusInscricao.PENDENTE);
    }

    /**
     * Essa função lista as inscrições aprovadas
     * @param oportunidadeId
     * @return
     * @throws RegraDeNegocioException
     */
    public List<Inscricao> listarAprovados(Integer oportunidadeId) throws RegraDeNegocioException {
        Oportunidade oportunidade = oportunidadeService.buscarPorId(oportunidadeId);
        return inscricaoRepo.findByOportunidadeAndStatus(oportunidade,StatusInscricao.APROVADA);
    }


    /**
     * Essa função lista todas as inscrições de um discente
     * @param discenteId Id do discente que vai ser checado
     * @return Lista com todas as inscrições do discente
     */
    public List<Inscricao> listarDiscente(Integer discenteId) {
        return inscricaoRepo.findByDiscenteId(discenteId);
    }

    /**
     * Essa função lista todas as inscrições aprovadas e pendentes de um discente
     * @param discenteId Id do discente que vai ser checado
     * @return Lista com todas as inscrições aprovadas ou pendentes do discente
     * @throws RegraDeNegocioException
     */
    public List<Inscricao> listarAprovadasEPendentesPorDiscente(Integer discenteId) throws RegraDeNegocioException {
        List<Inscricao> inscricoes = inscricaoRepo.findByDiscenteId(discenteId);

        return inscricoes.stream()
                .filter(i -> i.getStatus() == StatusInscricao.PENDENTE || i.getStatus() == StatusInscricao.APROVADA)
                .collect(Collectors.toList());
    }

    /**
     * Esta função lista todas as inscrições de uma oportunidade
     * @param oportunidadeId Id da oportunidade que vai ser checada
     * @return Lista com todas inscrições dessa oportunidade
     * @throws RegraDeNegocioException
     */
    public List<Inscricao> listarPorOportunidade(Integer oportunidadeId) throws RegraDeNegocioException {
        return inscricaoRepo.findByOportunidadeId(oportunidadeId);
    }

    /**
     * Essa função cria uma nova inscrição
     * @param dto DTO da inscrição
     * @param oportunidadeId Id da oportunidade que vai ser checada
     * @param discenteId Id do discente que vai ser checado
     * @return  Nova inscrição
     * @throws RegraDeNegocioException
     */
    @Transactional
    public Inscricao criarInscricao(InscricaoDTO dto, Integer oportunidadeId, Integer discenteId) throws RegraDeNegocioException {
        Oportunidade oportunidade = oportunidadeService.buscarPorId(oportunidadeId);
        if(!oportunidade.getFim().isAfter(LocalDate.now())){
            throw new RegraDeNegocioException("Não é possivel se inscrever após o fim de uma oportunidade");
        }

        Discente discente = discenteRepo.findById(discenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));
        Inscricao inscricao = Inscricao.builder()
                .oportunidade(oportunidade)
                .discente(discente)
                .motivacao(dto.getMotivacao())
                .status(StatusInscricao.PENDENTE)
                .build();

        inscricaoRepo.save(inscricao);
        return inscricao;
    }

    /**
     * Esta função cancela uma inscrição
     * @param inscricaoId Id da inscrição que vai ser cancelada
     * @throws RegraDeNegocioException
     */
    public Inscricao cancelarInscricao(Integer inscricaoId) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(inscricaoId);
        Oportunidade oportunidade = inscricao.getOportunidade();

        if(!oportunidade.getFim().isAfter(LocalDate.now())){
            throw new RegraDeNegocioException("Não é possivel cancelar inscrição após o fim de uma oportunidade");
        }

        inscricao.cancelar();

        Integer vagasOcupadasAtual = oportunidade.getVagasOcupadas() != 0 ? oportunidade.getVagasOcupadas() : 0;
        if (inscricao.getStatus() == StatusInscricao.APROVADA && vagasOcupadasAtual > 0) {
            oportunidade.setVagasOcupadas(vagasOcupadasAtual - 1);
        }

        oportunidadeService.salvarOportunidade(oportunidade);
        return inscricaoRepo.save(inscricao);
    }

}
