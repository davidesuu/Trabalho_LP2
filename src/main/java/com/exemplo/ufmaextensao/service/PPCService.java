package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.PPCDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.PPCRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
     * @param ppcId Id do PPC
     * @return Retrona o ppc com a id especificada no banco
     * @throws RegraDeNegocioException
     */
    public PPC obterPPCPorId(Integer ppcId) throws RegraDeNegocioException {
        return ppcRepo.findPPCById(ppcId)
                .orElseThrow(()-> new RegraDeNegocioException("PPC não encontrado"));
    }
    /**
     * Essa função cria um PPC novo e vincula com um curso
     * @param ppcdto DTO de PPC
     * @param cursoId Id do curso que o PPC será vinculado
     * @param usuarioId Id do usuario que está criando o PPC
     * @return Retorna o PPC após salvar no repositorio
     * @throws RegraDeNegocioException
     */
    public PPC criarPPC(PPCDTO ppcdto, Integer cursoId, Integer usuarioId) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "COORDENADOR", "ADMIN");

        if(ppcdto.getAnoVigencia() == null){
            throw new RegraDeNegocioException("Ano de vigencia é obrigatorio");
        }
        if(ppcdto.getCargaHorariaTotal() == null){
            throw new RegraDeNegocioException("Carga Horaria invalida");
        }
        Curso curso = cursoService.buscarCursoPorId(cursoId);
        PPC ppc = PPC.builder()
                .anoVigencia(ppcdto.getAnoVigencia())
                .cargaHorariaTotal(ppcdto.getCargaHorariaTotal()).build();
        ppc.setCurso(curso);
        return ppcRepo.save(ppc);
    }

    /**
     * Essa função busca o PPC mais recente do curso
     * @param cursoId Id do curso do PPC
     * @return PPC mais recente do curso do parametro
     * @throws RegraDeNegocioException
     */
    public PPC buscarPPCMaisRecente(Integer cursoId) throws RegraDeNegocioException{
        Curso curso = cursoService.buscarCursoPorId(cursoId);
        return ppcRepo.findTopByCursoOrderByAnoVigenciaDesc(curso)
                .orElseThrow(()->new RegraDeNegocioException("Nenhum PPC encontrado para este curso"));
    }

    public List<PPC> listarPPCsPorCurso(Integer cursoId) throws RegraDeNegocioException{
        Curso curso = cursoService.buscarCursoPorId(cursoId);
        List<PPC> ppcs = ppcRepo.findPPCByCurso(curso);
        if(ppcs.isEmpty()){
            throw new RegraDeNegocioException("Nenhum PPC encontrado para esse curso");
        }
        return ppcs;
    }
}
