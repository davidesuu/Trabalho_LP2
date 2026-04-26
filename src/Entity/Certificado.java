package Entity;

import java.time.LocalDate;
import java.util.UUID;

import Enum.StatusAssinatura;

public class Certificado {
    private String uuid_hash;
    private Discente discente;
    private Oportunidade oportunidade;
    private LocalDate data_emissao;
    private Integer horas;
    private String path;
    private StatusAssinatura status_assinatura;

    public StatusAssinatura getStatus_assinatura() {
        return status_assinatura;
    }

    public void setStatus_assinatura(StatusAssinatura status_assinatura) {
        this.status_assinatura = status_assinatura;
    }

    public Certificado (Oportunidade oportunidade, Discente discente,
                        Integer hora){
        this.uuid_hash = UUID.randomUUID().toString();
        this.oportunidade = oportunidade;
        this.horas = horas;
        this.status_assinatura = status_assinatura.PENDENTE;  // depois faz uma funçao pro docente aprovar
        this.data_emissao = LocalDate.now();                  // Tira isso. quando o docente aprovar, isso aqui acontece
        this.discente = discente;
    }

    @Override
    public String toString() {
        return "uuid_hash: "+ uuid_hash + "\n"+
                "Discente: " + discente + "\n" +
                "Oportunidade: " + oportunidade + "\n" +
                "Data de Emissao: " + data_emissao + "\n"+
                "Horas: " + horas + "\n"+
                "Status da Assinatura: " + status_assinatura;
    }
}