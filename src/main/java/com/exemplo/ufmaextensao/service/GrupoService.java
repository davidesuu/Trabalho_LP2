package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.GrupoDTO;
import com.exemplo.ufmaextensao.entity.*;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import com.exemplo.ufmaextensao.repository.GrupoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepo grupoRepo;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepo docenteRepo;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private DiscenteRepo discenteRepo;

    /**
     * Essa função cria um novo grupo
     * @param grupoDTO instancia do grupo a ser adicionado no repositorio
     * @param idUsuario id do usuario que quer criar um novo grupo
     * @param idDocente id do docente responsavel pelo novo grupo
     * @return retorna Grupo após salvar no repositorio
     * @throws RegraDeNegocioException
     */
    public Grupo criarGrupo(GrupoDTO grupoDTO, Integer idUsuario, Integer idDocente) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(idUsuario);
        securityService.validarPermissao(usuario, "ADMIN", "COORDENADOR");
        if (grupoDTO.getNome() == null || grupoDTO.getNome().isBlank()) {
            throw new RegraDeNegocioException("Nome do grupo é obrigatório");
        }
        if (grupoDTO.getEmail() == null || grupoDTO.getEmail().isBlank()) {
            throw new RegraDeNegocioException("Email do grupo é obrigatório");
        }
        if (grupoDTO.getDescricao() == null || grupoDTO.getDescricao().isBlank()) {
            throw new RegraDeNegocioException("Descricao do grupo é obrigatória");
        }
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        Grupo grupo = Grupo.builder()
                .nome(grupoDTO.getNome())
                .email(grupoDTO.getEmail())
                .descricao(grupoDTO.getDescricao())
                .responsavel(docente)
                .build();
        return grupoRepo.save(grupo);
    }

    /**
     * Adiciona um novo membro a um grupo
     * @param idGrupo id do grupo em que vai ser adicionado membro novo
     * @param idDiscente id do discente que será adicionado ao grupo
     * @param idDocente id do docente responsavel pelo grupo
     * @throws RegraDeNegocioException
     */
    public void adicionarMembro(Integer idGrupo, Integer idDiscente, Integer idDocente) throws RegraDeNegocioException{
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));
        List<Grupo> g = discente.getGrupos();
        g.add(grupo);
        discente.setGrupos(g);

        List<Discente> m = grupo.getDiscentes();
        m.add(discente);
        grupo.setDiscentes(m);

        grupoRepo.save(grupo);
        discenteRepo.save(discente);
    }

    /**
     * Da a um discente um cargo especifico
     * @param idGrupo id do grupo a que o membro pertence
     * @param idDiscente id do discente que recebe novo cargo
     * @param idDocente id do docente responsavel pelo grupo
     * @param cargo string com o cargo que discente recebe
     * @throws RegraDeNegocioException
     */
    public void promoverMembro(Integer idGrupo, Integer idDiscente, Integer idDocente, String cargo) throws RegraDeNegocioException{
        if (cargo == null || cargo.isBlank()){
            throw new RegraDeNegocioException("Cargo precisa ser um cargo válido");
        }

        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));
        List<Papel> papeis = discente.getPapeis();
        Papel p = new Papel();
        p.setNome(cargo);
        papeis.add(p);
        discente.setPapeis(papeis);

        List<Discente> d = grupo.getDiretoria();
        d.add(discente);
        grupo.setDiretoria(d);

        List<Grupo> grupo_director = discente.getGruposDiretor();
        grupo_director.add(grupo);
        discente.setGruposDiretor(grupo_director);
        grupoRepo.save(grupo);
        discenteRepo.save(discente);
    }

    /**
     * essa funcao remove um discente de um grupo
     * @param idGrupo id do grupo a que o discente pertence
     * @param idDiscente id do discente que sera removido
     * @param idDocente id do docente responsavel pelo grupo
     * @throws RegraDeNegocioException
     */
    public void removerMembro(Integer idGrupo, Integer idDiscente, Integer idDocente) throws RegraDeNegocioException{
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        grupo.getDiscentes().stream().filter(d -> discente.equals(d)).findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("Discente não faz parte do grupo"));

        grupo.getDiretoria().stream().filter(d -> discente.equals(d)).findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("Discente não faz parte da diretoria do grupo"));

        //List<Papel> papeis = discente.getPapeis();
        //papeis.remove();
        //papeis.add(p);
        //discente.setPapeis(papeis);
        //fazer funcao p rebaixar algm

        List<Discente> d = grupo.getDiretoria();
        d.remove(discente);
        grupo.setDiretoria(d);

        List<Grupo> g = discente.getGrupos();
        g.remove(grupo);
        discente.setGrupos(g);

        List<Discente> m = grupo.getDiscentes();
        m.remove(discente);
        grupo.setDiscentes(m);
        grupoRepo.save(grupo);
        discenteRepo.save(discente);
    }
}
