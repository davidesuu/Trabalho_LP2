import Entity.*;
import Repository.impl.*;
import Service.*;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Telas.TelaDiscente;
import Telas.TelaDiscenteDiretor;
import Telas.TelaDocente;

void main(String[] args) {

    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
    UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl();
    GrupoRepositoryImpl grupoRepository = new GrupoRepositoryImpl();
    AproveitamentoRepositoryImpl aproveitamentoRepository = new AproveitamentoRepositoryImpl();
    AuthService authService = new AuthService(usuarioRepository);
    UsuarioService usuarioService = new UsuarioService(usuarioRepository);
    GrupoService grupoService = new GrupoService(grupoRepository);
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository);
    InscricaoServico inscricaoServico = new InscricaoServico(inscricoesRepository);

// -------------------------------------------Usuarios de teste----------------------------------------------
    Curso cursoComputacao = new Curso(
            "Ciência da Computação",
            123,
            3200,
            "PPC 2023"
    );

    Discente discente = usuarioService.cadastrarDiscente(
            "Perla Sousa",
            "perla@",
            "senha",
            "2023001234",
            3,
            cursoComputacao
    );

    DiscenteDiretor discenteDiretor = usuarioService.cadastrarDiscenteDiretor(
            "David Martins",
            "dvd@",
            "senha",
            "2022005678",
            5,
            cursoComputacao,
            "Diretora de Eventos",
            2
    );


    Docente docente1 = usuarioService.cadastrarDocente(
            "Prof. Dr. Geraldo",
            "geraldo@",
            "senha",
            "SIAPE12345",
            "Departamento de Computação"
    );

    Docente docente2 = usuarioService.cadastrarDocente(
            "Prof. Dr. Luis Rivero",
            "luisrivero@",
            "senha",
            "SIAPE12325",
            "Departamento de Computação"
    );
    //--------------------------------------------------------------------------------


    Scanner scanner = new Scanner(System.in);
    do {
        IO.println("\nBEM VINDO");
        IO.println("1 - Login");
        IO.println("2 - Cadastro");
        IO.println("3 - Menu secreto");
        IO.println("0 - Sair");
        IO.println("Opção: ");

        String opc = scanner.nextLine();

        switch (opc) {
            case "1":
                try {
                    Login(authService);
                    TelaPrincipal(authService, oportunidadeService, aproveitamentoService, grupoService, inscricaoServico);
                } catch (RuntimeException e) {
                    IO.println("Erro no login: " + e.getMessage());
                }
                break;
            case "2":
                Cadastro(usuarioService);
                break;
            case "3":
                IO.println("Gerando QrCode...");
                String text = "certificado";
                String output = "qrcode.png";

                CertificadoService.generate("oiii.png");
                break;
            case "0":
                IO.println("Encerrando...");
                return; 
            default:
                IO.println("Opção inválida.");
        }

    } while (true);
    
    //String text = "https://example.com";
    //String output = "qrcode.png";

    //CertificadoService.generate("oiii.png");

    }

    public void Cadastro(UsuarioService usuarioService){
        Curso curso = new Curso("ccomp", 123, 60, "2");
        IO.println("Cadastro \n____________ \n");
        String opc;
        String nome, email, senha;
        Scanner scanner = new Scanner(System.in);
        IO.println("Nome: ");
        nome = scanner.nextLine();
        IO.println("Email: ");
        email = scanner.nextLine();
        IO.println("Senha: ");
        senha = scanner.nextLine();
        IO.println("Você é Discente/Docente/DiscDiretor: ");
        opc = scanner.nextLine();

        switch (opc){
            case "Discente":
                String matricula1;
                Integer semestre1;
                IO.println("Matricula: ");
                matricula1 = scanner.nextLine();
                IO.println("Semestre: ");
                semestre1 = scanner.nextInt();
                scanner.nextLine();
                Discente discente = usuarioService.cadastrarDiscente(nome, email, senha, matricula1, semestre1, curso);
                break;
            case "DiscDiretor":
                String matricula2;
                Integer semestre2;
                Grupo grupo;
                String cargo;
                Integer duracao;
                IO.println("Matricula: ");
                matricula2 = scanner.nextLine();
                IO.println("Semestre: ");
                semestre2 = scanner.nextInt();
                scanner.nextLine();
                IO.println("Cargo: ");
                cargo = scanner.nextLine();
                IO.println("Duracao: ");
                duracao = scanner.nextInt();
                scanner.nextLine();
                DiscenteDiretor discenteDiretor = usuarioService.cadastrarDiscenteDiretor(nome, email, senha,
                        matricula2, semestre2, curso, cargo, duracao);
                break;
            case "Docente":
                String siape, departamento;
                IO.println("Siape: ");
                siape = scanner.nextLine();
                IO.println("Departamento: ");
                departamento = scanner.nextLine();
                Docente docente = usuarioService.cadastrarDocente(nome, email, senha, siape, departamento);
                break;
            default: IO.println("Opção Invalida!");
        }
    }

    public Usuario Login(AuthService authService){
        String email, senha;
        IO.println("LOGIN:");
        Scanner scanner = new Scanner(System.in);
        IO.println("Email: ");
        email = scanner.nextLine();
        IO.println("Senha: ");
        senha = scanner.nextLine();
        Usuario usuario = authService.login(email, senha);
        IO.println("Bem Vindo " + usuario.getNome());
        return authService.getUsuarioLogado();
    }

    public void TelaPrincipal(AuthService authService, OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService, GrupoService grupoService, InscricaoServico inscricaoServico){
        Usuario usuario = authService.getUsuarioLogado();
        switch (usuario){
            case DiscenteDiretor discenteDiretor:
                IO.println("i'm discente diretor");
                TelaDiscenteDiretor.mostarTela(oportunidadeService, aproveitamentoService, inscricaoServico, discenteDiretor);
                break;
            case Discente discente:
                IO.println("soy discente");
                TelaDiscente.mostarTela(oportunidadeService, aproveitamentoService, inscricaoServico, discente);
                break;
            case Docente docente:
                IO.println("私は　docente");
                TelaDocente.mostrarTela(oportunidadeService, aproveitamentoService, grupoService, inscricaoServico, docente);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + usuario);

        }
        authService.logout();
        IO.println("Sessão Encerrada. Voltando...");
        return;
    }