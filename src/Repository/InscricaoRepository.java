// Repository/InscricaoRepository.java
package Repository;

//import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;

import java.util.List;

public interface InscricaoRepository {
    void salvar(Inscricao inscricao);
    Inscricao buscarPorId(Long id);
    //List<Inscricao> buscarPorDiscente(Discente discente);
    List<Inscricao> buscarPorOportunidade(Oportunidade oportunidade);
    List<Inscricao> listarTodas();
}