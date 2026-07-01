package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.AproveitamentoDTO;
import com.exemplo.ufmaextensao.entity.Aproveitamento;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Papel;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.enums.StatusAproveitamento;
import com.exemplo.ufmaextensao.repository.AproveitamentoRepo;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AproveitamentoService - Testes Unitários")
class AproveitamentoServiceTest {

    @Mock
    private AproveitamentoRepo aproveitamentoRepo;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private SecurityService securityService;
    @Mock
    private DiscenteRepo discenteRepo;

    @InjectMocks
    private AproveitamentoService aproveitamentoService;

    private Discente discente;
    private Usuario avaliador;
    private Aproveitamento aproveitamento;

    @BeforeEach
    void setUp() {
        Papel papelCoordenador = new Papel();
        papelCoordenador.setNome("COORDENADOR");

        avaliador = new Usuario();
        avaliador.setId(1);
        avaliador.setNome("Ana Coordenadora");
        avaliador.setPapeis(List.of(papelCoordenador));

        discente = new Discente();
        discente.setId(2);
        discente.setNome("Maria Discente");
        discente.setCh_total_cumprida(10.0f);

        aproveitamento = Aproveitamento.builder()
                .id(100)
                .discente(discente)
                .descricao("Curso de Java")
                .instituicao("UFMA")
                .horas(20)
                .certificadoPath("/certificados/123")
                .status(StatusAproveitamento.PENDENTE)
                .build();
    }

    @Nested
    @DisplayName("solicitar()")
    class Solicitar {

        @Test
        @DisplayName("Deve criar solicitação de aproveitamento com status PENDENTE")
        void deveCriarSolicitacaoComSucesso() throws RegraDeNegocioException {
            AproveitamentoDTO dto = new AproveitamentoDTO();
            dto.setDescricao("Curso de Spring");
            dto.setInstituicao("UFMA");
            dto.setHoras(16);
            dto.setCertificadoPath("/certificados/abc");

            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));
            when(aproveitamentoRepo.save(any(Aproveitamento.class))).thenAnswer(inv -> inv.getArgument(0));

