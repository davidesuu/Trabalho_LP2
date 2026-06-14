package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Curso criarCurso(Curso curso, Integer idUsuario)
            throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(idUsuario);
        securityService.validarPermissao(usuario, "ADMIN", "COORDENADOR");
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            throw new RegraDeNegocioException("Nome do curso é obrigatório");
        }
        if (curso.getCodigo() == null) {
            throw new RegraDeNegocioException("Código do curso é obrigatório");
        }
        return cursoRepo.save(curso);
    }
}
