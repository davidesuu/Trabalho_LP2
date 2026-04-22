// Repository/InscricaoRepository.java
package Repository;

//import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;

import java.util.List;
import Enum.Status;

public interface InscricaoRepository {
    void salvar(Inscricao inscricao);
    Inscricao buscarPorId(Long id);
    //List<Inscricao> buscarPorDiscente(Discente discente);
    List<Inscricao> buscarPorOportunidade(Oportunidade oportunidade);
    List<Inscricao> listarTodas();
    List<Inscricao> listarStatus(Status status);
}

