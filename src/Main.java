import java.io.IOException;
import java.util.Scanner;

import Entity.*;
import Repository.CertificadoRepository;
import Repository.impl.*;
import Service.*;
import Telas.*;
import Enum.*;
public class Main {

    public static void main(String[] args) throws IOException {

        //Repositorios
        OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
        InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
        UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl();
        GrupoRepositoryImpl grupoRepository = new GrupoRepositoryImpl();
        AproveitamentoRepositoryImpl aproveitamentoRepository = new AproveitamentoRepositoryImpl();
        CertificadoRepositoryImpl certificadoRepository = new CertificadoRepositoryImpl();

        //Services
        AuthService authService = new AuthService(usuarioRepository);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        GrupoService grupoService = new GrupoService(grupoRepository);
        OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
        AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository);
        InscricaoServico inscricaoServico = new InscricaoServico(inscricoesRepository);
        CertificadoService certificadoService = new CertificadoService(certificadoRepository);

        // Usuários de teste
        Curso curso = new Curso("Ciência da Computação", 123, 3200, "PPC 2023");

        usuarioService.cadastrarDiscente("Perla Sousa", "perla@", "senha", "2023001234", 3, curso);

        usuarioService.cadastrarDiscenteDiretor("David Martins", "dvd@", "senha", "2022005678", 5, curso, "Diretora", 2);

        usuarioService.cadastrarDocente("Prof. Geraldo", "geraldo@", "senha", "SIAPE123", "Comp");

        // Oportunidade oportunidade = oportunidadeService.criarOportunidade("titulo", "descriçao", TipoOportunidade.CURSO, Modalidade.HIBRIDO, 10, 10, usuarioService.getId(2L));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBEM VINDO");
            System.out.println("1 - Login");
            System.out.println("2 - Cadastro");
            System.out.println("3 - Menu secreto");
            System.out.println("0 - Sair");

            String opc = scanner.nextLine();

            switch (opc) {
                case "1":
                    try {
                        Login(authService);
                        TelaPrincipal(authService, oportunidadeService, aproveitamentoService, grupoService, inscricaoServico);
                    } catch (RuntimeException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case "2":
                    Cadastro(usuarioService);
                    break;
                case "3":
                    // certificadoService.criarCertificado(oportunidade, (Discente) usuarioService.getId(2L), 1);
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public static void Cadastro(UsuarioService usuarioService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();

        System.out.println("Tipo (Discente/Docente/DiscDiretor):");
        String opc = scanner.nextLine();

        Curso curso = new Curso("ccomp", 123, 60, "2");

        switch (opc) {
            case "Discente":
                System.out.println("Matricula:");
                String mat = scanner.nextLine();

                System.out.println("Semestre:");
                int sem = scanner.nextInt();
                scanner.nextLine();

                usuarioService.cadastrarDiscente(nome, email, senha, mat, sem, curso);
                break;

            case "Docente":
                System.out.println("Siape:");
                String siape = scanner.nextLine();

                System.out.println("Departamento:");
                String dep = scanner.nextLine();

                usuarioService.cadastrarDocente(nome, email, senha, siape, dep);
                break;

            default:
                System.out.println("Tipo inválido");
        }
    }

    public static Usuario Login(AuthService authService) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();

        Usuario u = authService.login(email, senha);
        System.out.println("Bem-vindo " + u.getNome());

        return u;
    }

    public static void TelaPrincipal(
            AuthService authService,
            OportunidadeService oportunidadeService,
            AproveitamentoService aproveitamentoService,
            GrupoService grupoService,
            InscricaoServico inscricaoServico
    ) {
        Usuario usuario = authService.getUsuarioLogado();

        if (usuario instanceof DiscenteDiretor dd) {
            TelaDiscenteDiretor.mostarTela(oportunidadeService, aproveitamentoService, inscricaoServico, dd);

        } else if (usuario instanceof Discente d) {
            TelaDiscente.mostarTela(oportunidadeService, aproveitamentoService, inscricaoServico, d);

        } else if (usuario instanceof Docente doc) {
            TelaDocente.mostrarTela(oportunidadeService, aproveitamentoService, grupoService, inscricaoServico, doc);
        }

        authService.logout();
        System.out.println("Sessão encerrada");
    }
}