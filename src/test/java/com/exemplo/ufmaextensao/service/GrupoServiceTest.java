package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.dto.GrupoDTO;
import com.exemplo.ufmaextensao.entity.Discente;
import com.exemplo.ufmaextensao.entity.Docente;
import com.exemplo.ufmaextensao.entity.Grupo;
import com.exemplo.ufmaextensao.repository.GrupoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GrupoService - Testes Unitários")
class GrupoServiceTest {

    @Mock
    private GrupoRepo grupoRepo;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private DocenteService docenteService;
    @Mock
    private com.exemplo.ufmaextensao.repository.PapelRepo papelRepo;
    @Mock
    private SecurityService securityService;
    @Mock
    private LogService logService;
    @Mock
    private DiscenteService discenteService;

    @InjectMocks
    private GrupoService grupoService;

    private Docente docente;
    private Discente discente;
    private Grupo grupo;

    @BeforeEach
    void setUp() {
        docente = Docente.builder()
                .id(1)
                .nome("Professor")
                .siape("123")
                .build();

        discente = Discente.builder()
                .id(2)
                .nome("Aluno")
                .build();

        grupo = Grupo.builder()
                .id(10)
                .nome("Grupo de Pesquisa")
                .email("grupo@ufma.br")
                .descricao("Descrição")
                .responsavel(docente)
                .discentes(new ArrayList<>())
                .diretoria(new ArrayList<>())
                .build();
    }

    @Nested
    @DisplayName("criarGrupo()")
    class CriarGrupo {
        @Test
        @DisplayName("Deve criar grupo com sucesso")
        void deveCriarGrupoComSucesso() throws RegraDeNegocioException {
            GrupoDTO dto = new GrupoDTO();
            dto.setNome("Grupo de Pesquisa");
            dto.setEmail("grupo@ufma.br");
            dto.setDescricao("Descrição");

            when(usuarioService.obterUsuarioPorId(1)).thenReturn(docente);
            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(grupoRepo.save(any(Grupo.class))).thenAnswer(inv -> inv.getArgument(0));

            Grupo resultado = grupoService.criarGrupo(dto, 1, 1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Grupo de Pesquisa");
            verify(grupoRepo).save(any(Grupo.class));
        }
    }

    @Nested
    @DisplayName("adicionarMembro()")
    class AdicionarMembro {
        @Test
        @DisplayName("Deve adicionar um membro ao grupo")
        void deveAdicionarMembroComSucesso() throws RegraDeNegocioException {
            when(grupoRepo.findById(10)).thenReturn(Optional.of(grupo));
            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(discenteService.buscarPorId(2)).thenReturn(discente);
            when(grupoRepo.save(any(Grupo.class))).thenAnswer(inv -> inv.getArgument(0));

            Grupo resultado = grupoService.adicionarMembro(10, 2, 1);

            assertThat(resultado.getDiscentes()).contains(discente);
            verify(logService).salvarLog(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando o discente já é membro do grupo")
        void deveLancarExcecaoQuandoDiscenteJaEMembro() throws RegraDeNegocioException {
            grupo.getDiscentes().add(discente);

            when(grupoRepo.findById(10)).thenReturn(Optional.of(grupo));
            when(docenteService.buscarPorId(1)).thenReturn(docente);
            when(discenteService.buscarPorId(2)).thenReturn(discente);

            assertThatThrownBy(() -> grupoService.adicionarMembro(10, 2, 1))
                    .isInstanceOf(RegraDeNegocioException.class)
                    .hasMessageContaining("Discente já é membro desse grupo");
        }
    }
}
