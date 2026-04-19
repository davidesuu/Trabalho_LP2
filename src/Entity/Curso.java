package Entity;
import java.util.ArrayList;
import java.util.List;


public class Curso {
    private String nome;
    private Integer codigo;
    private Integer carga_horaria;
    private String versao_ppc;
    private ArrayList<Discente> alunos;


    public Curso(String nome, Integer codigo, Integer carga_horaria, String versao_ppc){
        this.nome = nome;
        this.codigo = codigo;
        this.carga_horaria = carga_horaria;
        this.versao_ppc = versao_ppc;

        this.alunos = new ArrayList<>();

    }

    public String getNome() {
        return nome;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(Integer carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getVersao_ppc() {
        return versao_ppc;
    }

    public void setVersao_ppc(String versao_ppc) {
        this.versao_ppc = versao_ppc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Discente> getAlunos() {
        return alunos;
    }
}



