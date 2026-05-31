package Service;

import Entity.Inscricao;
import Enum.Status;
import Repository.impl.InscricoesRepositoryImpl;
import Entity.Discente;
import Entity.Oportunidade;

import java.util.List;

public class InscricaoService {
    private final InscricoesRepositoryImpl banco;
    private final OportunidadeService oportunidadeService;
    public InscricaoService(InscricoesRepositoryImpl inscricoesRepository, OportunidadeService oportunidadeService) {
        this.banco = inscricoesRepository;
        this.oportunidadeService = oportunidadeService;
    }

    //Depois
    public void aprovar(Long id){
        Inscricao inscricao = banco.buscarPorId(id);
        inscricao.aprovar();
        banco.salvar(inscricao);
    }

    public Inscricao buscar(Long id){

        return banco.buscarPorId(id);

    }


    public List<Inscricao> listarPendente() {
        return banco.listarStatus(Status.PENDENTE);
    }

    public List<Inscricao> listarDiscente(Discente discente){
        return banco.listarPorDiscente(discente);
    }

    public List<Inscricao> listarAprovadasEPendentePorDiscente(Discente discente){
        List<Inscricao>  inscricoes = banco.listarPorDiscente(discente);
        List<Inscricao> filtrada = inscricoes.stream().filter(i -> i.getStatus() == Status.PENDENTE ||
                i.getStatus() == Status.APROVADO).toList();
        return filtrada;
    }

    public void rejeitar(Long id){
        Inscricao inscricao = banco.buscarPorId(id);
        inscricao.rejeitar();
        banco.salvar(inscricao);
    }

    public List<Inscricao> listarPorOportunidade(Oportunidade oportunidade){
        return banco.buscarPorOportunidade(oportunidade);
    }

    public Inscricao criarInscricao(Oportunidade oportunidade, Discente discente, String motivacao){
        Inscricao b = discente.criarInscricao(oportunidade, discente, motivacao);
        oportunidade.incrementaVagasOcupadas(1);
        oportunidadeService.atualizarOportunidade(oportunidade);
        banco.salvar(b);
        return b;
    }

    public void cancelarInscricao(Inscricao inscricao){
        Oportunidade oportunidade = oportunidadeService.buscar(inscricao.getOportunidadeId());
        inscricao.setStatus(Status.CANCELADA);
        oportunidade.decrementaVagasOcupadas(1);
        oportunidadeService.atualizarOportunidade(oportunidade);
        banco.salvar(inscricao);
    }

    public List<Long> ListarIndice(List<Inscricao> inscricoes) {

        List<Long> ids = banco.listarKeys(inscricoes);   ///explicando o fluxo
        Integer menuIndex = 1;                                   ///Faz uma lista com todos ids com o filtro selecionado

        for(Long realId : ids) {
            ///itera no ids, e usa o buscarporid para conseguir pegar
            System.out.println(                                  /// O valor no hashmap enquando mostra um id "falso"
                    "[" + menuIndex + "]\n"
                            + banco.buscarPorId(realId)
                            + "\n"
            );

            menuIndex++;
        }

        return ids;
    }
}
