package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.PPCRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PPCService {
    @Autowired
    private PPCRepo ppcRepo;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SecurityService securityService;

    /**
     * Essa função encontra um PPC passando a ID
     * @param PPCid Id do PPC
     * @return Retrona o ppc com a id especificada no banco
     * @throws RegraDeNegocioException
     */
    public PPC obterPPCPorId(Integer PPCid) throws RegraDeNegocioException {
        return ppcRepo.findPPCById(PPCid)
                .orElseThrow(()-> new RegraDeNegocioException("PPC não encontrado"));
    }
    /**
     * Essa função cria um PPC novo e vincula com um curso
     * @param ppc Instancia de PPC
     * @param cursoId Id do curso que o PPC será vinculado
     * @param usuarioId Id do usuario que está criando o PPC
     * @return Retorna o PPC após salvar no repositorio
     * @throws RegraDeNegocioException
     */
    public PPC criarPPC(PPC ppc, Integer cursoId, Integer usuarioId) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "COORDENADOR", "ADMIN");

        if(ppc.getAnoVigencia() == null){
            throw new RegraDeNegocioException("Ano de vigencia é obrigatorio");
        }
        if(ppc.getCargaHorariaTotal() == null){
            throw new RegraDeNegocioException("Carga Horaria invalida");
        }
        Curso curso = cursoService.buscarCursoPorId(cursoId);
        ppc.setCurso(curso);
        return ppcRepo.save(ppc);

    }
}
