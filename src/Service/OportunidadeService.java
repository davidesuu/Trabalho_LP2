package Service;
import Enum.Status; //coment 16: Nao precisaria fazer isso

import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

public class OportunidadeService {
    private final OportunidadeRepositoryImpl repository;

    public OportunidadeService(OportunidadeRepositoryImpl repository){
        this.repository = repository;
    }

    public void publicar(Long id){
        Oportunidade o = repository.buscaPorId(id);  //aqui teria qyue verificar se ele existe no repo
        o.setStatus(Status.PUBLICADA); //poderia ter um metodo que setava sem passar?
        repository.salvar(o);
    }

    public void criarOportunidade(Oportunidade o){
        repository.salvar(o);
    }

}
