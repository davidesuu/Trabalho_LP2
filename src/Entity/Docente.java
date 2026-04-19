package Entity;

public class Docente extends Usuario{
    private String siape;
    private String departamento;

    public Docente(String nome, String email, String senha, String papel, String siape, String departamento){
        super(nome, email, senha, papel);
        this.siape = siape;
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Siape: " + siape + "\n" +
                "Departamento: " + departamento + "\n";
    }
}
