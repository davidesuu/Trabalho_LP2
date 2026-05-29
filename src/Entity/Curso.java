package Entity;
import java.util.ArrayList;
import java.util.List;


public class Curso {
    Long cursoId;
    private String nome;
    private Integer codigo;
    private ArrayList<Discente> alunos;


    public Curso(String nome, Integer codigo){
        this.nome = nome;
        this.codigo = codigo;
        this.alunos = new ArrayList<>();
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
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



