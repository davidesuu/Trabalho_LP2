package Service;

import Entity.Inscricao;
import Enum.Status;
import Repository.impl.InscricoesRepositoryImpl;
import Entity.Discente;
import Entity.Oportunidade;

import java.time.LocalDate;
import java.util.List;

public class InscricaoServico {
    private final InscricoesRepositoryImpl banco;

    public InscricaoServico(InscricoesRepositoryImpl inscricoesRepository) {
        this.banco = inscricoesRepository;
    }

    //Depois
    public void aprovar(Long id){
        Inscricao inscricao = banco.buscarPorId(id);
        inscricao.aprovar();
        banco.salvar(inscricao);
    }

    public List<Inscricao> listarPendente() {
        return banco.listarStatus(Status.PENDENTE);
    }

    public List<Inscricao> listarDiscente(Discente discente){
        return banco.listarPorDiscente(discente);
    }

    public void rejeitar(Long id){
        Inscricao inscricao = banco.buscarPorId(id);
        inscricao.rejeitar();
        banco.salvar(inscricao);
    }

    public Inscricao criarInscricao(Oportunidade oportunidade, Discente discente, String motivacao){
        Inscricao b = discente.criarInscricao(oportunidade, discente, motivacao);
        banco.salvar(b);
        return b;
    }
//    Oportunidade oportunidade, Discente discente, Status status,
//    String motivacao, LocalDate created_at
}
