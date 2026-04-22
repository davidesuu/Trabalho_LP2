package Repository.impl;
import Entity.*;
import Entity.Grupo;
import Entity.Inscricao;
import Entity.Oportunidade;
import Repository.InscricaoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InscricoesRepositoryImpl implements InscricaoRepository {

    HashMap<Long, Inscricao> banco = new HashMap<>();
    private Long proximoId = 1L;

    @Override
    public void salvar(Inscricao inscricao) {
        if (inscricao.getId() == null) inscricao.setId(proximoId++);
        banco.put(inscricao.getId(), inscricao);
    }

    @Override
    public Inscricao buscarPorId(Long id) {
        return banco.get(id);
    }

    //buscar por discente
    public List<Inscricao> listarPorDiscente(Discente discente){
        return banco.values().stream().filter(i -> i.getDiscente().equals(discente)).collect(Collectors.toList());
    }

    @Override
    public List<Inscricao> buscarPorOportunidade(Oportunidade oportunidade) {
        return banco.values().stream()
                .filter(i -> i.getOportunidade().equals(oportunidade))
                .collect(Collectors.toList());
    }

    @Override
    public List<Inscricao> listarTodas() {
        return List.copyOf(banco.values());
    }
}
