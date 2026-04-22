package Service;

import Entity.Discente;
import Entity.Docente;
import Entity.Grupo;
import Repository.GrupoRepository;
import Repository.impl.GrupoRepositoryImpl;
import Enum.Status;
import java.util.List;
import java.util.Stack;

public class GrupoService {
    private GrupoRepositoryImpl grupoRepository;

    public GrupoService(GrupoRepositoryImpl grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public void adicionarMembro(Long id, Discente discente){
        //Depois
        grupoRepository.buscaPorId(id).setMembros(discente);
    }

    public List<Grupo> listarPorDocente(Docente docente ){
        return grupoRepository.listarPorDocente(docente);
    }

    public List<Grupo> listarTudo(){
        return grupoRepository.listaTudo();
    }

    public Grupo criarGrupo(String nome, String tipo ,String email, String descricao, Docente docente){
        Grupo grupo = docente.criarGrupo(nome, tipo, email, descricao);
        grupoRepository.salvar(grupo);
        return grupo;
    }

}