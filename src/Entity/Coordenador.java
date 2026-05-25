package Entity;

public class Coordenador extends Docente{
    public Coordenador(String nome, String email, String senha, String siape, String departamento){
        super(nome, email, senha, siape, departamento);
    }

    public Grupo criarGrupo(String nome, String email, String descricao, Docente docente){
        return new Grupo(nome, email, descricao, docente);
    }
}
