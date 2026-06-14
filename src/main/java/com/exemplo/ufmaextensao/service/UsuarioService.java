package com.exemplo.ufmaextensao.service;


import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
   private UsuarioRepo usuarioRepo;

    public Usuario obterUsuarioPorId(Integer id) {
        return usuarioRepo.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
    }


}
