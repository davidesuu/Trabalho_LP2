package Service;

import Entity.Curso;
import Repository.impl.CursoRepositoryImpl;

import java.util.List;

public class CursoService {

    private final CursoRepositoryImpl repository;

    public CursoService(CursoRepositoryImpl repository) {
        this.repository = repository;
    }

    public Curso criarCurso(String nome, Integer codigo) {

        Curso curso = new Curso(nome, codigo);
        repository.salvar(curso);
        return curso;
    }

    public Curso buscarPorId(Long id) {

        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + id));
    }

    public Curso buscarPorNome(String nome) {
        return repository.buscarPorNome(nome)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + nome));
    }

    public List<Curso> listarTodos() {
        return repository.listarTodos();
    }
}