package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.DocenteDTO;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepo docenteRepo;

    public Docente criarDocente(DocenteDTO docenteDTO, Integer usuarioId) throws RegraDeNegocioException{
        if(usuarioId == null){
            throw new RegraDeNegocioException("Usuario invalido");
        }
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "COORDENADOR", "ADMIN");
        if(docenteDTO.getNome().isEmpty() || docenteDTO.getNome() == null){
            throw new RegraDeNegocioException("Nome invalido");
        }
        if (docenteDTO.getSenha().isEmpty() || docenteDTO.getSenha() == null){
            throw new RegraDeNegocioException("Senha invalida");
        }
        if (docenteDTO.getEmail().isEmpty() || docenteDTO.getEmail() == null){
            throw new RegraDeNegocioException("Email invalido");
        }
        if (docenteDTO.getSiape().isEmpty() || docenteDTO.getSiape() == null){
            throw new RegraDeNegocioException("Siape invalida");
        }
        if (docenteDTO.getDepartamento().isEmpty() || docenteDTO.getDepartamento() == null){
            throw new RegraDeNegocioException("Departamento invalido");
        }
        Docente docente = Docente.builder().nome(docenteDTO.getNome()).
                email(docenteDTO.getEmail()).
                senha(docenteDTO.getSenha()).
                siape(docenteDTO.getSiape())
                .departamento(docenteDTO.getDepartamento()).build();
        docente.setAtivo(true);
        return docenteRepo.save(docente);
    }

    public Docente buscarPorId(Integer docenteId) throws RegraDeNegocioException{
        return docenteRepo.findDocenteById(docenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));
    }

}
