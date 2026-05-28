package Repository.impl;

import Entity.Docente;
import Entity.Grupo;
import Entity.Usuario;
import Repository.GrupoRepository;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GrupoRepositoryImpl implements GrupoRepository {
    private HashMap<Long, Grupo> banco = new HashMap<>();
    private Long proximoId = 1L;


    public GrupoRepositoryImpl() {
        try (FileReader leitor = new FileReader("Grupos.json")) {

            Type tipo = new TypeToken<HashMap<Long, Grupo>>() {}.getType();

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
    @Override
    public void salvar(Grupo g){

        if (g.getId() == 0) {
            g.setId(proximoId++);
        }
        System.out.println(g.getId());
        banco.put(g.getId(), g);

        try (FileWriter escritor = new FileWriter("Grupos.json")) {

            GsonUtil.GSON.toJson(banco,escritor);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    @Override
    public List<Grupo> listarPorDocente(Docente docente){
        return banco.values().stream().filter(grupo ->
                grupo.getResponsavel().equals(docente)).collect(Collectors.toList());
    }

    @Override
    public List<Grupo> listaTudo(){
        return List.copyOf(banco.values());
    }

    @Override
    public Grupo buscaPorId(Long id) {
        return banco.get(id);
    }

    public List<Long> listarKeys(List<Grupo> grupos){
        return grupos.stream().map(g -> g.getId()).toList();
    }
}


