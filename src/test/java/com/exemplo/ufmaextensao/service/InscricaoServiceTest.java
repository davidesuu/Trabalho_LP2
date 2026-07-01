package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.InscricaoDTO;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Inscricao;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.enums.StatusInscricao;
import com.exemplo.ufmaextensao.enums.StatusOportunidade;
import com.exemplo.ufmaextensao.repository.DiscenteRepo;
import com.exemplo.ufmaextensao.repository.InscricaoRepo;
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
@DisplayName("InscricaoService - Testes Unitários")
class InscricaoServiceTest {

    @Mock private InscricaoRepo inscricaoRepo;
    @Mock private OportunidadeService oportunidadeService;
    @Mock private DiscenteRepo discenteRepo;
    @Mock private DocenteService docenteService;

    @InjectMocks
    private InscricaoService inscricaoService;

    private Docente docente;
    private Discente discente;
    private Oportunidade oportunidade;
    private Inscricao inscricao;

    @BeforeEach
    void setUp() {
        Usuario usuarioDocente = new Usuario();
        usuarioDocente.setId(1);

        docente = Docente.builder()
                .siape("1234567")
                .departamento("DEINF")
                .build();
        docente.setId(1);
        docente.setNome("Prof. Silva");

        discente = new Discente();
        discente.setId(2);
        discente.setNome("João Discente");

        oportunidade = Oportunidade.builder()
                .titulo("Workshop Spring Boot")
                .vagas(5)
                .vagasOcupadas(0)
                .inicio(LocalDate.now().minusDays(5))
                .fim(LocalDate.now().plusDays(30))   // oportunidade ainda aberta
                .status(StatusOportunidade.PUBLICADA)
                .responsavel_oportunidade(docente)
                .build();
        oportunidade.setId(10);

        inscricao = Inscricao.builder()
                .id(1)
                .oportunidade(oportunidade)
                .discente(discente)
                .status(StatusInscricao.PENDENTE)
                .motivacao("Quero aprender mais sobre Spring Boot")
                .build();
    }

    // =========================================================
    // criarInscricao
    // =========================================================
    @Nested
    @DisplayName("criarInscricao()")
    class CriarInscricao {

        @Test
        @DisplayName("Deve criar inscrição com status PENDENTE quando dados são válidos")
        void deveCriarInscricaoComSucesso() throws RegraDeNegocioException {
            InscricaoDTO dto = new InscricaoDTO();
            dto.setMotivacao("Interesse no tema");

            when(oportunidadeService.buscar(10)).thenReturn(oportunidade);
            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));
            when(inscricaoRepo.existsByOportunidadeAndDiscente(oportunidade, discente)).thenReturn(false);
            when(inscricaoRepo.save(any(Inscricao.class))).thenAnswer(inv -> inv.getArgument(0));

