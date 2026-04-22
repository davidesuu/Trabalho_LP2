package Repository;

import Entity.Docente;
import Entity.Grupo;

import java.util.List;

public interface GrupoRepository {
    void salvar(Grupo grupo);

    Grupo buscaPorId(Long id);

    List<Grupo> listarPorDocente(Docente docente);

    List<Grupo> listaTudo();
}
