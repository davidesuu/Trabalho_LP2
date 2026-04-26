package Repository.impl;

import Entity.Certificado;
import Repository.CertificadoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CertificadoRepositoryImpl implements CertificadoRepository {
    private HashMap<Long, Certificado> banco = new HashMap<>();
    Long proximoId = 1L;

    @Override
    public void salvar(Certificado certificado) {
        if (certificado.getId() == null){
            certificado.setId(proximoId++);
            banco.put(certificado.getId(), certificado);
        }
    }

    @Override
    public Certificado buscaPorId(Long id) {
        return banco.get(id);
    }

    @Override
    public List<Certificado> listarPorDiscente(String matricula) {
        return banco.values().stream()
                .filter(c -> c.getDiscente().getMatricula().equals(matricula))
                .collect(Collectors.toList());
    }

    @Override
    public List<Certificado> listaTudo() {
        return List.copyOf(banco.values());
    }
}
