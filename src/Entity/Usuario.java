package Entity;

public class Usuario {
    protected String nome;
    protected String email;
    protected String senha;
    protected Papel papel;
    private boolean ativo;

    public Usuario(String nome, String email, String senha, Papel papel){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public Papel getPapel() {
        return papel;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String nova_senha) {
        this.senha = nova_senha;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Senha: " + senha + "\n" +
                "Papel: " + papel + "\n" +
                "Ativo: " + ativo + "\n";
    }
}
