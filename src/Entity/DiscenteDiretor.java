package Entity;

import Service.OportunidadeService;
import Enum.*;

import java.time.LocalDate;

public class DiscenteDiretor extends Discente {
    private Grupo grupo;
    private Cargo cargo;
    private LocalDate data_inicio;
    private LocalDate data_fim;

    public DiscenteDiretor(
            Discente disc,
            Cargo cargo,
            Integer duracao,
            Grupo grupo
    ){
        super(disc.getNome(), disc.getEmail(), disc.getSenha(), disc.getMatricula(), disc.getSemestre(), disc.getCurso());

        this.setId(disc.getId());

        this.cargo = cargo;
        this.grupo = grupo;
        this.data_inicio = LocalDate.now();
        this.data_fim = LocalDate.now().plusYears(duracao);
        this.setAtivo(true);
        this.setVinculo(disc.getVinculo());
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
