package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.InscricaoDTO;
import com.exemplo.ufmaextensao.Enum.StatusInscricao;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.InscricaoRepo;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Essa função auxilia na busca
     * @param id Id da inscrição que se quer buscar
     * @return A inscrição do id recebido
     * @throws RegraDeNegocioException
     */
    public Inscricao buscar(Integer id) throws RegraDeNegocioException {
        return inscricaoRepo.findById(id).orElseThrow(() -> new RegraDeNegocioException("Inscrição não encontrada"));
    }

    /**
     * Essa função aprova uma inscrição
     * @param id ID da inscrição que vai ser aprovado
     * @throws RegraDeNegocioException
     */
    public Inscricao aprovar(Integer id) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id);
        inscricao.aprovar();
        return inscricaoRepo.save(inscricao);
    }

    /**
     * Essa função rejeita uma inscrição
     * @param id ID da inscrição que vai ser rejeitada
     * @throws RegraDeNegocioException
     */
    public Inscricao rejeitar(Integer id) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id);
        inscricao.rejeitar();
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
     * @param dto DTO da isncrição
     * @return  Nova inscrição
     * @throws RegraDeNegocioException
     */
    public Inscricao criarInscricao(InscricaoDTO dto) throws RegraDeNegocioException {
        Oportunidade oportunidade = oportunidadeRepo.findById(dto.getOportunidadeId())
                .orElseThrow(() -> new RegraDeNegocioException("Oportunidade não encontrada"));

        Discente discente = discenteRepo.findById(dto.getDiscenteId())
                .orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        Inscricao inscricao = Inscricao.builder()
                .oportunidade(oportunidade)
                .discente(discente)
                .motivacao(dto.getMotivacao())
                .status(StatusInscricao.PENDENTE)
                .build();

        Integer vagasOcupadasAtual = oportunidade.getVagasOcupadas() != null ? oportunidade.getVagasOcupadas() : 0;
        oportunidade.setVagasOcupadas(vagasOcupadasAtual + 1);
        oportunidadeRepo.save(oportunidade);

        return inscricaoRepo.save(inscricao);
    }

    /**
     * Esta função cancela uma inscrição
     * @param id Id da inscrição que vai ser cancelada
     * @throws RegraDeNegocioException
     */
    public Inscricao cancelarInscricao(Integer id) throws RegraDeNegocioException {
        Inscricao inscricao = buscar(id);
        Oportunidade oportunidade = inscricao.getOportunidade();

        inscricao.setStatus(StatusInscricao.REJEITADA);

        Integer vagasOcupadasAtual = oportunidade.getVagasOcupadas() != null ? oportunidade.getVagasOcupadas() : 0;
        if  (vagasOcupadasAtual > 0) {
            oportunidade.setVagasOcupadas(vagasOcupadasAtual-1);
        }

        oportunidadeRepo.save(oportunidade);
        return inscricaoRepo.save(inscricao);
    }
}
