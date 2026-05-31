package Entity;


public class Log {
    private String matricula;
    private String nome;
    private String grupo;
    private String cargo;

    public Log(String matricula, String nome, String grupo, String cargo) {
        this.matricula = matricula;
        this.nome = nome;
        this.grupo = grupo;
        this.cargo = cargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getCargo() {
        return cargo;
    }
}
