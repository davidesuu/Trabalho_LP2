package Repository;

import Entity.Aproveitamento;
import Entity.Oportunidade;
import Enum.*;
import java.util.List;

public interface AproveitamentoRepository {
    void salvar(Aproveitamento a);

    public Aproveitamento buscaPorId(Long id);

    public List<Aproveitamento> listarPorStatus(Status aproveitamento);



    List<Aproveitamento> listarPorDiscente(String matricula);

    public List<Aproveitamento> listarTodas();

}
