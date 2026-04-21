package Repository;

import Entity.Oportunidade;

import java.util.List;

import Enum.Status;

public interface OportunidadeRepository {
    void salvar(Oportunidade o);

    public Oportunidade buscaPorId(Long id);

    public List<Oportunidade> listarPorStatus(Status oportunidade);

    public List<Oportunidade> listarTodas();

}

