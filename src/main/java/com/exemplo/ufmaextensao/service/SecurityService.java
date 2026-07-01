package com.exemplo.ufmaextensao.service;

import java.util.List;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.entity.Papel;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public void validarPermissao(Usuario usuario, String... papeis) throws RegraDeNegocioException {
        if (usuario == null || usuario.getPapeis() == null) {
            throw new RegraDeNegocioException("Usuário inválido ou não autenticado.");
        }
        if (papeis == null || papeis.length == 0) {
            throw new RegraDeNegocioException("Permissões necessárias não especificadas.");
        }

        boolean hasPermissao = usuario.getPapeis().stream()
                .anyMatch(p -> p.getNome() != null && List.of(papeis).contains(p.getNome()));
        if (!hasPermissao) {
            throw new RegraDeNegocioException("Usuario não possui permissão para ação");
        }
    }
}
