package Repository.impl;

import Entity.Discente;
import Entity.Usuario;
import Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final List<Usuario> banco = new ArrayList<>();
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return banco.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    @Override
    public boolean buscarPorMatricula(String matricula) {
        for (Usuario u : banco){
            if (u instanceof Discente){
                if(((Discente) u).getMatricula().equals(matricula)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void salvar(Usuario usuario) {
        banco.add(usuario);
    }

}
