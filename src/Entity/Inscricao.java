package Entity;

import Enum.Status;

import java.time.LocalDate;

public class Inscricao {
    private Long oportunidadeId;
    private Long discenteId;
    private Status status;
    private String motivacao;
    private LocalDate created_at;
    private Long id;

    public Inscricao(Long oportunidadeId, Long discenteId, String motivacao) {
        this.oportunidadeId = oportunidadeId;
        this.discenteId = discenteId;
        this.status = Status.PENDENTE;
        this.motivacao = motivacao;
        this.created_at = LocalDate.now();
        this.id = 0L;
    }

    public Long getOportunidadeId() { return oportunidadeId; }
    public Long getDiscenteId() { return discenteId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getMotivacao() { return motivacao; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getCreated_at() { return created_at; }

    public void aprovar() { this.status = Status.APROVADO; }
    public void rejeitar() { this.status = Status.REJEITADO; }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Status: " + status + "\n" +
                "Motivação: " + motivacao + "\n" +
                "Data: " + created_at;
    }
}