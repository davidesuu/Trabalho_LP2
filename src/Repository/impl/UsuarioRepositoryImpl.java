package Repository.impl;

import Entity.Discente;
import Entity.Docente;
import Entity.Usuario;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;

public class UsuarioRepositoryImpl {

    private HashMap<Long, Usuario> banco = new HashMap<>();

    private Long proximoId = 1L;

    public UsuarioRepositoryImpl() {

        try (FileReader leitor = new FileReader("Usuarios.json")) {

            Type tipo = new TypeToken<HashMap<Long, Usuario>>() {}.getType();

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

    public Optional<Usuario> buscarPorEmail(String email) {

        return banco.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Docente> buscarPorSiape(String siape) {

        return banco.values()
                .stream()
                .filter(u -> u instanceof Docente)
                .map(u -> (Docente) u)
                .filter(a -> a.getSiape().equals(siape))
                .findFirst();
    }

    public Optional<Discente> buscarPorMatricula(String matricula) {

        return banco.values()
                .stream()
                .filter(u -> u instanceof Discente)
                .map(u -> (Discente) u)
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst();
    }

    public void salvar(Usuario u) {

        if (u.getId() == null) {
            u.setId(proximoId++);
        }

        banco.put(u.getId(), u);

        try (FileWriter escritor = new FileWriter("Usuarios.json")) {

            Type tipo = new TypeToken<HashMap<Long, Usuario>>() {}.getType();

            GsonUtil.GSON.toJson(banco, tipo, escritor);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}