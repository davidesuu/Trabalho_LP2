package Service;

import Entity.*;
import Enum.*; //coment 16: Nao precisaria fazer isso
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Repository.InscricaoRepository;
import Repository.impl.InscricoesRepositoryImpl;
import Service.MatriculaService;
import Repository.impl.AproveitamentoRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class OportunidadeService {

    private final OportunidadeRepositoryImpl repository;
    private final InscricoesRepositoryImpl inscricoesRepository;
    private final MatriculaService matriculaService;
    private final CertificadoService certificadoService;
    public OportunidadeService(OportunidadeRepositoryImpl repository, InscricoesRepositoryImpl inscricoesRepository, MatriculaService matriculaService, CertificadoService certificadoService) {
        this.matriculaService = matriculaService;
        this.repository = repository;
        this.inscricoesRepository = inscricoesRepository;

        this.certificadoService = certificadoService;
    }

    public void publicarOpurtunidade(long id, Docente docente) throws IOException {

        //aqui é serve para o docente aprovar uma oportunidade enviada por um discente diretor

        Oportunidade o = repository.buscaPorId(id);

        //aqui teria qyue verificar se ele existe no repo

        o.publicar(docente);

        //poderia ter um metodo que setava sem passar?

        repository.salvar(o);

        //tecnicamente aprvar e publicar sao coisas diferente, precisaria de um aprovar
    }

    public void rejeitarOportunidade(long id, Docente docente) throws IOException {

        Oportunidade o = repository.buscaPorId(id);

        o.rejeitar(docente);

        repository.salvar(o);

        //falta tbm a verificao do status e etc
    }

    public Oportunidade buscar(long id) {

        return repository.buscaPorId(id);

    }

    public List<Oportunidade> listarPublicadas() {

        return repository.listarPorStatus(StatusOportunidade.PUBLICADA);

    }

    public List<Oportunidade> listarPendentes() {

        return repository.listarPorStatus(StatusOportunidade.PENDENTE);

    }

    public List<Oportunidade> listarOportunidadesPossiveis(Discente discente) {
        List<Oportunidade> publicadas = repository.listarPorStatus(StatusOportunidade.PUBLICADA);
        List<Long> idsInscritos = inscricoesRepository.listarPorDiscente(discente)
                .stream()
                    .filter(i -> i.getStatus() != StatusInscricao.REJEITADA)
                .map(i -> i.getOportunidadeId())
                .toList();

        return publicadas.stream()
                .filter(o -> !idsInscritos.contains(o.getId()))
                .toList();
    }                                          ///Serve pra nao mostrar a oportunidade pra quem ja se inscreveu, mas mostra se foi rejeitada pra permitir reenviar

    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo,
                                          Modalidade modalidade, int cargaHoraria, int vagas, LocalDate inicio, LocalDate fim,
                                          Usuario autor) throws IOException {

        Oportunidade o = autor.criarOportunidade(
                titulo,
                descricao,
                tipo,
                modalidade,
                cargaHoraria,
                vagas
        );
        o.setInicio(inicio);
        o.setFim(fim);

        repository.salvar(o);

        return o;
    }

    public void salvarOportunidade(Oportunidade o){

    }

    public List<Long> ListarIndice(List<Oportunidade> oportunidades) {

        List<Long> ids = repository.listarKeys(oportunidades);   ///explicando o fluxo
        Integer menuIndex = 1;                                   ///Faz uma lista com todos ids com o filtro selecionado

        for (Long realId : ids) {
            ///itera no ids, e usa o buscarporid para conseguir pegar
            System.out.println(                                  /// O valor no hashmap enquando mostra um id "falso"
                    "[" + menuIndex + "]\n"
                            + repository.buscaPorId(realId)
                            + "\n"
            );

            menuIndex++;
        }

        return ids;
    }

    public void atualizarOportunidade(Oportunidade oportunidade) {
        try {
            repository.salvar(oportunidade);
        }
        catch (Exception e) {
            System.out.println("Erro ao atualizar oportunidade");
        }
    }

    public void verificarOportunidadesExpiradas(LocalDate dataAtual) {
        List<Oportunidade> publicadas = repository.listarPorStatus(StatusOportunidade.PUBLICADA);

        for (Oportunidade o : publicadas) {
            if (o.getFim() != null && o.getFim().isBefore(dataAtual)) {
                o.setStatus(StatusOportunidade.FINALIZADA);
                try {
                    repository.salvar(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void finalizarOportunidades() {
        List<Oportunidade> oportunidades = repository.listarPorStatus(StatusOportunidade.FINALIZADA);

        for (Oportunidade o : oportunidades) {
            List<Inscricao> inscricoes = inscricoesRepository.buscarPorOportunidade(o);
            for (Inscricao inscricao : inscricoes) {
                if (inscricao.getStatus().equals(StatusInscricao.APROVADA)) {
                    matriculaService.adicionarHoras(inscricao.getDiscenteId(), o.getCarga_horaria());
                    certificadoService.criarCertificado(o.getId(), inscricao.getDiscenteId(), o.getCarga_horaria());
                }
            }
            o.setStatus(StatusOportunidade.HORAS_CONTABILIZADAS);
            try {
                repository.salvar(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}