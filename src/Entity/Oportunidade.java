package Entity;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Enum.Status;

import java.time.LocalDate;

public class Oportunidade {
    private String titulo, descricao; //get e set
    private Long id;
    private TipoOportunidade tipo;
    private Modalidade modalidade;  //get e set
    private int carga_horaria; //get e set
    private int vagas; //get e set
    private Status status; //get e set
    private LocalDate inicio; //get e set
    private LocalDate fim; //get e set
    //Usuario
    //Docente


    public Oportunidade(String titulo, String descricao,
                        TipoOportunidade tipo,
                        Modalidade modalidade, int carga_horaria, int vagas) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.carga_horaria = carga_horaria;
        this.vagas = vagas;
        this.status = Status.PENDENTE;  //Repensa melhor depois se ela ja e pendente no começo
        this.inicio = LocalDate.now();
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

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Oportunidade{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", id=" + id +
                ", tipo=" + tipo +
                ", modalidade=" + modalidade +
                ", carga_horaria=" + carga_horaria +
                ", vagas=" + vagas +
                ", status=" + status +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}