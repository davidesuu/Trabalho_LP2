package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.GrupoDTO;
import com.exemplo.ufmaextensao.entity.*;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import com.exemplo.ufmaextensao.repository.GrupoRepo;
import com.exemplo.ufmaextensao.repository.PapelRepo;
import com.exemplo.ufmaextensao.repository.LogRepo;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepo grupoRepo;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepo docenteRepo;

    @Autowired
    private PapelRepo papelRepo;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private DiscenteRepo discenteRepo;
    @Autowired
    private LogRepo logRepo;

    /**
     * Essa função cria um novo grupo
     * @param grupoDTO instancia do grupo a ser adicionado no repositorio
     * @param idUsuario id do usuario que quer criar um novo grupo
     * @param idDocente id do docente responsavel pelo novo grupo
     * @return retorna Grupo após salvar no repositorio
     * @throws RegraDeNegocioException
     */
    @Transactional
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
    @Transactional
    public Grupo adicionarMembro(Integer idGrupo, Integer idDiscente, Integer idDocente) throws RegraDeNegocioException{
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        if (grupo.getDiscentes().contains(discente)) {
            throw new RegraDeNegocioException("Discente já é membro desse grupo");
        }

        grupo.getDiscentes().add(discente);
        grupo = grupoRepo.save(grupo);

        logRepo.save(Log.builder()
                .nomeAutor(docente.getNome())
                .matriculaAutor(docente.getSiape())
                .nomeAfetado(discente.getNome())
                .matriculaAfetado(discente.getMatricula())
                .nomeGrupo(grupo.getNome())
                .cargo("Membro")
                .operacao("ADICIONAR")
                .dataHora(LocalDateTime.now())
                .build());

        return grupo;
    }

    /**
     * Da a um discente um cargo especifico
     * @param idGrupo id do grupo a que o membro pertence
     * @param idDiscente id do discente que recebe novo cargo
     * @param idDocente id do docente responsavel pelo grupo
     * @param cargo string com o cargo que discente recebe
     * @throws RegraDeNegocioException
     */
    public Grupo promoverMembro(Integer idGrupo, Integer idDiscente, Integer idDocente, String cargo) throws RegraDeNegocioException{
        if (cargo == null || cargo.isBlank()){
            throw new RegraDeNegocioException("Cargo precisa ser um cargo válido");
        }

        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        if (!grupo.getDiscentes().contains(discente)) {
            throw new RegraDeNegocioException("Discente precisa ser membro do grupo antes de receber um cargo");
        }

        if (grupo.getDiretoria().contains(discente)) {
            throw new RegraDeNegocioException("Discente já faz parte da diretoria desse grupo");
        }

        Papel papel = papelRepo.findByNome(cargo).orElseThrow(()-> new RegraDeNegocioException("Papel Invalido"));


        if (!discente.getPapeis().contains(papel)) {
            discente.getPapeis().add(papel);
        }

        grupo.getDiretoria().add(discente);

        grupoRepo.save(grupo);
        discenteRepo.save(discente);
        
        logRepo.save(Log.builder()
                .nomeAutor(docente.getNome())
                .matriculaAutor(docente.getSiape())
                .nomeAfetado(discente.getNome())
                .matriculaAfetado(discente.getMatricula())
                .nomeGrupo(grupo.getNome())
                .cargo(cargo)
                .operacao("PROMOVER")
                .dataHora(LocalDateTime.now())
                .build());

        return grupo;
    }

    /**
     * essa funcao remove um discente de um grupo
     * @param idGrupo id do grupo a que o discente pertence
     * @param idDiscente id do discente que sera removido
     * @param idDocente id do docente responsavel pelo grupo
     * @throws RegraDeNegocioException
     */
    public Grupo removerMembro(Integer idGrupo, Integer idDiscente, Integer idDocente) throws RegraDeNegocioException{
        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        if (!grupo.getDiscentes().remove(discente)) {
            throw new RegraDeNegocioException("Discente não pertence a esse grupo");
        }

        // se o discente também for diretoria
        grupo.getDiretoria().remove(discente);

        grupo = grupoRepo.save(grupo);

        logRepo.save(Log.builder()
                .nomeAutor(docente.getNome())
                .matriculaAutor(docente.getSiape())
                .nomeAfetado(discente.getNome())
                .matriculaAfetado(discente.getMatricula())
                .nomeGrupo(grupo.getNome())
                .cargo("Membro")
                .operacao("REMOVER")
                .dataHora(LocalDateTime.now())
                .build());

        return grupo;
    }

    /**
     * essa funcao rebaixa o cargo de um discente de um grupo
     * @param idGrupo id do grupo a que o discente pertence
     * @param idDiscente id do discente que sera rebaixado
     * @param idDocente id do docente responsavel pelo grupo
     * @param cargo string com o cargo que discente tem e perderá
     * @throws RegraDeNegocioException
     */
    public Grupo rebaixarMembro(Integer idGrupo, Integer idDiscente, Integer idDocente, String cargo) throws RegraDeNegocioException{
        if (cargo == null || cargo.isBlank()){
            throw new RegraDeNegocioException("Cargo precisa ser um cargo válido");
        }

        Grupo grupo = grupoRepo.findById(idGrupo).orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado"));
        Docente docente = docenteRepo.findById(idDocente).orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));

        if (!grupo.getResponsavel().equals(docente)) {
            throw new RegraDeNegocioException("Este Docente não é responsavel por esse grupo");
        }

        Discente discente = discenteRepo.findById(idDiscente).orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));

        if (!grupo.getDiretoria().remove(discente)) {
            throw new RegraDeNegocioException("Discente não faz parte da diretoria desse grupo");
        }

        //só remove o Papel se o discente não for mais diretor em NENHUM outro grupo
        boolean aindaEDiretorEmOutroGrupo = grupoRepo.findByDiretoriaContaining(discente)
                .stream()
                .anyMatch(g -> !g.getId().equals(idGrupo));

        if (!aindaEDiretorEmOutroGrupo) {
            Papel papel = papelRepo.findByNome(cargo).orElseThrow(() -> new RegraDeNegocioException("Papel Invalido"));
            discente.getPapeis().remove(papel);
        }

        grupoRepo.save(grupo);
        discenteRepo.save(discente);

        logRepo.save(Log.builder()
                .nomeAutor(docente.getNome())
                .matriculaAutor(docente.getSiape())
                .nomeAfetado(discente.getNome())
                .matriculaAfetado(discente.getMatricula())
                .nomeGrupo(grupo.getNome())
                .cargo(cargo)
                .operacao("REBAIXAR")
                .dataHora(LocalDateTime.now())
                .build());

        return grupo;
    }

}
