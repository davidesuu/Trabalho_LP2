package Service;

import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

import java.time.LocalDate;

public class DocenteService {
    private final OportunidadeRepositoryImpl repository;

    public DocenteService(OportunidadeRepositoryImpl repository) {
        this.repository = repository;
    }

    public void criarOportunidade(Oportunidade o){
        repository.salvar(o);
    }

    public void registrarPlanoAtividades(Oportunidade oportunidade, LocalDate data){

    }
}
