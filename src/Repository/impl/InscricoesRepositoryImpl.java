package Repository.impl;
import Entity.*;
import Entity.Grupo;
import Entity.Inscricao;
import Entity.Oportunidade;
import Enum.*;
import Repository.InscricaoRepository;
import Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InscricoesRepositoryImpl implements InscricaoRepository {

    HashMap<Long, Inscricao> banco = new HashMap<>();
    private Long proximoId = 1L;

    public InscricoesRepositoryImpl() {
        try (FileReader leitor = new FileReader("Inscricoes.json")) {

            Type tipo = new TypeToken<HashMap<Long, Inscricao>>() {}.getType();

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
    public void salvar(Inscricao inscricao) {
        if (inscricao.getId() == 0L) inscricao.setId(proximoId++);
        banco.put(inscricao.getId(), inscricao);

        try(FileWriter escritor = new FileWriter("Inscricoes.json")) {
            Type tipo = new TypeToken<HashMap<Long, Inscricao>>() {
            }.getType();
            GsonUtil.GSON.toJson(banco, tipo, escritor);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Inscricao buscarPorId(Long id) {
        return banco.get(id);
    }


    public List<Inscricao> listarPorDiscente(Discente discente) {
        return banco.values().stream().filter(i -> i.getDiscenteId().equals(discente.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Inscricao> buscarPorOportunidade(Oportunidade oportunidade) {
        return banco.values().stream()
                .filter(i -> i.getOportunidadeId().equals(oportunidade.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Inscricao> listarTodas() {
        return List.copyOf(banco.values());
    }

    @Override
    public List<Inscricao> listarStatus(StatusInscricao status){
        return banco.values().stream().filter(inscricao ->
                inscricao.getStatus().equals(status)).collect(Collectors.toList());
    }

    public List<Long> listarKeys(List<Inscricao> inscricoes){

        return inscricoes.stream()
                .map(o -> o.getId())
                .toList();
    }
}
