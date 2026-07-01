package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.CertificadoDTO;
import com.exemplo.ufmaextensao.entity.Certificado;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.enums.StatusAssinatura;
import com.exemplo.ufmaextensao.repository.CertificadoRepo;
import com.exemplo.ufmaextensao.repository.OportunidadeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CertificadoService - Testes Unitários")
class CertificadoServiceTest {

    @Mock private CertificadoRepo certificadoRepo;
    @Mock private OportunidadeRepo oportunidadeRepo;
    @Mock private DiscenteService discenteService;
    @Mock private DocenteService docenteService;

    @InjectMocks
    private CertificadoService certificadoService;

    private Discente discente;
    private Docente docente;
    private Oportunidade oportunidade;
    private Certificado certificado;

    @BeforeEach
    void setUp() {
        // Docente responsável pela oportunidade
        docente = Docente.builder()
                .siape("1234567")
                .departamento("DEINF")
                .build();
        docente.setId(1);
        docente.setNome("Prof. João");

        // Discente que receberá o certificado
        discente = new Discente();
        discente.setId(2);
        discente.setNome("Maria Discente");

        // Oportunidade que gerou o certificado
        oportunidade = Oportunidade.builder()
                .titulo("Workshop de Java")
                .carga_horaria(40)
                .responsavel_oportunidade(docente)
                .build();
        oportunidade.setId(10);

        // Certificado base para reutilização
        certificado = Certificado.builder()
                .id(100)
                .uuidHash("CERT-2026-ABCD")
                .discente(discente)
                .oportunidade(oportunidade)
                .horas(40)
                .statusAssinatura(StatusAssinatura.PENDENTE)
                .dataEmissao(LocalDate.now())
                .build();
    }

    // =========================================================
    // criarCertificado
    // =========================================================
    @Nested
    @DisplayName("criarCertificado()")
    class CriarCertificado {

