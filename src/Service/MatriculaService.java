package Service;
import Entity.Discente;
import Entity.Vinculo;
import Repository.impl.UsuarioRepositoryImpl;

public class MatriculaService {
    private final UsuarioRepositoryImpl usuarioRepository;
    private final PPCService ppcService;

    public MatriculaService(UsuarioRepositoryImpl usuarioRepository, PPCService ppcService) {
        this.usuarioRepository = usuarioRepository;
        this.ppcService = ppcService;
    }

    public Vinculo matricular(Discente discente) {
        Long cursoId = discente.getCurso().getCursoId();
        Long ppcId = ppcService.buscarMaisRecentePorCurso(cursoId).getId();

        Vinculo vinculo = new Vinculo(ppcId, "Ativo", 0);
        discente.setVinculo(vinculo);
        usuarioRepository.salvar(discente);
        return vinculo;
    }

    public void adicionarHoras(Discente discente, int horas) {
        Vinculo vinculo = discente.getVinculo();
        vinculo.setChTotalCumprida(vinculo.getChTotalCumprida() + horas);
        usuarioRepository.salvar(discente);
    }
}
