package Entity;

import Service.OportunidadeService;
import Enum.TipoOportunidade;
import Enum.Modalidade;

import java.time.LocalDate;

public class DiscenteDiretor extends Discente {
    private Grupo grupo;
    private String cargo;
    private LocalDate data_inicio;
    private LocalDate data_fim;

    public DiscenteDiretor(String nome, String email, String senha, String matricula,
                           Integer semestre, Curso curso, String cargo, Integer duracao){
        super(nome, email, senha, matricula, semestre, curso);
        this.cargo = cargo;
        this.data_inicio = LocalDate.now();
        this.data_fim = LocalDate.now().plusYears(duracao);  //vai mudar dps pq a pessoal pd ja estar no cargo
    }

    public String getCargo() {
        return cargo;
    }


    @Override
    public String toString() {
        return "Cargo: " + cargo + "\n" +
                "Data_inicio: " + data_inicio + "\n" +
                "Data_fim: " + data_fim + "\n";
    }

    @Override
    public Oportunidade criarOportunidade(String titulo, String descricao,TipoOportunidade tipo, Modalidade modalidade, int cargaHoraria, int vagas) {
        return new Oportunidade(titulo, descricao, tipo, modalidade, cargaHoraria, vagas, this);
    }
}
