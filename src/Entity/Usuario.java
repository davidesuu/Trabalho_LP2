package Entity;
import Enum.*;

import java.util.Objects;

public class Usuario {
    protected String nome;
    protected String email;
    protected String senha;
    protected boolean ativo;
    protected Long id;

    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo, Modalidade modalidade, int cargaHoraria, int vagas) {
        throw new IllegalStateException("Usuario não pode criar Oportunidade");
    }

    public boolean getStatus() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String nova_senha) {
        this.senha = nova_senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Senha: " + senha + "\n" +
                "Ativo: " + ativo + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return ativo == usuario.ativo && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha, ativo, id);
    }
}
