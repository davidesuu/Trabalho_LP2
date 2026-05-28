package Service;

import Entity.*;
import Repository.impl.GrupoRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class GrupoService {
    private GrupoRepositoryImpl grupoRepository;

    public GrupoService(GrupoRepositoryImpl grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public void adicionarMembro(Discente discente, Grupo grupo){
        grupo.setMembros(discente);
    }

    public Grupo buscarGrupoPorId(Long id){
        return grupoRepository.buscaPorId(id);
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

    public void atualizarGrupo(Grupo grupo){
        grupoRepository.salvar(grupo);
    }

    public List<Long> ListarIndice(List<Grupo> grupos) {

        List<Long> ids = grupoRepository.listarKeys(grupos);   ///explicando o fluxo
        Integer menuIndex = 1;                                   ///Faz uma lista com todos ids com o filtro selecionado

        for(Long realId : ids) {
            ///itera no ids, e usa o buscarporid para conseguir pegar
            System.out.println(                                  /// O valor no hashmap enquando mostra um id "falso"
                    "[" + menuIndex + "]\n"
                            + grupoRepository.buscaPorId(realId)
                            + "\n"
            );

            menuIndex++;
        }
        return ids;
    }
}