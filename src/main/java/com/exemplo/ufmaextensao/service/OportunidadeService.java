package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.OportunidadeDTO;
import com.exemplo.ufmaextensao.Enum.StatusOportunidade;
import com.exemplo.ufmaextensao.entity.*;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import com.exemplo.ufmaextensao.service.TipoOportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import  java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepo oportunidadeRepo;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private TipoOportunidadeService tipoOportunidadeService;

    /**
     * Essa função salva uma oportunidade no repositorio após validar as informações da oportunidade instanciada e validar permisão do usuario
     * @param oportunidadeDTO instancia de oportunidade a ser adicionado ao repositorio
     * @param usuarioId ID do usuario que esta tentando criar a oportunidade
     * @return retorna uma Oportunidade após salvar no repositorio
     * @throws RegraDeNegocioException se as validacoes de dados ou de permissao falharem
     */

    public Oportunidade criarOportunidade(OportunidadeDTO oportunidadeDTO, Integer usuarioId, String tipo)
            throws RegraDeNegocioException {

        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "DOCENTE", "COORDENADOR", "ADMIN");

        if (oportunidadeDTO == null) {
            throw new RegraDeNegocioException("Os dados da oportunidade não foram informados.");
        }
        if (oportunidadeDTO.getNome() == null || oportunidadeDTO.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser vazio.");
        }
        if (oportunidadeDTO.getDescricao() == null || oportunidadeDTO.getDescricao().isBlank()) {
            throw new RegraDeNegocioException("A descrição não pode ser vazia.");
        }

        if (oportunidadeDTO.getTipoOportunidade() == null) {
            throw new RegraDeNegocioException("O tipo de oportunidade deve ser informado.");
        }

        if (oportunidadeDTO.getModalidade() == null) {
            throw new RegraDeNegocioException("A modalidade deve ser informada.");
        }
        if (oportunidadeDTO.getStatus() == null) {
            throw new RegraDeNegocioException("O status da oportunidade deve ser informado.");
        }

        if (oportunidadeDTO.getCarga_horaria() == null || oportunidadeDTO.getCarga_horaria() <= 0) {
            throw new RegraDeNegocioException("A carga horária deve ser maior que zero.");
        }
        if (oportunidadeDTO.getVagas() == null || oportunidadeDTO.getVagas() <= 0) {
            throw new RegraDeNegocioException("A quantidade de vagas deve ser maior que zero.");
        }
        if (oportunidadeDTO.getVagasOcupadas() == null) {
            throw new RegraDeNegocioException("A quantidade de vagas ocupadas deve ser informada.");
        }

        if (oportunidadeDTO.getIncio() == null) {
            throw new RegraDeNegocioException("A data de início deve ser informada.");
        }
        if (oportunidadeDTO.getFim() == null) {
            throw new RegraDeNegocioException("A data de fim deve ser informada.");
        }
        if (oportunidadeDTO.getFim().isBefore(oportunidadeDTO.getIncio())) {
            throw new RegraDeNegocioException("A data de fim não pode ser anterior à data de início.");
        }
        if (oportunidadeDTO.getResponsavel_oportunidade() == null) {
            throw new RegraDeNegocioException("O docente responsável deve ser informado.");
        }

        if(tipoOportunidadeService.buscarPorTipo(tipo)==null){
            throw new RegraDeNegocioException("Tipo de Oportunidade invalido");
        }
        TipoOportunidade tipoOportunidade = tipoOportunidadeService.buscarPorTipo(tipo);

        Oportunidade oportunidade = Oportunidade.builder()
                .titulo(oportunidadeDTO.getNome())
                .descricao(oportunidadeDTO.getDescricao())
                .oportunidade(tipoOportunidade)
                .modalidade(oportunidadeDTO.getModalidade())
                .carga_horaria(oportunidadeDTO.getCarga_horaria())
                .vagas(oportunidadeDTO.getVagas())
                .vagasOcupadas(oportunidadeDTO.getVagasOcupadas())
                .status(oportunidadeDTO.getStatus())
                .incio(oportunidadeDTO.getIncio())
                .fim(oportunidadeDTO.getFim())
                .responsavel_oportunidade(oportunidadeDTO.getResponsavel_oportunidade())
                .build();


        return oportunidadeRepo.save(oportunidade);
    }

    /**
     * Essa funçao buscas Oportunidades pelo id da oportunidade
     * @param id ID do registro de oportunidade a ser buscado no repositorio
     * @return oportunidade que estiver relacionada ao id
     * @throws RegraDeNegocioException se a oportunidade nao for encontrada
     */
    public Oportunidade buscar(Integer id) throws RegraDeNegocioException {
        return oportunidadeRepo.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Oportunidade não encontrada com o ID: " + id));
    }

    /**
     * Essa função salva a oportunidade diretamente no repositorio
     * @param o instancia de oportunidade a ser salva
     * @throws RegraDeNegocioException se a oportunidade for nula
     */
    public void salvarOportunidade(Oportunidade o) throws RegraDeNegocioException{
        if (o == null){
            throw new RegraDeNegocioException("a oportunidade não pode ser nula.");
        }
        oportunidadeRepo.save(o);
    }


    /**
     * Essa funçao atualiza uma oportunidade existente no repositorio apos validar que ela existe
     * @param oportunidade instancia de oportunidade contendo os dados atualizados e um ID valido
     * @throws RegraDeNegocioException se o ID for invalido ou se o registro nao existir no repositório
     */
    public void atualizarOportunidade(Oportunidade oportunidade) throws RegraDeNegocioException {
        if(oportunidade == null || oportunidade.getId() == null) {
            throw new RegraDeNegocioException("id da oportunidade inválido para atualização");
        }
        if(!oportunidadeRepo.existsById(oportunidade.getId())){
            throw new RegraDeNegocioException("não é possível atualizar: Oportunidade não encontrada");
        }
        oportunidadeRepo.save(oportunidade);
    }

    /**
     * Essa funçao altera o status de uma oportunidade para PUBLICADA e vincula o docente responsavel
     * @param id ID da oportunidade que vai ser publicada
     * @param docente instancia de docente que se tornara responsavel pela oportunidade publicada
     * @throws RegraDeNegocioException se o docente for nulo ou se a oportunidade ja estiver publicada
     */
    public void publicarOportunidade(Integer id, Docente docente) throws RegraDeNegocioException{
        if(docente == null) throw new RegraDeNegocioException("Um docente responsavel deve ser informado para publicar.");
        Oportunidade op = buscar(id);

        if(op.getStatus() == StatusOportunidade.PUBLICADA){
            throw new RegraDeNegocioException("esta oportunidade já está publicada.");
        }
        op.setStatus(StatusOportunidade.PUBLICADA);
        op.setResponsavel_oportunidade(docente);
        oportunidadeRepo.save(op);
    }

    /**
     * Essa funcao altera o status da oportunidade para REJEITADA caso ela esteja pendente
     * @param id ID da oportunidade a ser rejeitada
     * @param docente instancia do docente que esta executando a ação de rejeição
     * @throws RegraDeNegocioException se a oportunidade nao estiver com status PEBDENTE
     */
    public void rejeitarOportunidade(Integer id, Docente docente) throws RegraDeNegocioException{
        Oportunidade op = buscar(id);

        if(op.getStatus() != StatusOportunidade.PENDENTE){
            throw new RegraDeNegocioException("Apenas oportunidades pendentes podem ser rejeitadas");
        }
        op.setStatus(StatusOportunidade.REJEITADA);
        oportunidadeRepo.save(op);
    }

    //public List<Oportunidade> listarOportunidadesPossiveis(Discente discente) throws RegraDeNegocioException {
    // if(discente == null) throw new RegraDeNegocioException("discente inválido");

    //return oportunidadeRepo.findByModalidadeAndStatus(discente.getModalidadeInteresse())

    //}


    /**
     * essa função extrai e retorna uma lista contendo apenas os IDs de uma lista de oportunidade informada
     * @param oportunidades lista de oportunidade a ser mapeada
     * @return lista de inteiros representando os IDs das oportunidades, ou uma lista vazia caso a entrada seja nula
     */
    public List<Integer> listarIndice (List<Oportunidade> oportunidades){
        if(oportunidades == null)  return List.of();
        return oportunidades.stream()
                .map(Oportunidade::getId)
                .collect(Collectors.toList());

    }


    /**
     * Essa função lista todas as oportunidades que estão com o status PUBLICADA no repositório.
     * @return lista de oportunidades publicadas
     */
    public List<Oportunidade> listarPublicadas(){
        return oportunidadeRepo.findByStatus(StatusOportunidade.PUBLICADA);
    }

    /**
     * Essa função lista todas as oportunidades que estão com o status PENDENTE no repositório.
     * @return lista de oportunidades pendentes
     */
    public List<Oportunidade> listarPendentes(){
        return oportunidadeRepo.findByStatus(StatusOportunidade.PENDENTE);
    }

    /**
     * essa função varre as oportunidades publicadas e altera o status para FINALIZADA caso a data de fim seja anterior a data atual
     * @param dataAtual data de referencia para verificar a expiração da oportunidade
     */
    public void verificarOportunidadeExpiradas(LocalDate dataAtual){
        List<Oportunidade> publicadas = listarPublicadas();
        for(Oportunidade op : publicadas){
            if(op.getFim() != null && op.getFim().isBefore(dataAtual)){
                op.setStatus(StatusOportunidade.FINALIZADA);
                oportunidadeRepo.save(op);
            }
        }
    }

    /**
     * eu acho que essa função aqui nao util pra gente ate pq ela vai atualizar so meia noite e aciona a verificação expiradas com base na data do sistema
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void finalizarOportunidade(){
        verificarOportunidadeExpiradas(LocalDate.now());
    }




}
