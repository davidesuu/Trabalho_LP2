package Entity;

import Enum.Status;

import java.time.LocalDate;

public class Inscricao {
    private Oportunidade oportunidade;
    //Discete
    private Status status;
    private String motivacao;
    private LocalDate created_at;
    private Long id;
    public Inscricao(Oportunidade oportunidade, Status status, String motivacao, LocalDate created_at) {
        this.oportunidade = oportunidade;
        this.status = status;
        this.motivacao = motivacao;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
