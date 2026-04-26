package Repository.impl;

import Entity.Aproveitamento;
import Enum.*;
import Repository.AproveitamentoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AproveitamentoRepositoryImpl implements AproveitamentoRepository {
    private Map<Long, Aproveitamento> banco = new HashMap<>();
    private Long proximoId = 1L;
    @Override
    public void salvar(Aproveitamento a) {
        if (a.getId() == null) a.setId(proximoId++);
        banco.put(a.getId(), a);
    }

    @Override
    public Aproveitamento buscaPorId(Long id) {
        return banco.get(id);
    }

    @Override
    public List<Aproveitamento> listarPorStatus(Status status) {
        return banco.values().stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }


    @Override
    public List<Aproveitamento> listarPorDiscente(String matricula) {
        return banco.values().stream()
                .filter(a -> a.getDiscente().getMatricula().equals(matricula))
                .collect(Collectors.toList());
    }


    @Override
    public List<Aproveitamento> listarTodas(){
        return List.copyOf(banco.values());
    }
}