package Service;

import Entity.*;
import Repository.impl.InscricoesRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;
import Enum.Status;

import java.time.LocalDate;
import java.util.Scanner;

public class UsuarioService {
    private UsuarioRepositoryImpl usuarioRepository;

    public UsuarioService(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Discente cadastrarDiscente(String nome, String email, String senha,
                                      String matricula, Integer semestre, Curso curso){
        if(usuarioRepository.buscarPorEmail(email).isPresent()){
            throw new IllegalStateException("Email já cadastrado");
        }

        if (usuarioRepository.buscarPorMatricula(matricula).isPresent()){
            throw new IllegalStateException();
        }

        Discente discente = new Discente(nome, email, senha, matricula, semestre, curso);
        usuarioRepository.salvar(discente);
        discente.setAtivo(true);
        return discente;
    }
    public Usuario getId(Long id){
        return usuarioRepository.buscaPorId(id);
    }

    public DiscenteDiretor cadastrarDiscenteDiretor(String nome, String email, String senha,
                                      String matricula, Integer semestre, Curso curso, String cargo, int duracao){
        if(usuarioRepository.buscarPorEmail(email).isPresent()){
            throw new IllegalStateException("Email já cadastrado");
        }

        if (usuarioRepository.buscarPorMatricula(matricula).isPresent()){
            throw new IllegalStateException();
        }
        DiscenteDiretor discenteDiretor = new DiscenteDiretor(nome, email, senha, matricula, semestre, curso, cargo, duracao);
        usuarioRepository.salvar(discenteDiretor);
        discenteDiretor.setAtivo(true);
        return discenteDiretor;
    }

    public Docente cadastrarDocente(String nome, String email, String senha, String siape, String departamento){
        if(usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Email já cadastrado");
        }
        Docente docente = new Docente(nome, email, senha, siape, departamento);
        usuarioRepository.salvar(docente);
        docente.setAtivo(true);
        return docente;
    }

}
