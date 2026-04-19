package Service;
import Entity.DiscenteDiretor;
import Entity.Docente;
import Enum.Status; //coment 16: Nao precisaria fazer isso
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

public class OportunidadeService {
    private final OportunidadeRepositoryImpl repository;

    public OportunidadeService(OportunidadeRepositoryImpl repository){
        this.repository = repository;
    }

    public void publicarOpurtunidade(Long id, Docente docente){
        Oportunidade o = repository.buscaPorId(id);  //aqui teria qyue verificar se ele existe no repo
        o.publicar(docente); //poderia ter um metodo que setava sem passar?
        repository.salvar(o);  //tecnicamente aprvar e publicar sao coisas diferente, precisaria de um aprovar
    }

    public void rejeitarOportunidade(Long id){
        Oportunidade o = repository.buscaPorId(id);
        o.rejeitar();
        //falta tbm a verificao do status e etc
    }

    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo,
                                  Modalidade modalidade, int cargaHoraria, int vagas,
                                  DiscenteDiretor autor){
        Oportunidade o = autor.criarOportunidade(titulo, descricao, tipo, modalidade, cargaHoraria, vagas, autor);
        repository.salvar(o);
        return o;
    }

}
