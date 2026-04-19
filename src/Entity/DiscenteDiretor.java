package Entity;

import Service.OportunidadeService;
import Enum.TipoOportunidade;
import Enum.Modalidade;

import java.time.LocalDate;

public class DiscenteDiretor extends Discente {
    //private Grupo grupo;
    private String cargo;
    private LocalDate data_inicio;
    private LocalDate data_fim;

    public DiscenteDiretor(String nome, String email, String senha, String papel, String matricula,
                           Integer semestre, Curso curso, String cargo, Integer duracao) {
        //Grupo grupo
        super(nome, email, senha, papel, matricula, semestre, curso);
        this.cargo = cargo;
        this.data_inicio = LocalDate.now();
        this.data_fim = LocalDate.now().plusYears(duracao);
    }

    public String getCargo() {
        return cargo;
    }

    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo,
                                          Modalidade modalidade, int cargaHoraria, int vagas,
                                          DiscenteDiretor autor) {
        return new Oportunidade(titulo, descricao, tipo, modalidade, cargaHoraria, vagas, autor);
    }

    @Override
    public String toString() {
        return "Cargo: " + cargo + "\n" +
                "Data_inicio: " + data_inicio + "\n" +
                "Data_fim: " + data_fim + "\n";
    }
}
