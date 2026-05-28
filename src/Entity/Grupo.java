package Entity;
import Enum.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Grupo {
    String nome;
    String email;
    String descricao;
    Status status;
    Docente responsavel;
    Long id;
    List<Discente> membros;
    public Grupo(String nome, String email, String descricao, Docente responsavel) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.status = Status.ATIVO;
        this.responsavel = responsavel;
        this.membros = new ArrayList<>();
        this.id = 0L;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Docente responsavel) {
        this.responsavel = responsavel;
    }

    public List<Discente> getMembros() {
        return membros;
    }

    public void setMembros(Discente usuario){
        this.membros.add(usuario);
    }

    @Override
    public String toString() {

        StringBuilder membrosStr = new StringBuilder();

        if(membros.isEmpty()){

            membrosStr.append("Nenhum membro");

        } else {

            for(Discente membro : membros){

                membrosStr.append("- ")
                        .append(membro.getNome())
                        .append(" (")
                        .append(membro.getMatricula())
                        .append(")");

                if(membro instanceof DiscenteDiretor diretor){
                    membrosStr.append(" - ")
                            .append(diretor.getCargo());
                }

                membrosStr.append("\n");
            }
        }

        return  "Nome: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Responsável: " + responsavel.getNome() + "\n" +
                "Status: " + status + "\n\n" +
                "Membros:\n" +
                membrosStr;
    }
}
