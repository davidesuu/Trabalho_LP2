package Service;
import Entity.Discente;
import Entity.Matricula;
import Entity.PPC;
import Service.PPCService;
import Repository.impl.UsuarioRepositoryImpl;

public class MatriculaService {
    private final UsuarioService usuarioService;
    private final PPCService ppcService;

    public MatriculaService(UsuarioService usuarioService, PPCService ppcService) {
        this.usuarioService = usuarioService;
        this.ppcService = ppcService;
    }

    public Matricula matricular(Discente discente) {
        Long cursoId = discente.getCurso().getCursoId();
        Long ppcId = ppcService.buscarMaisRecentePorCurso(cursoId).getId();

        Matricula matricula = new Matricula(ppcId, "Ativo", 0);
        discente.setMatricula(matricula);
        return matricula;
    }

    public void adicionarHoras(Discente discente, int horas) {
        Matricula matricula = discente.getMatricula();
        matricula.setChTotalCumprida(matricula.getChTotalCumprida()+horas);
        usuarioService.atualizarUsuario(discente);
    }
}