        @Test
        @DisplayName("Deve criar certificado com status PENDENTE quando dados são válidos")
        void deveCriarCertificadoComSucesso() throws RegraDeNegocioException {
            when(oportunidadeRepo.findById(10)).thenReturn(Optional.of(oportunidade));
            when(certificadoRepo.save(any(Certificado.class))).thenAnswer(inv -> inv.getArgument(0));

            Certificado resultado = certificadoService.criarCertificado(discente, 10);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getStatusAssinatura()).isEqualTo(StatusAssinatura.PENDENTE);
            assertThat(resultado.getHoras()).isEqualTo(40);
            assertThat(resultado.getDiscente()).isEqualTo(discente);
            assertThat(resultado.getUuidHash()).matches("CERT-\\d{4}-[A-Z0-9]{4}");
            verify(certificadoRepo).save(any(Certificado.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando oportunidade não for encontrada")
        void deveLancarExcecaoQuandoOportunidadeNaoExiste() {
            when(oportunidadeRepo.findById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> certificadoService.criarCertificado(discente, 99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Oportunidade nao encontrada");
        }

        @Test
        @DisplayName("Deve lançar exceção quando carga horária da oportunidade é nula")
        void deveLancarExcecaoQuandoCargaHorariaNula() {
            oportunidade.setCarga_horaria(null);
            when(oportunidadeRepo.findById(10)).thenReturn(Optional.of(oportunidade));

            assertThatThrownBy(() -> certificadoService.criarCertificado(discente, 10))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("carga horária");
        }

        @Test
        @DisplayName("Deve lançar exceção quando carga horária da oportunidade é zero ou negativa")
        void deveLancarExcecaoQuandoCargaHorariaZeroOuNegativa() {
            oportunidade.setCarga_horaria(0);
            when(oportunidadeRepo.findById(10)).thenReturn(Optional.of(oportunidade));

            assertThatThrownBy(() -> certificadoService.criarCertificado(discente, 10))
                    .isInstanceOf(RegraDeNegocioException.class);
        }
    }

    // =========================================================
    // assinarCertificado
    // =========================================================
    @Nested
    @DisplayName("assinarCertificado()")
    class AssinarCertificado {

        @Test
        @DisplayName("Deve assinar certificado com sucesso quando docente é responsável")
        void deveAssinarComSucesso() throws RegraDeNegocioException {
            CertificadoDTO dto = new CertificadoDTO();
            dto.setId(100);
            dto.setStatusAssinatura(StatusAssinatura.PENDENTE);

            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(certificadoRepo.findById(100)).thenReturn(Optional.of(certificado));
            when(certificadoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

            certificadoService.assinarCertificado(dto, 1);

            verify(certificadoRepo).save(argThat(c -> c.getStatusAssinatura() == StatusAssinatura.ASSINADO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando certificado não for encontrado")
        void deveLancarExcecaoQuandoCertificadoNaoExiste() {
            CertificadoDTO dto = new CertificadoDTO();
            dto.setId(999);
            dto.setStatusAssinatura(StatusAssinatura.PENDENTE);

            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(certificadoRepo.findById(999)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> certificadoService.assinarCertificado(dto, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("certificado não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando certificado já está assinado")
        void deveLancarExcecaoQuandoJaAssinado() {
            CertificadoDTO dto = new CertificadoDTO();
            dto.setId(100);
            dto.setStatusAssinatura(StatusAssinatura.ASSINADO); // já assinado

            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(certificadoRepo.findById(100)).thenReturn(Optional.of(certificado));

            assertThatThrownBy(() -> certificadoService.assinarCertificado(dto, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("assinado anteriormente");
        }

        @Test
        @DisplayName("Deve lançar exceção quando docente não é o responsável pela oportunidade")
        void deveLancarExcecaoQuandoDocenteNaoEResponsavel() {
            Docente outroDocente = Docente.builder().siape("9999999").build();
            outroDocente.setId(99); // id diferente do responsável

            CertificadoDTO dto = new CertificadoDTO();
            dto.setId(100);
            dto.setStatusAssinatura(StatusAssinatura.PENDENTE);

            when(docenteService.buscarPorId(99)).thenReturn(outroDocente);
            when(certificadoRepo.findById(100)).thenReturn(Optional.of(certificado));

            assertThatThrownBy(() -> certificadoService.assinarCertificado(dto, 99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("apenas o docente");
        }
    }

    // =========================================================
    // validarCertificado
    // =========================================================
    @Nested
    @DisplayName("validarCertificado()")
    class ValidarCertificado {

        @Test
        @DisplayName("Deve retornar certificado quando código é válido e está assinado")
        void deveValidarComSucesso() throws RegraDeNegocioException {
            certificado.setStatusAssinatura(StatusAssinatura.ASSINADO);
            when(certificadoRepo.findByUuidHash("CERT-2026-ABCD")).thenReturn(Optional.of(certificado));

            Certificado resultado = certificadoService.validarCertificado("cert-2026-abcd"); // deve converter pra uppercase

            assertThat(resultado).isEqualTo(certificado);
        }

        @Test
        @DisplayName("Deve lançar exceção quando código de validação é nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            assertThatThrownBy(() -> certificadoService.validarCertificado(null))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não foi informado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando código de validação é vazio")
        void deveLancarExcecaoQuandoCodigoVazio() {
            assertThatThrownBy(() -> certificadoService.validarCertificado("  "))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não foi informado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando certificado não existe no sistema")
        void deveLancarExcecaoQuandoCertificadoNaoExiste() {
            when(certificadoRepo.findByUuidHash("CERT-0000-XXXX")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> certificadoService.validarCertificado("CERT-0000-XXXX"))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não existe no sistema");
        }

        @Test
        @DisplayName("Deve lançar exceção quando certificado existe mas não está assinado")
        void deveLancarExcecaoQuandoCertificadoPendente() {
            certificado.setStatusAssinatura(StatusAssinatura.PENDENTE);
            when(certificadoRepo.findByUuidHash("CERT-2026-ABCD")).thenReturn(Optional.of(certificado));

            assertThatThrownBy(() -> certificadoService.validarCertificado("CERT-2026-ABCD"))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não foi assinado");
        }
    }

    // =========================================================
    // listar certificados
    // =========================================================
    @Nested
    @DisplayName("listarCertificados()")
    class ListarCertificados {

        @Test
        @DisplayName("Deve retornar lista de certificados pendentes")
        void deveListarPendentes() {
            when(certificadoRepo.findByStatusAssinatura(StatusAssinatura.PENDENTE))
                    .thenReturn(List.of(certificado));

            List<Certificado> resultado = certificadoService.listarCerticadosPendentes(null);

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getStatusAssinatura()).isEqualTo(StatusAssinatura.PENDENTE);
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há certificados pendentes")
        void deveRetornarListaVaziaQuandoNaoHaPendentes() {
            when(certificadoRepo.findByStatusAssinatura(StatusAssinatura.PENDENTE))
                    .thenReturn(List.of());

            List<Certificado> resultado = certificadoService.listarCerticadosPendentes(null);

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar lista de certificados assinados")
        void deveListarAssinados() {
            certificado.setStatusAssinatura(StatusAssinatura.ASSINADO);
            when(certificadoRepo.findByStatusAssinatura(StatusAssinatura.ASSINADO))
                    .thenReturn(List.of(certificado));

            List<Certificado> resultado = certificadoService.listarCerticadosAssinados(null);

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getStatusAssinatura()).isEqualTo(StatusAssinatura.ASSINADO);
        }
    }
}
