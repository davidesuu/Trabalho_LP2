package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.DocenteDTO;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Papel;
import com.exemplo.ufmaextensao.entity.Usuario;
import com.exemplo.ufmaextensao.repository.DocenteRepo;
import com.exemplo.ufmaextensao.repository.PapelRepo;
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
@DisplayName("DocenteService - Testes Unitários")
class DocenteServiceTest {

    @Mock
    private SecurityService securityService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private DocenteRepo docenteRepo;
    @Mock
    private PapelRepo papelRepo;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private DocenteService docenteService;

    private Usuario usuario;
    private Docente docente;
    private Papel papel;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);

        papel = new Papel();
        papel.setNome("DOCENTE");

        docente = Docente.builder()
                .id(1)
                .nome("Professor Silva")
                .email("silva@ufma.br")
                .siape("12345")
                .departamento("DEINF")
                .build();
    }

    @Nested
    @DisplayName("criarDocente()")
    class CriarDocente {
        @Test
        @DisplayName("Deve criar docente com sucesso")
        void deveCriarDocenteComSucesso() throws RegraDeNegocioException {
            DocenteDTO dto = new DocenteDTO();
            dto.setNome("Professor Silva");
            dto.setEmail("silva@ufma.br");
            dto.setSenha("123456");
            dto.setSiape("12345");
            dto.setDepartamento("DEINF");

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);
            when(papelRepo.findByNome("DOCENTE")).thenReturn(Optional.of(papel));
            when(passwordEncoder.encode("123456")).thenReturn("hashSenha");
            when(docenteRepo.save(any(Docente.class))).thenAnswer(inv -> inv.getArgument(0));

            Docente resultado = docenteService.criarDocente(dto, 1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Professor Silva");
            assertThat(resultado.getEmail()).isEqualTo("silva@ufma.br");
            verify(docenteRepo).save(any(Docente.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome do docente for inválido")
        void deveLancarExcecaoQuandoNomeInvalido() {
            DocenteDTO dto = new DocenteDTO();
            dto.setNome("   ");
            dto.setEmail("silva@ufma.br");
            dto.setSenha("123456");
            dto.setSiape("12345");
            dto.setDepartamento("DEINF");

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(usuario);

            assertThatThrownBy(() -> docenteService.criarDocente(dto, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Nome invalido");
        }
    }

    @Nested
    @DisplayName("buscarPorId()")
    class BuscarPorId {
        @Test
        @DisplayName("Deve buscar docente pelo id")
        void deveBuscarDocentePorIdComSucesso() throws RegraDeNegocioException {
            when(docenteRepo.findDocenteById(1)).thenReturn(Optional.of(docente));

            Docente resultado = docenteService.buscarPorId(1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getSiape()).isEqualTo("12345");
        }

        @Test
        @DisplayName("Deve lançar exceção quando docente não for encontrado")
        void deveLancarExcecaoQuandoDocenteNaoExiste() {
            when(docenteRepo.findDocenteById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> docenteService.buscarPorId(99))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Docente não encontrado");
        }
    }
}
