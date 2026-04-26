package Service;
import Entity.Docente;
import Entity.Inscricao;
import Entity.Usuario;
import Enum.Status; //coment 16: Nao precisaria fazer isso
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;

import java.io.IOException;
import java.util.List;

public class OportunidadeService {
    private final OportunidadeRepositoryImpl repository;

    public OportunidadeService(OportunidadeRepositoryImpl repository){
        this.repository = repository;
    }

    public void publicarOpurtunidade(Long id, Docente docente) throws IOException {
        //aqui é serve para o docente aprovar uma oportunidade enviada por um discente diretor
        Oportunidade o = repository.buscaPorId(id);  //aqui teria qyue verificar se ele existe no repo
        o.publicar(docente); //poderia ter um metodo que setava sem passar?
        repository.salvar(o);  //tecnicamente aprvar e publicar sao coisas diferente, precisaria de um aprovar
    }

    public void rejeitarOportunidade(Long id, Docente docente) throws IOException {
        Oportunidade o = repository.buscaPorId(id);
        o.rejeitar(docente);
        repository.salvar(o);
        //falta tbm a verificao do status e etc
    }

    public Oportunidade buscar(Long id){
        return repository.buscaPorId(id);
    }

    public List<Oportunidade> listarPublicadas(){
        return repository.listarPorStatus(Status.PUBLICADA);
    }

    public List<Oportunidade> listarPendentes(){
        return repository.listarPorStatus(Status.PENDENTE);
    }

    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo,
                                  Modalidade modalidade, int cargaHoraria, int vagas,
                                  Usuario autor) throws IOException {
        Oportunidade o = autor.criarOportunidade(titulo, descricao, tipo, modalidade, cargaHoraria, vagas);
        repository.salvar(o);
        return o;
    }

}
