package Repository.impl;

import Entity.Certificado;
import Repository.CertificadoRepository;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CertificadoRepositoryImpl {

    private HashMap<Long, Certificado> banco = new HashMap<>();
    private Long proximoId = 1L;

    public CertificadoRepositoryImpl() {
        try (FileReader leitor = new FileReader("Certificados.json")) {

            Type tipo = new TypeToken<HashMap<Long, Certificado>>() {}.getType();
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

    public void salvar(Certificado certificado) {
        if (certificado.getId() == null) {
            certificado.setId(proximoId++);
        }

        banco.put(certificado.getId(), certificado);

        try (FileWriter escritor = new FileWriter("Certificados.json")) {
            Type tipo = new TypeToken<HashMap<Long, Certificado>>() {}.getType();
            GsonUtil.GSON.toJson(banco, tipo, escritor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Certificado buscaPorId(Long id) {
        return banco.get(id);
    }

    public List<Certificado> listarPorDiscente(Long discenteId) {
        return banco.values().stream()
                .filter(c -> c.getDiscenteId().equals(discenteId))
                .collect(Collectors.toList());
    }

    public List<Certificado> listaTudo() {
        return List.copyOf(banco.values());
    }
}