package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.CursoDTO;
import com.exemplo.ufmaextensao.entity.Curso;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.CursoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CursoService - Testes Unitários")
class CursoServiceTest {

    @Mock
    private CursoRepo cursoRepo;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private SecurityService securityService;

    @InjectMocks
    private CursoService cursoService;

    private Usuario usuario;
    private Curso curso;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);

        curso = Curso.builder()
                .id(1)
                .nome("Engenharia de Software")
                .codigo(2024)
                .build();
    }

    @Nested
    @DisplayName("buscarCursoPorId()")
    class BuscarCursoPorId {
        @Test
        @DisplayName("Deve buscar curso pelo id com sucesso")
        void deveBuscarCursoPorIdComSucesso() throws RegraDeNegocioException {
            when(cursoRepo.findCursoById(1)).thenReturn(Optional.of(curso));

            Curso resultado = cursoService.buscarCursoPorId(1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Engenharia de Software");
        }

        @Test
        @DisplayName("Deve lançar exceção quando curso não for encontrado")
        void deveLancarExcecaoQuandoCursoNaoExiste() {
            when(cursoRepo.findCursoById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> cursoService.buscarCursoPorId(99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Curso não encontrado");
        }
    }

    @Nested
    @DisplayName("criarCurso()")
    class CriarCurso {
        @Test
        @DisplayName("Deve criar curso com sucesso")
        void deveCriarCursoComSucesso() throws RegraDeNegocioException {
            CursoDTO dto = new CursoDTO();
            dto.setNome("ADS");
            dto.setCodigo(101);

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(cursoRepo.save(any(Curso.class))).thenAnswer(inv -> inv.getArgument(0));

            Curso resultado = cursoService.criarCurso(dto, 1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("ADS");
            assertThat(resultado.getCodigo()).isEqualTo(101);
            verify(cursoRepo).save(any(Curso.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome do curso for inválido")
        void deveLancarExcecaoQuandoNomeInvalido() {
            CursoDTO dto = new CursoDTO();
            dto.setNome("   ");
            dto.setCodigo(101);

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);

            assertThatThrownBy(() -> cursoService.criarCurso(dto, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Nome do curso é obrigatório");
        }
    }

    @Nested
    @DisplayName("obterCursos()")
    class ObterCursos {
        @Test
        @DisplayName("Deve retornar todos os cursos cadastrados")
        void deveRetornarCursosComSucesso() throws RegraDeNegocioException {
            when(cursoRepo.findAll()).thenReturn(List.of(curso));

            List<Curso> resultado = cursoService.obterCursos();

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getNome()).isEqualTo("Engenharia de Software");
        }

        @Test
        @DisplayName("Deve lançar exceção quando não houver cursos cadastrados")
        void deveLancarExcecaoQuandoNaoHouverCursos() {
            when(cursoRepo.findAll()).thenReturn(List.of());

            assertThatThrownBy(() -> cursoService.obterCursos())
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Nenhum curso encontrado");
        }
    }
}
