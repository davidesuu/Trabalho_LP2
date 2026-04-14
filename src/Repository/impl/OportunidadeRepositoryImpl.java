package Repository.impl;

import Entity.Oportunidade;
import Repository.OportunidadeRepository;

import java.util.HashMap;
import java.util.Map;

public class OportunidadeRepositoryImpl implements OportunidadeRepository {
    private Map<Long, Oportunidade> banco = new HashMap<>();
    private Long proximoId = 1L;
    @Override
    public void salvar(Oportunidade o) {
        if (o.getId() == null) o.setId(proximoId++);
        banco.put(o.getId(), o);
    }

    @Override
    public Oportunidade buscaPorId(Long id) {
        return banco.get(id);
    }


}
