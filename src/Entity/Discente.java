package Entity;

public class Discente extends Usuario{
    private String matricula;
    private Integer semestre;
    //private Curso curso;

    public Discente(String nome, String email, String senha, Papel papel, String matricula, Integer semestre){
        super(nome, email, senha, papel);
        //; private Curso curso;){
        this.matricula = matricula;
        this.semestre = semestre;
        //this.curso = curso;
    }

    @Override
    public String toString() {
        return "Matricula: " + matricula + "\n" +
                "Semestre: " + semestre + "\n";
    }
}
