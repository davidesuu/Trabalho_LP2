package com.exemplo.ufmaextensao.service;

import java.util.List;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.entity.Papel;
import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import com.exemplo.ufmaextensao.entity.Usuario;

@Service
public class SecurityService {

    public void  validarPermissao(Usuario usuario, String...papeis) throws RegraDeNegocioException{

            boolean hasPermissao = usuario.getPapeis().stream().anyMatch(p-> List.of(papeis).contains(p.getNome()));
            if(!hasPermissao){
                throw new RegraDeNegocioException("Usuario não possui permisão para ação");
            }

    }
}
