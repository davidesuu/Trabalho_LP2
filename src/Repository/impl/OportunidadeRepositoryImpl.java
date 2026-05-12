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

public class OportunidadeRepositoryImpl implements OportunidadeRepository {
    private Map<Integer, Oportunidade> banco = new HashMap<>();

    @Override
    public void salvar(Oportunidade o) throws IOException {
        banco.put(o.hashCode(), o);
        FileWriter escritor = new FileWriter("Oportunidades.json");
        GsonUtil.GSON.toJson(banco, escritor);
        escritor.close();

    }
    public OportunidadeRepositoryImpl() throws IOException {
        Type tipo = new TypeToken<HashMap<Integer, Oportunidade>>(){}.getType();
        FileReader leitor = new FileReader("Oportunidades.json");
        this.banco = GsonUtil.GSON.fromJson(leitor, tipo);
        leitor.close();  //TRATAR ARQUIVO VAZIO PRA ONTEM!
    }

    @Override
    public Oportunidade buscaPorId(int id) {
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

    public List<Integer> teste(){return List.copyOf(banco.keySet());}
}
