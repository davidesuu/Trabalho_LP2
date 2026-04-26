package Repository;

import Entity.Oportunidade;

import java.io.IOException;
import java.util.List;

import Enum.Status;

public interface OportunidadeRepository {
    void salvar(Oportunidade o) throws IOException;

    public Oportunidade buscaPorId(Long id);

    public List<Oportunidade> listarPorStatus(Status oportunidade);

    public List<Oportunidade> listarTodas();

}

