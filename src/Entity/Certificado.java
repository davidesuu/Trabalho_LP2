package Entity;

import Enum.StatusAssinatura;
import java.time.LocalDate;
import java.util.UUID;

public class Certificado {
    private Long id;
    private String uuid_hash;
    private Long discenteId;
    private Long oportunidadeId;
    private Integer horas;
    private String path;
    private StatusAssinatura status_assinatura;
    private LocalDate data_emissao;

    public Certificado(Long oportunidadeId, Long discenteId, Integer horas) {
        this.uuid_hash = UUID.randomUUID().toString();
        this.oportunidadeId = oportunidadeId;
        this.discenteId = discenteId;
        this.horas = horas;
        this.status_assinatura = StatusAssinatura.PENDENTE;
    }

    public void assinar() {
        this.status_assinatura = StatusAssinatura.ASSINADO;
        this.data_emissao = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUuid_hash() { return uuid_hash; }
    public Long getDiscenteId() { return discenteId; }
    public Long getOportunidadeId() { return oportunidadeId; }
    public Integer getHoras() { return horas; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public StatusAssinatura getStatus_assinatura() { return status_assinatura; }
    public LocalDate getData_emissao() { return data_emissao; }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Hash: " + uuid_hash + "\n" +
                "Horas: " + horas + "\n" +
                "Status: " + status_assinatura + "\n" +
                "Emissão: " + data_emissao;
    }
}