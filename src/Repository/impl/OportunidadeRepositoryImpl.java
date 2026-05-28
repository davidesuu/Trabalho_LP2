package Repository.impl;

import Entity.Oportunidade;
import Repository.OportunidadeRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import Util.GsonUtil;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;

import Enum.Status;

public class OportunidadeRepositoryImpl{

    private Map<Long, Oportunidade> banco = new HashMap<>();
    private Long proximoId = 1L;

    public void salvar(Oportunidade o) throws IOException {

        if (o.getId() == null) {
            o.setId(proximoId++);
        }

        banco.put(o.getId(), o);

        try (FileWriter escritor = new FileWriter("Oportunidades.json")) {

            GsonUtil.GSON.toJson(banco, escritor);
        }
    }

    public OportunidadeRepositoryImpl() {

        try (FileReader leitor = new FileReader("Oportunidades.json")) {

            Type tipo = new TypeToken<HashMap<Long, Oportunidade>>(){}.getType();

            this.banco = GsonUtil.GSON.fromJson(leitor, tipo);

            //TRATAR ARQUIVO VAZIO P.RA ONTEM!
            if (this.banco == null) {
                this.banco = new HashMap<>();
            }

            long maiorId = banco.keySet()
                    .stream()
                    .max(Long::compare)
                    .orElse(0L);

            this.proximoId = maiorId + 1;

        } catch (Exception e) {

            this.banco = new HashMap<>();

        }
    }


    public Oportunidade buscaPorId(long id) {

        return banco.get(id);

    }


    public List<Oportunidade> listarPorStatus(Status status) {

        return banco.values()
                .stream()
                .filter(o -> o.getStatus().equals(status))
                .collect(Collectors.toList());

    }


    public List<Oportunidade> listarTodas(){

        return List.copyOf(banco.values());

    }

    public List<Long> listarKeys(List<Oportunidade> oportunidades){

        return oportunidades.stream()
                .map(o -> o.getId())
                .toList();
    }
}