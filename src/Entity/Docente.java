package Entity;

import Enum.TipoOportunidade;
import Enum.Modalidade;

public class Docente extends Usuario{
    private String siape;
    private String departamento;

    public Docente(String nome, String email, String senha, String siape, String departamento){
        super(nome, email, senha);
        this.siape = siape;
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Siape: " + siape + "\n" +
                "Departamento: " + departamento + "\n";
    }

    @Override
    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo, Modalidade modalidade, int cargaHoraria, int vagas) {
        Oportunidade oportunidade = new Oportunidade(titulo, descricao, tipo, modalidade, cargaHoraria, vagas, this);
        oportunidade.publicar(this);
        return oportunidade;
    }

    public Grupo criarGrupo(String nome, String tipo, String email, String descricao){
        return new Grupo(nome, tipo, email, descricao, this);
    }
}