            Inscricao resultado = inscricaoService.criarInscricao(dto, 10, 2);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getStatus()).isEqualTo(StatusInscricao.PENDENTE);
            assertThat(resultado.getMotivacao()).isEqualTo("Interesse no tema");
            verify(inscricaoRepo).save(any(Inscricao.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando oportunidade já encerrou")
        void deveLancarExcecaoQuandoOportunidadeEncerrada() throws RegraDeNegocioException {
            oportunidade.setFim(LocalDate.now().minusDays(1)); // encerrada ontem
            when(oportunidadeService.buscar(10)).thenReturn(oportunidade);

            InscricaoDTO dto = new InscricaoDTO();
            assertThatThrownBy(() -> inscricaoService.criarInscricao(dto, 10, 2))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("após o fim");
        }

        @Test
        @DisplayName("Deve lançar exceção quando discente não é encontrado")
        void deveLancarExcecaoQuandoDiscenteNaoExiste() throws RegraDeNegocioException {
            when(oportunidadeService.buscar(10)).thenReturn(oportunidade);
            when(discenteRepo.findById(99)).thenReturn(Optional.empty());

            InscricaoDTO dto = new InscricaoDTO();
            assertThatThrownBy(() -> inscricaoService.criarInscricao(dto, 10, 99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Discente não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando discente já está inscrito na oportunidade")
        void deveLancarExcecaoQuandoJaInscrito() throws RegraDeNegocioException {
            when(oportunidadeService.buscar(10)).thenReturn(oportunidade);
            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));
            when(inscricaoRepo.existsByOportunidadeAndDiscente(oportunidade, discente)).thenReturn(true);

            InscricaoDTO dto = new InscricaoDTO();
            assertThatThrownBy(() -> inscricaoService.criarInscricao(dto, 10, 2))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("já possui inscrição");
        }
    }

    // =========================================================
    // aprovar
    // =========================================================
    @Nested
    @DisplayName("aprovar()")
    class Aprovar {

        @Test
        @DisplayName("Deve aprovar inscrição e incrementar vagas ocupadas")
        void deveAprovarComSucesso() throws RegraDeNegocioException {
            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(inscricaoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

            Inscricao resultado = inscricaoService.aprovar(1, 1);

            assertThat(resultado.getStatus()).isEqualTo(StatusInscricao.APROVADA);
            assertThat(oportunidade.getVagasOcupadas()).isEqualTo(1);
            verify(oportunidadeService).salvarOportunidade(oportunidade);
        }

        @Test
        @DisplayName("Deve lançar exceção quando docente não é responsável pela oportunidade")
        void deveLancarExcecaoQuandoDocenteNaoEResponsavel() throws RegraDeNegocioException {
            Docente outroDocente = Docente.builder().siape("9999999").build();
            outroDocente.setId(99);

            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(docenteService.buscarPorId(99)).thenReturn(outroDocente);

            assertThatThrownBy(() -> inscricaoService.aprovar(1, 99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não é responsavel");
        }

        @Test
        @DisplayName("Deve lançar exceção quando não há vagas disponíveis")
        void deveLancarExcecaoQuandoSemVagas() throws RegraDeNegocioException {
            oportunidade.setVagas(5);
            oportunidade.setVagasOcupadas(5); // lotada

            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(docenteService.buscarPorId(1)).thenReturn(docente);

            assertThatThrownBy(() -> inscricaoService.aprovar(1, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não tem vagas");
        }
    }

    // =========================================================
    // rejeitar
    // =========================================================
    @Nested
    @DisplayName("rejeitar()")
    class Rejeitar {

        @Test
        @DisplayName("Deve rejeitar inscrição com sucesso")
        void deveRejeitarComSucesso() throws RegraDeNegocioException {
            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(inscricaoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

            Inscricao resultado = inscricaoService.rejeitar(1, 1);

            assertThat(resultado.getStatus()).isEqualTo(StatusInscricao.REJEITADA);
            verify(inscricaoRepo).save(inscricao);
        }

        @Test
        @DisplayName("Deve lançar exceção quando docente não é responsável")
        void deveLancarExcecaoQuandoDocenteNaoEResponsavel() throws RegraDeNegocioException {
            Docente outro = Docente.builder().siape("0000001").build();
            outro.setId(50);

            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(docenteService.buscarPorId(50)).thenReturn(outro);

            assertThatThrownBy(() -> inscricaoService.rejeitar(1, 50))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("não é responsavel");
        }
    }

    // =========================================================
    // cancelarInscricao
    // =========================================================
    @Nested
    @DisplayName("cancelarInscricao()")
    class CancelarInscricao {

        @Test
        @DisplayName("Deve cancelar inscrição PENDENTE sem alterar vagas ocupadas")
        void deveCancelarInscricaoPendenteSeAlterarVagas() throws RegraDeNegocioException {
            inscricao.setStatus(StatusInscricao.PENDENTE);
            oportunidade.setVagasOcupadas(2);

            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(inscricaoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

            inscricaoService.cancelarInscricao(1);

            assertThat(inscricao.getStatus()).isEqualTo(StatusInscricao.CANCELADA);
            assertThat(oportunidade.getVagasOcupadas()).isEqualTo(2); // não mudou
        }

        @Test
        @DisplayName("Deve cancelar inscrição APROVADA e decrementar vagas ocupadas")
        void deveCancelarInscricaoAprovadaEDecrementarVagas() throws RegraDeNegocioException {
            inscricao.setStatus(StatusInscricao.APROVADA);
            oportunidade.setVagasOcupadas(3);

            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));
            when(inscricaoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

            inscricaoService.cancelarInscricao(1);

            assertThat(inscricao.getStatus()).isEqualTo(StatusInscricao.CANCELADA);
            assertThat(oportunidade.getVagasOcupadas()).isEqualTo(2); // decrementou
        }

        @Test
        @DisplayName("Deve lançar exceção quando oportunidade já encerrou")
        void deveLancarExcecaoQuandoOportunidadeEncerrada() throws RegraDeNegocioException {
            oportunidade.setFim(LocalDate.now().minusDays(1));
            when(inscricaoRepo.findById(1)).thenReturn(Optional.of(inscricao));

            assertThatThrownBy(() -> inscricaoService.cancelarInscricao(1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("após o fim");
        }
    }

    // =========================================================
    // listarAprovadasEPendentesPorDiscente
    // =========================================================
    @Nested
    @DisplayName("listarAprovadasEPendentesPorDiscente()")
    class ListarAprovadasEPendentes {

        @Test
        @DisplayName("Deve retornar apenas inscrições APROVADAS e PENDENTES")
        void deveRetornarApenasAprovadasEPendentes() throws RegraDeNegocioException {
            Inscricao pendente = Inscricao.builder().id(1).status(StatusInscricao.PENDENTE).build();
            Inscricao aprovada = Inscricao.builder().id(2).status(StatusInscricao.APROVADA).build();
            Inscricao rejeitada = Inscricao.builder().id(3).status(StatusInscricao.REJEITADA).build();
            Inscricao cancelada = Inscricao.builder().id(4).status(StatusInscricao.CANCELADA).build();

            when(inscricaoRepo.findByDiscenteId(2)).thenReturn(List.of(pendente, aprovada, rejeitada, cancelada));

            List<Inscricao> resultado = inscricaoService.listarAprovadasEPendentesPorDiscente(2);

            assertThat(resultado).hasSize(2);
            assertThat(resultado).extracting(Inscricao::getStatus)
                    .containsExactlyInAnyOrder(StatusInscricao.PENDENTE, StatusInscricao.APROVADA);
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há inscrições ativas")
        void deveRetornarListaVaziaQuandoNaoHaAtivas() throws RegraDeNegocioException {
            Inscricao rejeitada = Inscricao.builder().id(1).status(StatusInscricao.REJEITADA).build();
            when(inscricaoRepo.findByDiscenteId(2)).thenReturn(List.of(rejeitada));

            List<Inscricao> resultado = inscricaoService.listarAprovadasEPendentesPorDiscente(2);

            assertThat(resultado).isEmpty();
        }
    }
}
