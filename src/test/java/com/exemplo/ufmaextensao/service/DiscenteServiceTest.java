package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.DiscenteDTO;
import com.exemplo.ufmaextensao.dto.HorasDiscenteDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.PPC;
import com.exemplo.ufmaextensao.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DiscenteService - Testes Unitários")
class DiscenteServiceTest {

    @Mock
    private com.exemplo.ufmaextensao.repository.DiscenteRepo discenteRepo;
    @Mock
    private SecurityService securityService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private CursoService cursoService;
    @Mock
    private PPCService ppcService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private DiscenteService discenteService;

    private Usuario usuario;
    private Curso curso;
    private PPC ppc;
    private Discente discente;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);

        curso = Curso.builder().id(10).nome("ADS").codigo(101).build();

        ppc = PPC.builder().id(1).curso(curso).anoVigencia(2024).cargaHorariaTotal(240F).build();

        discente = Discente.builder()
                .id(2)
                .nome("Maria")
                .email("maria@ufma.br")
                .semestre(5)
                .build();
    }

    @Nested
    @DisplayName("criarDiscente()")
    class CriarDiscente {
        @Test
        @DisplayName("Deve criar discente com sucesso")
        void deveCriarDiscenteComSucesso() throws RegraDeNegocioException {
            DiscenteDTO dto = new DiscenteDTO();
            dto.setNome("Maria");
            dto.setEmail("maria@ufma.br");
            dto.setSenha("123456");
            dto.setSemestre(5);

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(cursoService.buscarCursoPorId(10)).thenReturn(curso);
            when(ppcService.buscarPPCMaisRecente(10)).thenReturn(ppc);
            when(passwordEncoder.encode("123456")).thenReturn("hashSenha");
            when(discenteRepo.save(any(Discente.class))).thenAnswer(inv -> inv.getArgument(0));

            Discente resultado = discenteService.criarDiscente(dto, 1, 10);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Maria");
            assertThat(resultado.getCurso()).isEqualTo(curso);
            verify(discenteRepo).save(any(Discente.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome do discente for inválido")
        void deveLancarExcecaoQuandoNomeInvalido() {
            DiscenteDTO dto = new DiscenteDTO();
            dto.setNome("   ");
            dto.setEmail("maria@ufma.br");
            dto.setSenha("123456");
            dto.setSemestre(5);

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);

            assertThatThrownBy(() -> discenteService.criarDiscente(dto, 1, 10))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Nome invalido");
        }
    }

    @Nested
    @DisplayName("buscarPorId()")
    class BuscarPorId {
        @Test
        @DisplayName("Deve buscar discente por id")
        void deveBuscarDiscentePorIdComSucesso() throws RegraDeNegocioException {
            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));

            Discente resultado = discenteService.buscarPorId(2);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getEmail()).isEqualTo("maria@ufma.br");
        }

        @Test
        @DisplayName("Deve lançar exceção quando discente não existir")
        void deveLancarExcecaoQuandoDiscenteNaoExiste() {
            when(discenteRepo.findById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> discenteService.buscarPorId(99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Discente não encontrado");
        }
    }

    @Nested
    @DisplayName("mostrarPainelDeHoras()")
    class MostrarPainelDeHoras {
        @Test
        @DisplayName("Deve montar painel de horas com sucesso")
        void deveMostrarPainelDeHorasComSucesso() throws RegraDeNegocioException {
            discente.setBanco_de_horas(240F);
            discente.setCh_total_cumprida(120F);

            when(discenteRepo.findById(2)).thenReturn(Optional.of(discente));

            HorasDiscenteDTO resultado = discenteService.mostrarPainelDeHoras(2);

            assertThat(resultado.getNome()).isEqualTo("Maria");
            assertThat(resultado.getHorasTotal()).isEqualTo(240F);
            assertThat(resultado.getHorasCompletas()).isEqualTo(120F);
        }
    }
}
