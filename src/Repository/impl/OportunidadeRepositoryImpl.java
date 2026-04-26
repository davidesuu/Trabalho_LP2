package Repository.impl;

import Entity.Oportunidade;
import Repository.OportunidadeRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import Util.GsonUtil;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;

import Enum.Status;

public class OportunidadeRepositoryImpl implements OportunidadeRepository {
    private Map<Long, Oportunidade> banco = new HashMap<>();

    private Long proximoId = 1L;
    @Override
    public void salvar(Oportunidade o) throws IOException {
        if (o.getId() == null) {
            o.setId(proximoId++);
        }
        banco.put(o.getId(), o);
        FileWriter escritor = new FileWriter("Oportunidades.json");
        GsonUtil.GSON.toJson(banco, escritor);
        escritor.close();

    }
    public OportunidadeRepositoryImpl() throws IOException {
        Type tipo = new TypeToken<HashMap<Long, Oportunidade>>(){}.getType();
        FileReader leitor = new FileReader("Oportunidades.json");
        this.banco = GsonUtil.GSON.fromJson(leitor, tipo);
        if (banco != null){
            proximoId = Collections.max(banco.keySet()) + 1;
        }

        leitor.close();
    }

    @Override
    public Oportunidade buscaPorId(Long id) {
        return banco.get(id);
    }

    @Override
    public List<Oportunidade> listarPorStatus(Status status) {
        return banco.values().stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<Oportunidade> listarTodas(){
        return List.copyOf(banco.values());
    }
}
