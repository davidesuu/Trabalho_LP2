package com.exemplo.ufmaextensao.service;


import com.exemplo.ufmaextensao.dto.DiscenteDTO;
import com.exemplo.ufmaextensao.dto.LoginDTO;
import com.exemplo.ufmaextensao.entity.*;
import com.exemplo.ufmaextensao.enums.StatusInscricao;
import com.exemplo.ufmaextensao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private GrupoRepo grupoRepo;

    @Autowired
    private InscricaoRepo inscricaoRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Esse metodo funciona como um login pra autenticar um usuario
     * @param usuario Dto com as credenciais do usuario
     * @return retorno o usuario
     * @throws RegraDeNegocioException
     */
    public Usuario autenticarUsuario(LoginDTO usuario) throws RegraDeNegocioException {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RegraDeNegocioException("Email invalido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RegraDeNegocioException("Senha invalida");
        }
        Usuario usuarioLogado = usuarioRepo.findByEmail(usuario.getEmail()).
                orElseThrow(() -> new RegraDeNegocioException("Email informado não existente"));
        if (!passwordEncoder.matches(usuario.getSenha(), usuarioLogado.getSenha())) {
            throw new RegraDeNegocioException("Senha incorreta");
        }
        return usuarioLogado;
    }

    /**
     * Essa função busca no UsuarioRepository um usuario pelo ID
     * @param usuarioId Id do usuario que está sendo buscado
     * @return  Retorna o usuario buscado se achar, lança regra de negocio casa não
     * @throws RegraDeNegocioException
     */
    public Usuario obterUsuarioPorId(Integer usuarioId) throws RegraDeNegocioException{
        return usuarioRepo.findById(usuarioId).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
    }

    /**
     * Essa função lista todos os usuarios do repositorio
     * @return Retorna uma lista com os usuarios do repositorio
     * @throws RegraDeNegocioException
     */
    public List<Usuario> listarUsuarios() throws RegraDeNegocioException {
        List<Usuario> usuarios = usuarioRepo.findAll();
        if(usuarios.isEmpty()){
            throw new RegraDeNegocioException("Nenhum usuario encontrado");
        }
        return usuarios;
    }

    /**
     * Essa função desativa um usuario. Caso ele seja discente, remove ele do grupo e caso ele seja da diretoria, lança excecao.
     * Remove também todas as inscrições pendentes do discente.
     * @param usuarioId
     * @param adminId
     * @throws RegraDeNegocioException
     */
    public void desativarUsuario(Integer usuarioId, Integer adminId) throws RegraDeNegocioException {
        Usuario admin = obterUsuarioPorId(adminId);
        securityService.validarPermissao(admin, "ADMIN");

        if (adminId.equals(usuarioId)) {
            throw new RegraDeNegocioException("Admin não pode desativar a si mesmo");
        }

        Usuario usuario = obterUsuarioPorId(usuarioId);

        if (!usuario.isAtivo()) {
            throw new RegraDeNegocioException("Usuário já está desativado");
        }

        if (usuario instanceof Docente docente) {
            List<Grupo> gruposResponsavel = grupoRepo.findByResponsavel(docente);
            if (!gruposResponsavel.isEmpty()) {
                throw new RegraDeNegocioException(
                        "Docente é responsável por " + gruposResponsavel.size() + " grupo(s). " +
                                "Transfira a responsabilidade antes de desativar."
                );
            }
        }

        if (usuario instanceof Discente discente) {
            List<Inscricao> pendentes = inscricaoRepo.findByDiscenteAndStatus(discente, StatusInscricao.PENDENTE);
            for (Inscricao i : pendentes) {
                i.setStatus(StatusInscricao.CANCELADA);
                inscricaoRepo.save(i);
            }
            List<Grupo> grupos = grupoRepo.findByDiscentesContaining(discente);
            for (Grupo g : grupos) {
                g.getDiscentes().remove(discente);
                g.getDiretoria().remove(discente);
                grupoRepo.save(g);
            }
        }
        usuario.setAtivo(false);
        usuarioRepo.save(usuario);
    }

    /**
     * Essa função reativa um usuario desativado
     * @param usuarioId
     * @param adminId
     * @throws RegraDeNegocioException
     */
    public void reativarUsuario(Integer usuarioId, Integer adminId) throws RegraDeNegocioException {
        Usuario admin = obterUsuarioPorId(adminId);
        securityService.validarPermissao(admin, "ADMIN");

        if (adminId.equals(usuarioId)) {
            throw new RegraDeNegocioException("Admin não pode reativar a si mesmo");
        }

        Usuario usuario = obterUsuarioPorId(usuarioId);

        if (usuario.isAtivo()) {
            throw new RegraDeNegocioException("Usuário já está ativo");
        }

        usuario.setAtivo(true);
        usuarioRepo.save(usuario);
    }
}