            Aproveitamento resultado = aproveitamentoService.solicitar(dto, 2);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getStatus()).isEqualTo(StatusAproveitamento.PENDENTE);
            assertThat(resultado.getDiscente()).isEqualTo(discente);
            verify(aproveitamentoRepo).save(any(Aproveitamento.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando o discente não existe")
        void deveLancarExcecaoQuandoDiscenteNaoExiste() {
            AproveitamentoDTO dto = new AproveitamentoDTO();
            dto.setDescricao("Curso");
            dto.setInstituicao("UFMA");
            dto.setHoras(10);
            dto.setCertificadoPath("/certificados/1");

            when(discenteRepo.findById(999)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> aproveitamentoService.solicitar(dto, 999))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Discente não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando a descrição for inválida")
        void deveLancarExcecaoQuandoDescricaoForInvalida() {
            AproveitamentoDTO dto = new AproveitamentoDTO();
            dto.setDescricao("   ");
            dto.setInstituicao("UFMA");
            dto.setHoras(10);
            dto.setCertificadoPath("/certificados/1");

            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));

            assertThatThrownBy(() -> aproveitamentoService.solicitar(dto, 2))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Descrição é obrigatória");
        }
    }

    @Nested
    @DisplayName("aprovar()")
    class Aprovar {

        @Test
        @DisplayName("Deve aprovar solicitação e somar horas ao discente")
        void deveAprovarComSucesso() throws RegraDeNegocioException {
            when(usuarioService.obterUsuarioPorId(1)).thenReturn(avaliador);
            doNothing().when(securityService).validarPermissao(avaliador, "COORDENADOR", "ADMIN");
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));
            when(discenteRepo.save(any(Discente.class))).thenAnswer(inv -> inv.getArgument(0));
            when(aproveitamentoRepo.save(any(Aproveitamento.class))).thenAnswer(inv -> inv.getArgument(0));

            Aproveitamento resultado = aproveitamentoService.aprovar(100, 1);

            assertThat(resultado.getStatus()).isEqualTo(StatusAproveitamento.APROVADO);
            assertThat(resultado.getAvaliador()).isEqualTo(avaliador);
            assertThat(discente.getCh_total_cumprida()).isEqualTo(30);
            assertThat(resultado.getDataAvaliacao()).isEqualTo(LocalDate.now());
            verify(discenteRepo).save(discente);
        }

        @Test
        @DisplayName("Deve lançar exceção quando a solicitação já não está pendente")
        void deveLancarExcecaoQuandoStatusNaoEhPendente() {
            aproveitamento.setStatus(StatusAproveitamento.APROVADO);

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(avaliador);
            doNothing().when(securityService).validarPermissao(avaliador, "COORDENADOR", "ADMIN");
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));

            assertThatThrownBy(() -> aproveitamentoService.aprovar(100, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Apenas solicitações pendentes podem ser aprovadas");
        }
    }

    @Nested
    @DisplayName("indeferir()")
    class Indeferir {

        @Test
        @DisplayName("Deve indeferir solicitação com motivo válido")
        void deveIndeferirComSucesso() throws RegraDeNegocioException {
            when(usuarioService.obterUsuarioPorId(1)).thenReturn(avaliador);
            doNothing().when(securityService).validarPermissao(avaliador, "COORDENADOR", "ADMIN");
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));
            when(aproveitamentoRepo.save(any(Aproveitamento.class))).thenAnswer(inv -> inv.getArgument(0));

            Aproveitamento resultado = aproveitamentoService.indeferir(100, 1, "Documento inválido");

            assertThat(resultado.getStatus()).isEqualTo(StatusAproveitamento.INDEFERIDO);
            assertThat(resultado.getMotivoRejeicao()).isEqualTo("Documento inválido");
            assertThat(resultado.getAvaliador()).isEqualTo(avaliador);
        }

        @Test
        @DisplayName("Deve lançar exceção quando o motivo for vazio")
        void deveLancarExcecaoQuandoMotivoEhVazio() {
            when(usuarioService.obterUsuarioPorId(1)).thenReturn(avaliador);
            doNothing().when(securityService).validarPermissao(avaliador, "COORDENADOR", "ADMIN");
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));

            assertThatThrownBy(() -> aproveitamentoService.indeferir(100, 1, "   "))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Motivo do indeferimento é obrigatório");
        }
    }

    @Nested
    @DisplayName("cancelar()")
    class Cancelar {

        @Test
        @DisplayName("Deve cancelar solicitação pendente quando o discente é o dono")
        void deveCancelarSolicitacaoPendenteComSucesso() throws RegraDeNegocioException {
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));
            when(aproveitamentoRepo.save(any(Aproveitamento.class))).thenAnswer(inv -> inv.getArgument(0));

            Aproveitamento resultado = aproveitamentoService.cancelar(100, 2);

            assertThat(resultado.getStatus()).isEqualTo(StatusAproveitamento.CANCELADO);
        }

        @Test
        @DisplayName("Deve lançar exceção quando o discente não é dono da solicitação")
        void deveLancarExcecaoQuandoDiscenteNaoEDono() {
            when(aproveitamentoRepo.findById(100)).thenReturn(Optional.of(aproveitamento));

            assertThatThrownBy(() -> aproveitamentoService.cancelar(100, 999))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Discente não é o dono dessa solicitação");
        }
    }

    @Nested
    @DisplayName("listarPendentes()")
    class ListarPendentes {

        @Test
        @DisplayName("Deve listar apenas solicitações pendentes")
        void deveListarPendentes() {
            when(aproveitamentoRepo.findByStatus(StatusAproveitamento.PENDENTE)).thenReturn(List.of(aproveitamento));

            List<Aproveitamento> resultado = aproveitamentoService.listarPendentes();

            assertThat(resultado).containsExactly(aproveitamento);
        }
    }

    @Nested
    @DisplayName("listarPorDiscente()")
    class ListarPorDiscente {

        @Test
        @DisplayName("Deve listar as solicitações de um discente")
        void deveListarPorDiscente() throws RegraDeNegocioException {
            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));
            when(aproveitamentoRepo.findByDiscente(discente)).thenReturn(List.of(aproveitamento));

            List<Aproveitamento> resultado = aproveitamentoService.listarPorDiscente(2);

            assertThat(resultado).containsExactly(aproveitamento);
        }
    }

    @Nested
    @DisplayName("buscar()")
    class Buscar {

        @Test
        @DisplayName("Deve lançar exceção quando a solicitação não existe")
        void deveLancarExcecaoQuandoNaoExiste() {
            when(aproveitamentoRepo.findById(999)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> aproveitamentoService.buscar(999))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Solicitação não encontrada");
        }
    }
}
