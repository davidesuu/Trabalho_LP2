package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.OportunidadeDTO;
import com.exemplo.ufmaextensao.Enum.StatusOportunidade;
import com.exemplo.ufmaextensao.entity.*;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import jakarta.transaction.Transactional;
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
    private CertificadoService certificadoService;
    @Autowired
    private TipoOportunidadeService tipoOportunidadeService;
    @Autowired
    private GrupoService grupoService;

    /**
     * Essa função salva uma oportunidade no repositorio após validar as informações da oportunidade instanciada e validar permisão do usuario
     * @param oportunidadeDTO instancia de oportunidade a ser adicionado ao repositorio
     * @param usuarioId ID do usuario que esta tentando criar a oportunidade
     * @return retorna uma Oportunidade após salvar no repositorio
     * @throws RegraDeNegocioException se as validacoes de dados ou de permissao falharem
     */

    public Oportunidade criarOportunidade(OportunidadeDTO oportunidadeDTO, Integer usuarioId, String tipo, Integer idGrupo)
            throws RegraDeNegocioException {

        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);
        securityService.validarPermissao(usuario, "DOCENTE", "COORDENADOR", "ADMIN", "D_DIRETOR");

        if (oportunidadeDTO == null) {
            throw new RegraDeNegocioException("Os dados da oportunidade não foram informados.");
        }
        if (oportunidadeDTO.getNome() == null || oportunidadeDTO.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser vazio.");
        }
        if (oportunidadeDTO.getDescricao() == null || oportunidadeDTO.getDescricao().isBlank()) {
            throw new RegraDeNegocioException("A descrição não pode ser vazia.");
        }
        if (oportunidadeDTO.getModalidade() == null) {
            throw new RegraDeNegocioException("A modalidade deve ser informada.");
        }
        if (oportunidadeDTO.getCarga_horaria() == null || oportunidadeDTO.getCarga_horaria() <= 0) {
            throw new RegraDeNegocioException("A carga horária deve ser maior que zero.");
        }
        if (oportunidadeDTO.getVagas() == null || oportunidadeDTO.getVagas() <= 0) {
            throw new RegraDeNegocioException("A quantidade de vagas deve ser maior que zero.");
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

        // corrigido: tipo.toUpperCase() não modifica a variável original
        TipoOportunidade tipoOportunidade = tipoOportunidadeService.buscarPorTipo(tipo.toUpperCase());
        if (tipoOportunidade == null) {
            throw new RegraDeNegocioException("Tipo de oportunidade inválido.");
        }

        boolean isDiretor = usuario.getPapeis().stream()
                .anyMatch(p -> p.getNome().equals("D_DIRETOR"));

        Grupo grupo = null;
        if (isDiretor) {
            if (idGrupo == null) {
                throw new RegraDeNegocioException("Discente diretor precisa informar o grupo.");
            }
            grupo = grupoService.buscarPorId(idGrupo);

            if (!grupo.getDiretoria().contains(usuario)) {
                throw new RegraDeNegocioException("Discente não é diretor desse grupo.");
            }
        }

        Oportunidade oportunidade = Oportunidade.builder()
                .titulo(oportunidadeDTO.getNome())
                .descricao(oportunidadeDTO.getDescricao())
                .tipoOportunidade(tipoOportunidade)
                .modalidade(oportunidadeDTO.getModalidade())
                .carga_horaria(oportunidadeDTO.getCarga_horaria())
                .vagas(oportunidadeDTO.getVagas())
                .incio(oportunidadeDTO.getIncio())
                .fim(oportunidadeDTO.getFim())
                .grupo(grupo)
                .autor(usuario)
                .build();

        oportunidade.setVagasOcupadas(0);

        if (isDiretor){
            oportunidade.setStatus(StatusOportunidade.PENDENTE);
        }
        else {
            oportunidade.setResponsavel_oportunidade(usuario);
            oportunidade.setStatus(StatusOportunidade.PUBLICADA);
        }
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
     * @param oportunidadeId ID da oportunidade que vai ser publicada
     * @param responsavelId id do docente que se tornara responsavel pela oportunidade publicada
     * @throws RegraDeNegocioException se o docente for nulo ou se a oportunidade ja estiver publicada
     */
    public Oportunidade publicarOportunidade(Integer oportunidadeId, Integer responsavelId) throws RegraDeNegocioException {
        Usuario usuario = usuarioService.obterUsuarioPorId(responsavelId);
        securityService.validarPermissao(usuario, "DOCENTE", "COORDENADOR", "ADMIN");

        Oportunidade op = buscar(oportunidadeId);

        if (op.getStatus() != StatusOportunidade.PENDENTE) {
            throw new RegraDeNegocioException("Apenas oportunidades aguardando aprovação podem ser publicadas.");
        }


        if (op.getGrupo() != null && !op.getGrupo().getResponsavel().getId().equals(responsavelId)) {
            throw new RegraDeNegocioException("Apenas o docente responsável pelo grupo pode aprovar essa oportunidade.");
        }

        op.setStatus(StatusOportunidade.PUBLICADA);
        op.setResponsavel_oportunidade(usuario);
        return oportunidadeRepo.save(op);
    }

    /**
     * Essa funcao altera o status da oportunidade para REJEITADA caso ela esteja pendente
     * @param oportunidadeId ID da oportunidade a ser rejeitada
     * @param responsavelId instancia do responsavel que esta executando a ação de rejeição
     * @throws RegraDeNegocioException se a oportunidade nao estiver com status PEBDENTE
     */
    public Oportunidade rejeitarOportunidade(Integer oportunidadeId, Integer responsavelId) throws RegraDeNegocioException{
        Oportunidade op = buscar(oportunidadeId);
        Usuario usuario = usuarioService.obterUsuarioPorId(responsavelId);
        securityService.validarPermissao(usuario, "DOCENTE", "COORDENADOR", "ADMIN");
        if (op.getStatus() != StatusOportunidade.PENDENTE) {
            throw new RegraDeNegocioException("Apenas oportunidades aguardando aprovação podem ser rejeitadas.");
        }


        if (op.getGrupo() != null && !op.getGrupo().getResponsavel().getId().equals(responsavelId)) {
            throw new RegraDeNegocioException("Apenas o docente responsável pelo grupo pode rejeitar essa oportunidade.");
        }
        op.setStatus(StatusOportunidade.REJEITADA);
        op.setResponsavel_oportunidade(usuario);
        return oportunidadeRepo.save(op);
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
     * Essa funcao finaliza oportunidade e gera um certificado para todos os discentes que estao nessa oportunidade
     * @param idOportunidade id da oportunidade que vai ser finalizada
     * @throws RegraDeNegocioException se essa oportunidade nao existir ou se nao tiver nenhum discente cadastrado na oportunidadde
     */
    @Transactional
    // essa anota��o � usada quando um metodo faz mais de uma opera��o, se der erro, o sistema volta pro inicio, se der tudo certo, ele salva no banco de dados
    public void finalizarOportunidade(Integer idOportunidade)
            throws RegraDeNegocioException{
        Oportunidade oportunidade = oportunidadeRepo.findById(idOportunidade)
                .orElseThrow(() -> new RegraDeNegocioException("oportunidade n�o encontrada."));

        Grupo grupo = oportunidade.getGrupo();
        if(grupo == null || grupo.getDiscentes() == null || grupo.getDiscentes().isEmpty()){
            throw new RegraDeNegocioException("o grupo dessa oportunidade nao tem discentes vinculados");
        }
        oportunidade.setStatus(StatusOportunidade.FINALIZADA);
        oportunidadeRepo.save(oportunidade);

        for(Discente discente : grupo.getDiscentes()){
            certificadoService.criarCertificado(discente.getId(), idOportunidade);
        }


    }





}
