package Service;

import Entity.PPC;
import Repository.impl.PPCRepositoryImpl;

public class PPCService {

    private final PPCRepositoryImpl repository;

    public PPCService(PPCRepositoryImpl repository) {
        this.repository = repository;
    }

    public PPC buscarPorId(Long id) {

        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("PPC não encontrado: " + id));
    }

    public PPC buscarMaisRecentePorCurso(Long cursoId) {

        return repository.buscarMaisRecentePorCurso(cursoId)
                .orElseThrow(() -> new RuntimeException("Nenhum PPC encontrado para o curso: " + cursoId));
    }

    public PPC criarPPC(Integer cursoId, int anoVigencia, int chTotalExigida) {

        PPC ppc = new PPC();
        ppc.setCursoId(cursoId);
        ppc.setAnoVigencia(anoVigencia);
        ppc.setCargaHorariaTotal(chTotalExigida);

        repository.salvar(ppc);

        return ppc;
    }
}