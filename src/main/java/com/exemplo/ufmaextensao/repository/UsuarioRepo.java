package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    public Usuario findByEmail(String email);
}
