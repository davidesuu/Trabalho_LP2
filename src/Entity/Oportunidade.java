package Entity;

public class Oportunidade {
    private String titulo;
    private String descricao;
    private Long id;
    private boolean disponivel;  // So de teste, depois tira

    public Oportunidade(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.disponivel = false; // tira depois
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisponivel() {   // tira depois
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "Oportunidade{" +
                "disponivel=" + disponivel +
                ", id=" + id +
                ", descricao='" + descricao + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}