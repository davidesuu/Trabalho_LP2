package Service;

import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

public class DocenteService {
    private final OportunidadeRepositoryImpl repository;

    public DocenteService(OportunidadeRepositoryImpl repository) {
        this.repository = repository;
    }

    public void criarOportunidade(Oportunidade o){
        repository.salvar(o);
    }
}
