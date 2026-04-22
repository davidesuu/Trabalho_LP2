package Repository.impl;

import Entity.Docente;
import Entity.Grupo;
import Repository.GrupoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GrupoRepositoryImpl implements GrupoRepository {
    private final HashMap<Long, Grupo> banco = new HashMap<>();
    Long proximoId = 1L;
    @Override
    public void salvar(Grupo grupo){
        if(grupo.getId() == null) grupo.setId(proximoId++);
        banco.put(grupo.getId(), grupo);
    }

    @Override
    public List<Grupo> listarPorDocente(Docente docente){
        return banco.values().stream().filter(grupo ->
                grupo.getResponsavel().equals(docente)).collect(Collectors.toList());
    }

    @Override
    public List<Grupo> listaTudo(){
        return List.copyOf(banco.values());
    }

    @Override
    public Grupo buscaPorId(Long id) {
        return banco.get(id);
    }
}


