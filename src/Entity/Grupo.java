package Entity;
import Enum.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private Long id;
    private String nome;
    private String email;
    private String descricao;
    private StatusGrupo status;
    private Docente responsavel;
    private List<Long> membroIds = new ArrayList<>();
    public Grupo(String nome, String email, String descricao, Docente responsavel) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.status = StatusGrupo.ATIVO;
        this.responsavel = responsavel;
        this.id = 0L;
    }

    public List<Long> getMembroIds() {
        return membroIds;
    }

    public void setMembroIds(List<Long> membroIds) {
        this.membroIds = membroIds;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusGrupo getStatus() {
        return status;
    }

    public void setStatus(StatusGrupo status) {
        this.status = status;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Docente responsavel) {
        this.responsavel = responsavel;
    }

    public List<Long> getMembros() {
        return membroIds;
    }

    public void setMembros(Long id) {
        this.membroIds.add(id);
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Responsável: " + responsavel.getNome() + "\n" +
                "Status: " + status;
    }
}
