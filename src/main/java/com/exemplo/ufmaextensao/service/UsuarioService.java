package com.exemplo.ufmaextensao.service;


import com.exemplo.ufmaextensao.dto.DiscenteDTO;
import com.exemplo.ufmaextensao.dto.LoginDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.CursoRepo;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.UsuarioRepo;
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
    private BCryptPasswordEncoder passwordEncoder;


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
}
