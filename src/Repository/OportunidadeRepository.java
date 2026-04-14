package Repository;

import Entity.Oportunidade;

public interface OportunidadeRepository {
    void salvar(Oportunidade o);

    public Oportunidade buscaPorId(Long id);

}

