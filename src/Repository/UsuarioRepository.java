package Repository;

import Entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    public Optional<Usuario> buscarPorEmail(String email);

    public boolean buscarPorMatricula(String matricula);

    public void salvar(Usuario usuario);


}
