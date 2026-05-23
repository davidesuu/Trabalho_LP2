package Entity;
import java.util.ArrayList;
import java.util.List;


public class Curso {
    private String nome;
    private Integer codigo;
    private ArrayList<Discente> alunos;


    public Curso(String nome, Integer codigo){
        this.nome = nome;
        this.codigo = codigo;
        this.alunos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Discente> getAlunos() {
        return alunos;
    }
}



