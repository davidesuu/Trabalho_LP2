// Repository/InscricaoRepository.java
package Repository;

//import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;

import java.util.List;
import Enum.StatusInscricao;

public interface InscricaoRepository {
    void salvar(Inscricao inscricao);
    Inscricao buscarPorId(Long id);
    //List<Inscricao> buscarPorDiscente(Discente discente);
    List<Inscricao> buscarPorOportunidade(Oportunidade oportunidade);
    List<Inscricao> listarTodas();
    List<Inscricao> listarStatus(StatusInscricao status);
    public List<Long> listarKeys(List<Inscricao> inscricoes);
}

