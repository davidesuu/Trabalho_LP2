package Repository;

import Entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    public Optional<Usuario> buscarPorEmail(String email);

    public Optional<Usuario> buscarPorMatricula(String matricula);

    public Usuario buscaPorId(Long id);

    public void salvar(Usuario usuario);


}
