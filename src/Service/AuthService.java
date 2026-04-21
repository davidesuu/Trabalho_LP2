package Service;

import Entity.Inscricao;
import Entity.Oportunidade;
import Entity.Usuario;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.InscricoesRepositoryImpl;
import Enum.Status;
import Repository.impl.UsuarioRepositoryImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class AuthService {
    private final UsuarioRepositoryImpl usuarioRepository;

    private Usuario usuarioLogado;

    public AuthService(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void mudarSenha (Usuario usuario, String nova_senha){
        usuario.setSenha(nova_senha);
    }


    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        //isso é a mesma coisa do if e usar o optional.get depois pra passar o usuario do optinal pro usuario

        if (!usuario.getStatus()) {
            throw new IllegalStateException("Usuario não ativo");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalStateException("Senha Incorreta");
        }

        this.usuarioLogado = usuario;
        return usuario;
    }

    public void logout(){
        this.usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
