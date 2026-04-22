package Entity;

import Enum.*;
import Repository.InscricaoRepository;

public class Discente extends Usuario{
    private String matricula;
    private Integer semestre;
    private Curso curso;

    public Discente(String nome, String email, String senha,
                    String matricula, Integer semestre, Curso curso){
        super(nome, email, senha);
        this.matricula = matricula;
        this.semestre = semestre;
        this.curso = curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Matricula: " + matricula + "\n" +
                "Nome: " + nome + "\n" +
                "Semestre: " + semestre + "\n";
    }

    public Inscricao criarInscricao(Oportunidade oportunidade, Discente discente, String motivacao){
        return new Inscricao(oportunidade, discente, motivacao);
    }

    public Aproveitamento criarAproveitamento(Discente discente, int horas, String descricao, String instituicao, String certificado_path){
        return new Aproveitamento(discente, horas, descricao, instituicao, certificado_path);
    }


}
