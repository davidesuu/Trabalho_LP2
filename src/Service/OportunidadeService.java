package Service;


import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

public class OportunidadeService {
    private final OportunidadeRepositoryImpl repository;

    public OportunidadeService(OportunidadeRepositoryImpl repository){
        this.repository = repository;
    }

    public void publicar(Long id){
        Oportunidade o = repository.buscaPorId(id);  //aqui teria qyue verificar se ele existe no repo
        o.setDisponivel();
        repository.salvar(o);
        IO.println("Oportunidade cadastrada: " + o);
    }

    public void criarOportunidade(Oportunidade o){
        repository.salvar(o);
    }

}
