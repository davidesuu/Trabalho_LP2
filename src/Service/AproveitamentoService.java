package Service;

import Entity.*;

import Repository.impl.AproveitamentoRepositoryImpl;

import Enum.*;

import java.util.List;

public class AproveitamentoService {

    private final AproveitamentoRepositoryImpl repository;

    public AproveitamentoService(AproveitamentoRepositoryImpl repository){
        this.repository = repository;
    }


    public void rejeitarAproveitamento(Long id, Usuario usuario){
        Aproveitamento a = repository.buscaPorId(id);
        a.rejeitar(usuario);
        repository.salvar(a);
        //falta tbm a verificao do status e etc
    }

    public void aprovarAproveitamento(Long id, Usuario usuario){
        Aproveitamento a = repository.buscaPorId(id); //Usa o optional pra pegar os runtimes depois
        a.aprovar(usuario);
        repository.salvar(a);
    }

    public List<Aproveitamento> listarAprovadas(){
        return repository.listarPorStatus(Status.APROVADO);
    }

    public List<Aproveitamento> listarPendentes(){
        return repository.listarPorStatus(Status.PENDENTE);
    }

    public List<Aproveitamento> listarTodas(){
        return repository.listarTodas();
    }

    public List<Aproveitamento> listarPrivado(Discente discente) {
        return repository.listarPorDiscente(discente.getMatricula());
    }

    public Aproveitamento criarAproveitamento(Discente discente, int horas, String descricao, String instituicao,
                                            String certificado_path){
        Aproveitamento a = discente.criarAproveitamento(discente, horas, descricao, instituicao, certificado_path);
        repository.salvar(a);
        return a;
    }


}
