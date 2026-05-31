package Entity;

public class Curso {
    private Long cursoId;
    private String nome;
    private Integer codigo;

    public Curso(String nome, Integer codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Curso: " + nome + " | Código: " + codigo;
    }
}