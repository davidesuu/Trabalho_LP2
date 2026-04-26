package Repository.impl;

import Entity.Oportunidade;
import Repository.OportunidadeRepository;

import java.util.*;
import java.util.stream.Collectors;

import Enum.Status;

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

    @Override
    public List<Oportunidade> listarPorStatus(Status status) {
        return banco.values().stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<Oportunidade> listarTodas(){
        return List.copyOf(banco.values());
    }
}
