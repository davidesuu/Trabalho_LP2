package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.DiscenteDTO;
import com.exemplo.ufmaextensao.dto.HorasDiscenteDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DiscenteService {

    @Autowired
    private DiscenteRepo discenteRepo;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private PPCService ppcService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Essa função gera um numero de matricula único para o usuario
     * @return
     */
    private String gerarNumeroMatricula() {
        int ano = LocalDate.now().getYear();
        String matricula;
        do {
            int numero = ThreadLocalRandom.current().nextInt(1000, 9999);
            matricula = ano + String.format("%04d", numero);
        } while (discenteRepo.findByMatricula(matricula).isPresent());
        return matricula;
    }


    /**
     * Essa função cria um novo discente e adiciona ele ao discenteRepo
     * @param discenteDTO DTO com as informações do discente a ser cadastrado
     * @param usuarioId Id do usuario que está criando o discente
     * @param cursoId   Id do curso que o discente pertence
     * @return  Salva o discente no discenteRepo
     * @throws RegraDeNegocioException
     */
    public Discente criarDiscente(DiscenteDTO discenteDTO, Integer usuarioId, Integer cursoId) throws RegraDeNegocioException {
        if(usuarioId == null){
            throw new RegraDeNegocioException("Usuario invalido");
        }
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "COORDENADOR", "DOCENTE", "ADMIN");
        if(discenteDTO.getNome() == null || discenteDTO.getNome().isBlank()){
            throw new RegraDeNegocioException("Nome invalido");
        }
        if(discenteDTO.getEmail() == null || discenteDTO.getEmail().isBlank()){
            throw new RegraDeNegocioException("Email invalido");
        }
        if (discenteDTO.getSenha() == null || discenteDTO.getSenha().isBlank()){
            throw new RegraDeNegocioException("Senha invalida");
        }
        if(discenteDTO.getSemestre() == null){
            throw new RegraDeNegocioException("Semestre invalido");
        }
        if (cursoId == null){
            throw new RegraDeNegocioException("Curso invalido");
        }
        Curso curso = cursoService.buscarCursoPorId(cursoId);
        if (curso == null) {
            throw new RegraDeNegocioException("Curso invalido");
        }
        PPC ppc = ppcService.buscarPPCMaisRecente(cursoId);
        if (ppc == null) {
            throw new RegraDeNegocioException("PPC invalido");
        }


        Discente discente = Discente.builder().nome(discenteDTO.getNome()).email(discenteDTO.getEmail())
                .semestre(discenteDTO.getSemestre()).build();
        discente.setCurso(curso);
        discente.setBanco_de_horas(ppc.getCargaHorariaTotal());
        discente.setMatricula(gerarNumeroMatricula());
        discente.setAtivo(true);
        discente.setSenha(passwordEncoder.encode(discenteDTO.getSenha()));


        return discenteRepo.save(discente);
    }

    public Discente buscarPorId(Integer discenteId) throws RegraDeNegocioException {
        return discenteRepo.findById(discenteId)
                .orElseThrow(() -> new RegraDeNegocioException("Discente não encontrado"));
    }

    public Discente atualizar(Discente discente) throws RegraDeNegocioException {
        return discenteRepo.save(discente);
    }

    public HorasDiscenteDTO mostrarPainelDeHoras(Integer discenteId) throws RegraDeNegocioException {
        Discente discente = discenteRepo.findById(discenteId).
                orElseThrow(() -> new RegraDeNegocioException("Discente invalido"));
        HorasDiscenteDTO horasDiscenteDTO = HorasDiscenteDTO.builder().nome(discente.getNome()).
                horasTotal(discente.getBanco_de_horas()).horasCompletas(discente.getCh_total_cumprida()).build();
        return horasDiscenteDTO;
    }
}
