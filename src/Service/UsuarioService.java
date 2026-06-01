package Service;

import Entity.*;
import Repository.impl.InscricoesRepositoryImpl;
import Repository.impl.LogRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private UsuarioRepositoryImpl usuarioRepository;
    private MatriculaService matriculaService;

    public UsuarioService(UsuarioRepositoryImpl usuarioRepository,  MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
        this.usuarioRepository = usuarioRepository;
    }

    public Discente cadastrarDiscente(String nome, String email, String senha,
                                      Integer semestre, Curso curso) {
        if (usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Email já cadastrado");
        }

        String matricula = gerarNumeroMatricula();

        Discente discente = new Discente(nome, email, senha, matricula, semestre, curso);
        discente.setAtivo(true);

        matriculaService.matricular(discente); // já salva o discente internamente

        return discente;
    }

//    public DiscenteDiretor cadastrarDiscenteDiretor(String nome, String email, String senha,
//                                      String matricula, Integer semestre, Curso curso, String cargo, int duracao, Grupo grupo){
//        if(usuarioRepository.buscarPorEmail(email).isPresent()){
//            throw new IllegalStateException("Email já cadastrado");
//        }
//
//        if (usuarioRepository.buscarPorMatricula(matricula).isPresent()){
//            throw new IllegalStateException();
//        }
//        DiscenteDiretor discenteDiretor = new DiscenteDiretor(nome, email, senha, matricula, semestre, curso, cargo, duracao, grupo);
//        usuarioRepository.salvar(discenteDiretor);
//        discenteDiretor.setAtivo(true);
//        return discenteDiretor;                             //Discente ----------- membro
//                                                            //Discente ------------ Diretor
//                                                            //Discetne = new DiscenteDiretor(grupo);
//    }

    public Docente cadastrarDocente(String nome, String email, String senha, String siape, String departamento){
        if(usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Email já cadastrado");
        }
        Docente docente = new Docente(nome, email, senha, siape, departamento);
        docente.setAtivo(true);
        usuarioRepository.salvar(docente);
        return docente;
    }

    public Coordenador cadastrarCoordenador(String nome, String email, String senha, String siape, String departamento){
        if(usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Email já cadastrado");
        }
        Coordenador coordenador = new Coordenador(nome, email, senha, siape, departamento);
        coordenador.setAtivo(true);
        usuarioRepository.salvar(coordenador);
        return coordenador;
    }

    public Discente buscarMatricula(String matricula){
        return usuarioRepository.buscarPorMatricula(matricula).orElseThrow(null);
    }

    public Usuario buscarId(Long id){
        return usuarioRepository.buscarPorId(id);
    }

    public void atualizarUsuario(Usuario usuario){
        usuarioRepository.salvar(usuario);

        if (usuario instanceof DiscenteDiretor diretor) {

            Log log = new Log(diretor.getMatricula(), diretor.getNome(), diretor.getGrupo().getNome(), diretor.getCargo().toString());

            LogRepositoryImpl LogRepository = new LogRepositoryImpl();
            LogRepository.registrar(log);
        }
    }

    private String gerarNumeroMatricula() {
        int ano = LocalDate.now().getYear();
        String matricula;
        do {
            int numero = ThreadLocalRandom.current().nextInt(1000, 9999);
            matricula = ano + String.format("%04d", numero);
        } while (usuarioRepository.buscarPorMatricula(matricula).isPresent());
        return matricula;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }



    public Docente buscarSiape(String siape){
        return usuarioRepository.buscarPorSiape(siape).orElseThrow(null);
    }
}
