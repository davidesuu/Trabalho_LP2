package Entity;

import java.time.LocalDate;

import Enum.StatusAssinatura;

public class Certificado {
    private String uuid_hash;
    //private Discente discente;
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

    public Certificado (String uuid_hash, Oportunidade oportunidade, //Discente discente,
                        Integer horas, String path){
        this.uuid_hash = uuid_hash;
        this.oportunidade = oportunidade;
        this.horas = horas;
        this.path = path;
        this.status_assinatura = status_assinatura.PENDENTE;
        this.data_emissao = LocalDate.now();
        // this.discente = discente;
    }

    @Override
    public String toString() {
        return "uuid_hash: "+ uuid_hash + "\n"+
                //"Discente: " + discente + "\n" +
                "Oportunidade: " + oportunidade + "\n" +
                "Data de Emissao: " + data_emissao + "\n"+
                "Horas: " + horas + "\n"+
                "Status da Assinatura: " + status_assinatura;
    }
}