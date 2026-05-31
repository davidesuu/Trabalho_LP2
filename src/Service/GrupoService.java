package Service;

import Entity.*;
import Repository.impl.GrupoRepositoryImpl;
import Repository.impl.LogRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class GrupoService {
    private final GrupoRepositoryImpl grupoRepository;
    private final LogRepositoryImpl logRepository;
    private final UsuarioRepositoryImpl usuarioRepository;

    public GrupoService(GrupoRepositoryImpl grupoRepository, LogRepositoryImpl logRepository, UsuarioRepositoryImpl usuarioRepository) {
        this.grupoRepository = grupoRepository;
        this.logRepository = logRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void adicionarMembro(Long grupoId, Discente discente) {
        Grupo grupo = grupoRepository.buscaPorId(grupoId);

        if (grupo == null) {
            throw new RuntimeException("Grupo não encontrado");
        }

        if (grupo.getMembroIds().contains(discente.getId())) {
            throw new RuntimeException("Discente já é membro do grupo");
        }

        String cargo = "Membro";
        if (discente instanceof DiscenteDiretor diretor) {
            cargo = diretor.getCargo().toString();
        }

        grupo.getMembroIds().add(discente.getId());
        grupoRepository.salvar(grupo);

        Log log = new Log(discente.getMatricula(), discente.getNome(), grupo.getNome(), cargo);
        logRepository.registrar(log);
    }

    public void removerMembro(Discente discente, Grupo grupo) {
        grupo.getMembroIds().remove(discente.getId());
        grupoRepository.salvar(grupo);
    }

    public List<Discente> getMembros(Grupo grupo) {
        return grupo.getMembroIds().stream()
                .map(id -> usuarioRepository.buscarPorId(id))
                .filter(u -> u instanceof Discente)
                .map(u -> (Discente) u)
                .collect(Collectors.toList());
    }

    public Grupo buscarGrupoPorId(Long id) {
        return grupoRepository.buscaPorId(id);
    }

    public List<Grupo> listarPorDocente(Docente docente) {
        return grupoRepository.listarPorDocente(docente);
    }

    public List<Grupo> listarTudo() {
        return grupoRepository.listaTudo();
    }

    public Grupo criarGrupo(String nome, String email, String descricao, Docente docente, Coordenador coordenador) {
        Grupo grupo = coordenador.criarGrupo(nome, email, descricao, docente);
        grupoRepository.salvar(grupo);
        return grupo;
    }

    public void atualizarGrupo(Grupo grupo) {
        grupoRepository.salvar(grupo);
    }

    public List<Long> ListarIndice(List<Grupo> grupos) {
        List<Long> ids = grupoRepository.listarKeys(grupos);
        Integer menuIndex = 1;

        for (Long realId : ids) {
            System.out.println("[" + menuIndex + "]\n" + grupoRepository.buscaPorId(realId) + "\n");
            menuIndex++;
        }
        return ids;
    }

    public String exibirGrupo(Grupo grupo) {
        StringBuilder membrosStr = new StringBuilder();

        List<Discente> membros = getMembros(grupo);

        if (membros.isEmpty()) {
            membrosStr.append("Nenhum membro");
        } else {
            for (Discente membro : membros) {
                membrosStr.append("- ")
                        .append(membro.getNome())
                        .append(" (")
                        .append(membro.getMatricula())
                        .append(")");

                if (membro instanceof DiscenteDiretor diretor) {
                    membrosStr.append(" - ").append(diretor.getCargo());
                }

                membrosStr.append("\n");
            }
        }

        return "Nome: " + grupo.getNome() + "\n" +
                "Descrição: " + grupo.getDescricao() + "\n" +
                "Responsável: " + grupo.getResponsavel().getNome() + "\n" +
                "Status: " + grupo.getStatus() + "\n\n" +
                "Membros:\n" + membrosStr;
    }
}