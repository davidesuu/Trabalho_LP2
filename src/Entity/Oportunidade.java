package Entity;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Enum.Status;

import java.time.LocalDate;
import java.util.Objects;

public class Oportunidade {
    private String titulo, descricao; //get e set
    private Long id;
    private TipoOportunidade tipo;
    private Modalidade modalidade;  //get e set
    private int carga_horaria; //get e set
    private int vagas; //get e set
    private int vagasOcupadas;
    private Status status; //get e set
    private LocalDate inicio; //get e set
    private LocalDate fim; //get e set
    private Usuario autor;
    private Docente responsavel;

    public Oportunidade (String titulo, String descricao,
                        TipoOportunidade tipo,
                        Modalidade modalidade, int carga_horaria, int vagas, Usuario autor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.carga_horaria = carga_horaria;
        this.vagas = vagas;
        this.status = Status.PENDENTE;  //Repensa melhor depois se ela ja e pendente no começo
        this.autor = autor;
        this.vagasOcupadas = 0;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Oportunidade that = (Oportunidade) o;
        return carga_horaria == that.carga_horaria && vagas == that.vagas && Objects.equals(titulo, that.titulo) && Objects.equals(descricao, that.descricao) && Objects.equals(id, that.id) && tipo == that.tipo && modalidade == that.modalidade && status == that.status && Objects.equals(inicio, that.inicio) && Objects.equals(fim, that.fim) && Objects.equals(autor, that.autor) && Objects.equals(responsavel, that.responsavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao, id, tipo, modalidade, carga_horaria, vagas, status, inicio, fim, autor, responsavel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipo(TipoOportunidade tipo) {
        this.tipo = tipo;
    }

    public TipoOportunidade getTipo() {
        return tipo;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitulo() {
        return titulo;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void publicar(Docente docente){
        this.status = Status.PUBLICADA;
        this.responsavel = docente;
    }

    public void incrementaVagasOcupadas(int acrescimo) {
        this.vagasOcupadas += acrescimo;
    }
    public void decrementaVagasOcupadas(int acrescimo) {this.vagasOcupadas =- acrescimo;}

    public void rejeitar(Docente docente){
        this.responsavel = docente;
        this.status = Status.REJEITADO;
    }
    @Override
    public String toString() {

        return  "Título: " + titulo + "\n" +
                "Tipo: " + tipo + "\n" +
                "Modalidade: " + modalidade + "\n" +
                "Carga Horária: " + carga_horaria + "h\n" +
                "Vagas Livres: " + (vagas - vagasOcupadas) + "\n" +
                "Status: " + status + "\n" +
                "Descrição: " + descricao;
    }
}