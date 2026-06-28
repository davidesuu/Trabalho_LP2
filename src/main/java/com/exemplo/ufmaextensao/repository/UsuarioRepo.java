package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    public Optional<Usuario> findByEmail(String email);

    public Usuario findUsuarioById(Integer id);

}
