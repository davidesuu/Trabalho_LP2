package Repository.impl;

import Entity.Usuario;
import Repository.UsuarioRepository;

import java.util.HashMap;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final HashMap<Long, Usuario> banco = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return banco.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorMatricula(String matricula) {
        return banco.values().stream().filter(u -> u.getEmail().equals(matricula)).findFirst();
    }

    @Override
    public Usuario buscaPorId(Long id) {
        return banco.get(id);
    }

    @Override
    public void salvar(Usuario u) {
        if (u.getId() == null) u.setId(proximoId++);
        banco.put(u.getId(), u);
    }

}
