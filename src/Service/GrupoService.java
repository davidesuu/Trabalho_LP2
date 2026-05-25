package Service;

import Entity.Discente;
import Entity.Docente;
import Entity.Coordenador;
import Entity.Grupo;
import Repository.impl.GrupoRepositoryImpl;

import java.util.List;
import java.util.Optional;

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

    public Grupo criarGrupo(String nome,String email, String descricao, Docente docente, Coordenador coordenador){
        Grupo grupo = coordenador.criarGrupo(nome, email, descricao, docente);
        grupoRepository.salvar(grupo);
        return grupo;
    }

    public List<Long> ListarIndice(List<Grupo> oportunidades) {

        List<Long> ids = grupoRepository.ListarKeys();

        Integer menuIndex = 1;

        for(Long realId : ids) {

            System.out.println(
                    "[" + menuIndex + "]\n"
                            + grupoRepository.buscaPorId(realId)
                            + "\n"
            );

            menuIndex++;
        }

        return ids;
    }
}