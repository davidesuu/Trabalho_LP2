package Repository;

import Entity.Discente;
import Entity.Docente;
import Entity.Certificado;

import java.util.List;

public interface CertificadoRepository {
    void salvar(Certificado certificado);

    Certificado buscaPorId(Long id);

    List<Certificado> listarPorDiscente(String matricula);

    List<Certificado> listaTudo();
}
