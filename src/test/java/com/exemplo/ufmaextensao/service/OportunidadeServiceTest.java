package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.OportunidadeDTO;
import com.exemplo.ufmaextensao.entity.Grupo;
import com.exemplo.ufmaextensao.entity.Oportunidade;
import com.exemplo.ufmaextensao.entity.TipoOportunidade;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.enums.Modalidade;
import com.exemplo.ufmaextensao.enums.StatusOportunidade;
import com.exemplo.ufmaextensao.repository.InscricaoRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OportunidadeService - Testes Unitários")
class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepo oportunidadeRepo;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private SecurityService securityService;
    @Mock
    private CertificadoService certificadoService;
    @Mock
    private TipoOportunidadeService tipoOportunidadeService;
    @Mock
    private GrupoService grupoService;
    @Mock
    private InscricaoRepo inscricaoRepo;

    @InjectMocks
    private OportunidadeService oportunidadeService;

    private Usuario usuario;
    private TipoOportunidade tipo;
    private Oportunidade oportunidade;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);

        tipo = new TipoOportunidade();
        tipo.setTipo("CURSO");

        oportunidade = Oportunidade.builder()
                .id(10)
                .titulo("Workshop")
                .descricao("Descrição")
                .tipoOportunidade(tipo)
                .modalidade(Modalidade.PRESENCIAL)
                .carga_horaria(40)
                .vagas(10)
                .vagasOcupadas(0)
                .inicio(LocalDate.now())
                .fim(LocalDate.now().plusDays(5))
                .status(StatusOportunidade.PENDENTE)
                .build();
    }

    @Nested
    @DisplayName("criarOportunidade()")
    class CriarOportunidade {
        @Test
        @DisplayName("Deve criar oportunidade com sucesso")
        void deveCriarOportunidadeComSucesso() throws RegraDeNegocioException {
            OportunidadeDTO dto = new OportunidadeDTO();
            dto.setNome("Workshop");
            dto.setDescricao("Descrição");
            dto.setModalidade(Modalidade.PRESENCIAL);
            dto.setCarga_horaria(40);
            dto.setVagas(10);
            dto.setInicio(LocalDate.now());
            dto.setFim(LocalDate.now().plusDays(5));

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(tipoOportunidadeService.buscarPorTipo("CURSO")).thenReturn(tipo);
            when(oportunidadeRepo.save(any(Oportunidade.class))).thenAnswer(inv -> inv.getArgument(0));

            Oportunidade resultado = oportunidadeService.criarOportunidade(dto, 1, "CURSO", null);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getTitulo()).isEqualTo("Workshop");
            assertThat(resultado.getStatus()).isEqualTo(StatusOportunidade.PUBLICADA);
        }
    }

    @Nested
    @DisplayName("publicarOportunidade()")
    class PublicarOportunidade {
        @Test
        @DisplayName("Deve publicar oportunidade pendente")
        void devePublicarOportunidadeComSucesso() throws RegraDeNegocioException {
            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(oportunidadeRepo.findById(10)).thenReturn(Optional.of(oportunidade));
            when(oportunidadeRepo.save(any(Oportunidade.class))).thenAnswer(inv -> inv.getArgument(0));

            Oportunidade resultado = oportunidadeService.publicarOportunidade(10, 1);

            assertThat(resultado.getStatus()).isEqualTo(StatusOportunidade.PUBLICADA);
        }
    }

    @Nested
    @DisplayName("rejeitarOportunidade()")
    class RejeitarOportunidade {
        @Test
        @DisplayName("Deve rejeitar oportunidade pendente")
        void deveRejeitarOportunidadeComSucesso() throws RegraDeNegocioException {
            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(oportunidadeRepo.findById(10)).thenReturn(Optional.of(oportunidade));
            when(oportunidadeRepo.save(any(Oportunidade.class))).thenAnswer(inv -> inv.getArgument(0));

            Oportunidade resultado = oportunidadeService.rejeitarOportunidade(10, 1);

            assertThat(resultado.getStatus()).isEqualTo(StatusOportunidade.REJEITADA);
        }
    }

    @Nested
    @DisplayName("listarIndice()")
    class ListarIndice {
        @Test
        @DisplayName("Deve retornar apenas os ids das oportunidades")
        void deveListarIndiceComSucesso() {
            List<Oportunidade> oportunidades = List.of(Oportunidade.builder().id(1).build(),
                    Oportunidade.builder().id(2).build());

            assertThat(oportunidadeService.listarIndice(oportunidades)).containsExactly(1, 2);
        }
    }
}
