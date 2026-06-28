package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.DTO.CertificadoDTO;
import com.exemplo.ufmaextensao.Enum.StatusAssinatura;
import com.exemplo.ufmaextensao.entity.Certificado;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.repository.CertificadoRepo;
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
    OportunidadeService oportunidadeService;


    public Certificado criarCertificado(Integer id_discente, Integer id_docente, CertificadoDTO certificadoDTO, Integer id_oportunidade)
            throws RegraDeNegocioException {
        Discente discente = discenteService.buscarPorId(id_discente);
        Docente docente = docenteService.buscarPorId(id_docente);
        Oportunidade oportunidade = oportunidadeService.buscar(id_oportunidade);

        if (certificadoDTO == null) {
            throw new RegraDeNegocioException("Os dados do certificado não foram informados.");
        }
        if (certificadoDTO.getId() != null) {
            throw new RegraDeNegocioException("Um novo certificado não deve possuir ID já definido.");
        }

        if (certificadoDTO.getHoras() == null || certificadoDTO.getHoras() <= 0) {
            throw new RegraDeNegocioException("A carga horária do certificado deve ser informada e maior que zero.");
        }

        Certificado certificado = Certificado.builder()
                .horas(certificadoDTO.getHoras())
                .discente(discente)
                .oportunidade(oportunidade)
                .uuid_hash(java.util.UUID.randomUUID().toString()) // funçao nativa do java que fabrica um identificador unico e universal
                .statusAssinatura(StatusAssinatura.PENDENTE)
                .dataEmissao(java.time.LocalDate.now())
                .build();
        return certificadoRepo.save(certificado);


    }

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

    public List<Certificado> listarCerticadosPendentes(CertificadoDTO certificadoDTO){
        return certificadoRepo.findByStatus(StatusAssinatura.PENDENTE);
    }

    public List<Certificado> listarCerticadosAssinados(CertificadoDTO certificadoDTO){
        return certificadoRepo.findByStatus(StatusAssinatura.ASSINADO);
    }



}

