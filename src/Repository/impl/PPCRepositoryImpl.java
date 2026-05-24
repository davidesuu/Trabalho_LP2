package Repository.impl;

import Entity.PPC;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class PPCRepositoryImpl {

    private HashMap<Long, PPC> banco = new HashMap<>();

    private Long proximoId = 1L;

    public PPCRepositoryImpl() {

        try (FileReader leitor = new FileReader("PPCs.json")) {

            Type tipo = new TypeToken<HashMap<Long, PPC>>() {}.getType();

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

    public Optional<PPC> buscarPorId(Long id) {

        return Optional.ofNullable(banco.get(id));
    }

    public Optional<PPC> buscarMaisRecentePorCurso(Long cursoId) {

        return banco.values()
                .stream()
                .filter(p -> p.getCursoId().equals(cursoId))
                .max(Comparator.comparing(PPC::getAnoVigencia));
    }

    public void salvar(PPC ppc) {

        if (ppc.getId() == null) {
            ppc.setId(proximoId++);
        }

        banco.put(ppc.getId(), ppc);

        try (FileWriter escritor = new FileWriter("PPCs.json")) {

            Type tipo = new TypeToken<HashMap<Long, PPC>>() {}.getType();

            GsonUtil.GSON.toJson(banco, tipo, escritor);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}