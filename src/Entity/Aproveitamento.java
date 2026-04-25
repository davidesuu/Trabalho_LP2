package Entity;
import Enum.Status;
public class Aproveitamento {
    private Discente discente;
    private String descricao;
    private String instituicao;
    private int horas;
    private Long id;
    private Status status;
    private String certificado_path;
    private Usuario avaliador;
    private String motivo_rejeicao;

    public Aproveitamento(Discente discente, int horas, String descricao, String instituicao, String certificado_path) {
        this.discente = discente;
        this.horas = horas;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.status = Status.PENDENTE;
        this.certificado_path = certificado_path;
    }

    public void avaliar(Usuario usuario){
        this.avaliador = usuario;
    }

    public void rejeitar(Usuario usuario){
        avaliar(usuario);
        this.status = Status.REJEITADO;
    }

    public void aprovar(Usuario usuario){
        avaliar(usuario);
        this.status = Status.APROVADO;
    }

//    public void publicar(Discente discente){
//        this.status = Status.PENDENTE;
//        this.discente = discente;
//    }

    public Discente getDiscente() {
        return discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCertificado_path() {
        return certificado_path;
    }

    public void setCertificado_path(String certificado_path) {
        this.certificado_path = certificado_path;
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador) {
        this.avaliador = avaliador;
    }

    public String getMotivo_rejeicao() {
        return motivo_rejeicao;
    }

    public void setMotivo_rejeicao(String motivo_rejeicao) {
        this.motivo_rejeicao = motivo_rejeicao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
