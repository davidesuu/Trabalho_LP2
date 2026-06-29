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
    private OportunidadeRepo oportunidadeRepo;
    @Autowired
    private DiscenteRepo discenteRepo;
    @Autowired
    private DocenteService docenteService;

    /**
     * Essa função auxilia na busca
     * @param id_inscricao Id da inscrição que se quer buscar
     * @return A inscrição do id recebido
     * @throws RegraDeNegocioException
     */
    public Inscricao buscar(Integer id_inscricao) throws RegraDeNegocioException {
        return inscricaoRepo.findById(id_inscricao).orElseThrow(() -> new RegraDeNegocioException("Inscrição não encontrada"));
    }

    /**
     * Essa função aprova uma inscrição
     * @param id_inscricao ID da inscrição que vai ser aprovado
     * @param idDocente id do docente responsavel pela oportunidade
     * @return inscricao aprovada
     * @throws RegraDeNegocioException
     */
    public Inscricao aprovar(Integer id_inscricao, Integer idDocente) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id_inscricao);
        Docente docente = docenteService.buscarPorId(idDocente);
        Oportunidade oportunidade = inscricao.getOportunidade();
        if (!oportunidade.getResponsavel_oportunidade().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por essa oportunidade");
        }
        if (oportunidade.getVagas().equals(oportunidade.getVagasOcupadas())){
            throw new RegraDeNegocioException("Esta oportunidade não tem vagas livres");
        }
        inscricao.aprovar();
        oportunidade.getListaInscritos().add(inscricao);
        oportunidade.getListaEspera().remove(inscricao);
        return inscricaoRepo.save(inscricao);
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
        oportunidade.getListaEspera().remove(inscricao);
        return inscricaoRepo.save(inscricao);
    }

    /**
     * Essa função lista as inscrições pendentes
     * @return Uma lista com todas as inscrições pendentes
     * @throws RegraDeNegocioException
     */
    public List<Inscricao> listarPendentes() throws RegraDeNegocioException {
        return inscricaoRepo.findByStatus(StatusInscricao.PENDENTE);
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
    public Inscricao criarInscricao(InscricaoDTO dto, Integer oportunidadeId, Integer discenteId) throws RegraDeNegocioException {
        Oportunidade oportunidade = oportunidadeRepo.findById(oportunidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Oportunidade não encontrada"));

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

        Integer vagasOcupadasAtual = oportunidade.getVagasOcupadas() != 0 ? oportunidade.getVagasOcupadas() : 0;

        oportunidade.setVagasOcupadas(vagasOcupadasAtual + 1);
        oportunidade.getListaEspera().add(inscricao);
        oportunidadeRepo.save(oportunidade);

        return inscricaoRepo.save(inscricao);
    }

    /**
     * Esta função cancela uma inscrição
     * @param id_inscricao Id da inscrição que vai ser cancelada
     * @throws RegraDeNegocioException
     */
    public Inscricao cancelarInscricao(Integer id_inscricao) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id_inscricao);
        Oportunidade oportunidade = inscricao.getOportunidade();

        if(!oportunidade.getFim().isAfter(LocalDate.now())){
            throw new RegraDeNegocioException("Não é possivel cancelar inscrição após o fim de uma oportunidade");
        }

        inscricao.setStatus(StatusInscricao.REJEITADA);

        Integer vagasOcupadasAtual = oportunidade.getVagasOcupadas() != 0 ? oportunidade.getVagasOcupadas() : 0;
        if  (vagasOcupadasAtual > 0) {
            oportunidade.setVagasOcupadas(vagasOcupadasAtual-1);
        }

        oportunidade.getListaInscritos().remove(inscricao);

        oportunidadeRepo.save(oportunidade);
        return inscricaoRepo.save(inscricao);
    }

}
