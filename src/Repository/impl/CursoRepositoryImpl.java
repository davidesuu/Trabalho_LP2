package Repository.impl;

import Entity.Curso;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.stream.Collectors;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;

public class CursoRepositoryImpl {

    private HashMap<Long, Curso> banco = new HashMap<>();

    private Long proximoId = 1L;

    public CursoRepositoryImpl() {

        try (FileReader leitor = new FileReader("Cursos.json")) {

            Type tipo = new TypeToken<HashMap<Long, Curso>>() {}.getType();

            banco = GsonUtil.GSON.fromJson(leitor, tipo);

            if (banco == null) {
                banco = new HashMap<>();
            }

            long maiorId = banco.keySet()
                    .stream()
                    .max(Long::compare)
                    .orElse(0L);

            this.proximoId = maiorId + 1;

        } catch (Exception e) {

            banco = new HashMap<>();

            this.proximoId = 1L;
        }
    }

    public Optional<Curso> buscarPorId(Long id) {

        return Optional.ofNullable(banco.get(id));
    }

    public Optional<Curso> buscarPorNome(String nome) {

        return banco.values()
                .stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
    public List<Curso> listarTodos() {
        return banco.values().stream().toList();
    }

    public void salvar(Curso curso) {

        if (curso.getCursoId() == null) {
            curso.setCursoId(proximoId++);
        }

        banco.put(curso.getCursoId(), curso);

        try (FileWriter escritor = new FileWriter("Cursos.json")) {

            Type tipo = new TypeToken<HashMap<Long, Curso>>() {}.getType();

            GsonUtil.GSON.toJson(banco, tipo, escritor);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}