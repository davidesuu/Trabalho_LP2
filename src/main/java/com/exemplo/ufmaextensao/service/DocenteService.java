package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.DocenteDTO;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Papel;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import com.exemplo.ufmaextensao.repository.PapelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocenteService {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepo docenteRepo;

    @Autowired
    private PapelRepo papelRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Docente criarDocente(DocenteDTO docenteDTO, Integer usuarioId) throws RegraDeNegocioException{

        if(usuarioId == null){
            throw new RegraDeNegocioException("Usuario invalido");
        }
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "COORDENADOR", "ADMIN");
        if(docenteDTO.getNome() == null || docenteDTO.getNome().isBlank())
        {
            throw new RegraDeNegocioException("Nome invalido");
        }
        if (docenteDTO.getSenha() == null || docenteDTO.getSenha().isBlank()){
            throw new RegraDeNegocioException("Senha invalida");
        }
        if (docenteDTO.getEmail() == null || docenteDTO.getEmail().isBlank()){
            throw new RegraDeNegocioException("Email invalido");
        }
        if (docenteDTO.getSiape() == null || docenteDTO.getSiape().isBlank()){
            throw new RegraDeNegocioException("Siape invalida");
        }
        if (docenteDTO.getDepartamento() == null || docenteDTO.getDepartamento().isBlank()){
            throw new RegraDeNegocioException("Departamento invalido");
        }
        Papel papel = papelRepo.findByNome("DOCENTE").orElseThrow(() -> new RuntimeException("Papel informado não existe"));
        Docente docente = Docente.builder().nome(docenteDTO.getNome()).
                email(docenteDTO.getEmail()).
                senha(passwordEncoder.encode(docenteDTO.getSenha())).
                siape(docenteDTO.getSiape())
                .departamento(docenteDTO.getDepartamento()).build();
        docente.setAtivo(true);
        docente.getPapeis().add(papel);
        return docenteRepo.save(docente);
    }

    public Docente buscarPorId(Integer docenteId) throws RegraDeNegocioException{
        return docenteRepo.findDocenteById(docenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Docente não encontrado"));
    }

}
