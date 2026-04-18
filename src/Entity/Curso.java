package Entity;
import java.util.ArrayList;
import java.util.List;


public class Curso {
    private String nome;
    private int codigo;
    private int carga_horaria;
    private String versao_ppc;

    private List<Discente> alunos;


    public Curso(String nome, int codigo, int carga_horaria, String versao_ppc){
        this.nome = nome;
        this.codigo = codigo;
        this.carga_horaria = carga_horaria;
        this.versao_ppc = versao_ppc;

        this.alunos = new ArrayList<>();

    }

    // tentando fazer a bostinha dos metodos
    public void atualizarPPC (int horas, String versao){
        this.carga_horaria = horas;
        this.versao_ppc  = versao;
    }

    //public List<Discente> listarAlunosPorStatus(String Status){
        //List<Discente> resultado = new ArrayList<>();

        //for(Discente d: alunos){
        //    if(d.getStatus().equalsIgoneCase(Status)){
        //        resultado.add(d);


        //    }
        //}
        //return resultado;
    //}

    //public void adicionarAlunos(String alunos){
    //    alunos.add(aluno);
    //}


    public String getNome() {
        return nome;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getVersao_ppc() {
        return versao_ppc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}



