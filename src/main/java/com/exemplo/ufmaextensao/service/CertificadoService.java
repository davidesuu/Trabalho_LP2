package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.CertificadoDTO;
import com.exemplo.ufmaextensao.Enum.StatusAssinatura;
import com.exemplo.ufmaextensao.entity.Certificado;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.repository.CertificadoRepo;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CertificadoService {
    @Autowired
    CertificadoRepo certificadoRepo;
    @Autowired
    DiscenteService discenteService;
    @Autowired
    DocenteService docenteService;
    @Autowired
    OportunidadeRepo oportunidadeRepo;

    /**
     * Essa funcaoo cria um certificado e registra no sistema um novo certificado com status de pendente
     * gerando um hash de autenticidade que ser� ultilizado no qrcode
     * @param id_discente ID do aluno dono do certificado
     * @param id_oportunidade oportunidade que o aluno ganhou o certificado
     * @return O objeto certificado salvo no banco de dados.
     * @throws RegraDeNegocioException Se os dados forem nulos, invalidos ou se o certificado ja possuir id
     */
    public Certificado criarCertificado(Integer id_discente, Integer id_oportunidade)
            throws RegraDeNegocioException {
        Discente discente = discenteService.buscarPorId(id_discente);
        Oportunidade oportunidade = oportunidadeRepo.findById(id_oportunidade)
                .orElseThrow(() -> new RegraDeNegocioException("Oportunidade nao encontrada."));
        Integer horasCertificado = oportunidade.getCarga_horaria();

        if (horasCertificado== null || horasCertificado <= 0) {
            throw new RegraDeNegocioException("A carga hor�ria da oportunidade e maior que zero.");
        }

        Certificado certificado = Certificado.builder()
                .horas(horasCertificado)
                .discente(discente)
                .oportunidade(oportunidade)
                .uuidHash(gerarCodigoCertificado()) // fun�ao nativa do java que fabrica um identificador unico e universal
                .statusAssinatura(StatusAssinatura.PENDENTE)
                .dataEmissao(java.time.LocalDate.now())
                .build();
        return certificadoRepo.save(certificado);


    }

    /**
     * Realiza a assinatura digital de um certificado pendente, alterando seu status para assinado
     * além disso verifica se quem está assinando é o docente que é respondavel pela oportunidade
     * @param certificadoDTO dados do certificado
     * @param id_docente id do docente que vai assinar o certificado
     * @throws RegraDeNegocioException se o certificado não existir, se já estiver assinado ou se o docente não é o respponsavel pela oportunidade
     */
    public void assinarCertificado(CertificadoDTO certificadoDTO, Integer id_docente)
            throws RegraDeNegocioException{
        Docente docente = docenteService.buscarPorId(id_docente);
        Certificado certificado = certificadoRepo.findById(certificadoDTO.getId())
                .orElseThrow(() -> new RegraDeNegocioException("certificado não encontrado"));


        if(certificadoDTO.getId() == null){
            throw new RegraDeNegocioException("id de certificado nao informado");
        }
        if(certificadoDTO.getStatusAssinatura() == StatusAssinatura.ASSINADO){
            throw new RegraDeNegocioException("esse certificado ja foi assinado anteriormente");
        }
        if(!certificado.getOportunidade().getResponsavel_oportunidade().getId().equals(docente.getId())){
            throw new RegraDeNegocioException("apenas o docente pela oportunidade pode assinar o documento");
        }
        certificado.setStatusAssinatura(StatusAssinatura.ASSINADO);
        certificadoRepo.save(certificado);

    }

    /**
     * Essa função lista todos os certificados que estao com status pendentes
     * @param certificadoDTO dados do certificado
     * @return Uma lista de certificado com status PENDENTES
     */
    public List<Certificado> listarCerticadosPendentes(CertificadoDTO certificadoDTO){
        return certificadoRepo.findByStatus(StatusAssinatura.PENDENTE);
    }

    /**
     * Essa função lista todos os certificados que estao com status assinados
     * @param certificadoDTO dados do certificado
     * @return Uma lista de certificado com status ASSINADOS
     */
    public List<Certificado> listarCerticadosAssinados(CertificadoDTO certificadoDTO){
        return certificadoRepo.findByStatus(StatusAssinatura.ASSINADO);
    }

    /**
     * Essa função gera um codigo de validação curto usando o ano do servidor
     * o formato é: CERT-ANO-ALEATORIO (tipo: CER-2026-X9U7)
     * @return o codigo formatado
     */
    private String gerarCodigoCertificado(){
        int anoAtual = java.time.LocalDate.now().getYear();
        String CodigoAleatorio = java.util.UUID.randomUUID().toString()
                .substring(0,4)
                .toUpperCase();
        return "CERT-" +anoAtual + "-" + CodigoAleatorio;
    }

    /**
     * Valida a autencidade de um certificado através do seu codigo curto( pode ser tanto o qrcode ou
     * codigo digitado)
     * @param codigoValidacao o codigo impresso no certificado
     * @return o objeto certificado completo
     * @throws RegraDeNegocioException se o codigo nao existir ou se o certificado não estiver assinado
     */
    public Certificado validarCertificado(String codigoValidacao)
            throws RegraDeNegocioException{
        if(codigoValidacao == null || codigoValidacao.isBlank()){
            throw new RegraDeNegocioException("o codigo de validação não foi informado");
        }
        Certificado certificado = certificadoRepo.findByUuidHash(codigoValidacao.toUpperCase())
                .orElseThrow(() -> new RegraDeNegocioException("certificado invalido, este documento não existe no sistema"));

        if(certificado.getStatusAssinatura() != StatusAssinatura.ASSINADO){
            throw new RegraDeNegocioException("este certificado existe, mas não foi assinado pelo docente responsavel");
        }
        return certificado;
    }








}

