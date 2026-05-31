package Repository;

import Entity.Oportunidade;

import java.io.IOException;
import java.util.List;

import Enum.StatusOportunidade;

public interface OportunidadeRepository {
    void salvar(Oportunidade o) throws IOException;


    Oportunidade buscaPorId(int id);

    public List<Oportunidade> listarPorStatus(StatusOportunidade oportunidade);

    public List<Oportunidade> listarTodas();

}

