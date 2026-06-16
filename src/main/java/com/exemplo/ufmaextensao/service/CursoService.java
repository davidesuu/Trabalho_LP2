package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.CursoDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepo cursoRepo;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SecurityService securityService;

    /**
     * Essa função busca um Curso por id
     * @param idCurso chave do curso única
     * @return throw exception se não encontrar, Curso na base se existir
     */
    public Curso buscarCursoPorId(Integer idCurso) throws RegraDeNegocioException {
        return cursoRepo.findCursoById(idCurso)
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
    }

    /**
     * Essa função salva um curso no repositorio após validar as informações do Curso instanciado e validar permisão do usuario
     * @param curso instancia do Curso a ser adicionado no repositorio
     * @param idUsuario Id do usuario que está criando o curso
     * @return retorna Curso após salvar no repositorio
     */
    public Curso criarCurso(CursoDTO cursoDTO, Integer idUsuario)
            throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(idUsuario);
        securityService.validarPermissao(usuario, "ADMIN", "COORDENADOR");
        if (cursoDTO.getNome() == null || cursoDTO.getNome().isBlank()) {
            throw new RegraDeNegocioException("Nome do curso é obrigatório");
        }
        if (cursoDTO.getCodigo() == null) {
            throw new RegraDeNegocioException("Código do curso é obrigatório");
        }
        Curso curso1 = Curso.builder()
                .nome(cursoDTO.getNome())
                .codigo(cursoDTO.getCodigo())
                .build();
        return cursoRepo.save(curso1);
    }

    public List<Curso> obterCursos(){
        List<Curso> cursos = cursoRepo.findAll();
        return  cursos;
    }
}
