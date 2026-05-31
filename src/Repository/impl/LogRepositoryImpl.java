package Repository.impl;

import Entity.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import Util.GsonUtil;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;

public class LogRepositoryImpl {
    private List<Log> logs = new ArrayList<>();
    private final String FILE = "Log.json";

    public LogRepositoryImpl() {
        carregarDados();
    }

    private void carregarDados(){
        try(FileReader leitor = new FileReader(FILE)){
            Type tipo = new TypeToken<ArrayList<Log>>(){}.getType();
            List<Log> dados = GsonUtil.GSON.fromJson(leitor, tipo);
            if (dados != null){
                this.logs = dados;
            }
        } catch (IOException e) {
            logs = new ArrayList<>();
        }
    }

    public void registrar(Log log){
        this.logs.add(log);
        salvarDados();
    }

    private void salvarDados(){
        try (FileWriter escritor = new FileWriter(FILE)) {
            GsonUtil.GSON.toJson(this.logs, escritor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
