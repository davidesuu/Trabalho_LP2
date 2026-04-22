import Entity.*;
import Repository.impl.AproveitamentoRepositoryImpl;
import Repository.impl.InscricoesRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;
import Service.*;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Telas.TelaDiscente;
import Telas.TelaDiscenteDiretor;
import Telas.TelaDocente;

void main(String[] args) {







    //manter até 25
    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
    UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl();
    AproveitamentoRepositoryImpl aproveitamentoRepository = new AproveitamentoRepositoryImpl();
    AuthService authService = new AuthService(usuarioRepository);
    UsuarioService usuarioService = new UsuarioService(usuarioRepository);
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository);
    //

    // Criando um curso
    Curso cursoComputacao = new Curso(
            "Ciência da Computação",
            123,
            3200,
            "PPC 2023"
    );

    // Cadastro de Discente
    Discente discente = usuarioService.cadastrarDiscente(
            "João Silva",
            "joao",
            "senha",
            "2023001234",
            3,
            cursoComputacao
    );

    // Cadastro de Discente Diretor
    DiscenteDiretor discenteDiretor = usuarioService.cadastrarDiscenteDiretor(
            "Maria Souza",
            "maria",
            "senha",
            "2022005678",
            5,
            cursoComputacao,
            "Diretora de Eventos",
            2
    );

    // Cadastro de Docente
    Docente docente = usuarioService.cadastrarDocente(
            "Prof. Carlos",
            "carlos",
            "senha",
            "SIAPE12345",
            "Departamento de Computação"
    );

    Scanner scanner = new Scanner(System.in);
    do {
        IO.println("\n=== BEM VINDO ===");
        IO.println("1 - Login");
        IO.println("2 - Cadastro");
        IO.println("0 - Sair");
        IO.println("Opção: ");

        String opc = scanner.nextLine();

        switch (opc) {
            case "1":
                try {
                    Login(authService);
                    TelaPrincipal(authService, oportunidadeService, aproveitamentoService);
                } catch (RuntimeException e) {
                    IO.println("Erro no login: " + e.getMessage());
                }
                break;
            case "2":
                Cadastro(usuarioService);
                break;
            case "0":
                IO.println("Encerrando...");
                return; // ← sai do main
            default:
                IO.println("Opção inválida.");
        }

    } while (true);
    //ideia, usa o auth no login de todos, ai dependendo da escolha no switch no futuro, so fazer Usuario = new (escolha do switch)
    //como fazer isso? ainda nao sei
    //Docente docente = new Docente("Geraldo", "geraldobraz@", "senha", "sda", "2131", "dmat");



    //Oportunidade oportunidade1 = oportunidadeService.criarOportunidade("Vaga marketing DA", "Disponivel agora", TipoOportunidade.EVENTO, Modalidade.PRESENCIAL, 24, 10, usuario);
    //Oportunidade oportunidade2 = oportunidadeService.criarOportunidade("Vaga PETCOMP", "Disponivel agora", TipoOportunidade.PROJETO, Modalidade.PRESENCIAL, 64, 12, usuario);
    //Criar oportunidade ta discentediretor, ve la depois, pois o docente tbm pode, pode fazer um switch com docente aprovado e discente diretor pendendte


    //IO.println("Oportunidade Criada " + oportunidade1);   //ainda nao cadastrada
    //IO.println("Oportunidade Criada " + oportunidade2);
    //oportunidadeService.publicarOpurtunidade(1L, docente);
    //oportunidadeService.publicarOpurtunidade(2L, docente);
    //Curso curso = new Curso("ccomp", 123, 60, "2");
    //Usuario sam = new Discente("nome", "email", "senha", "papel",
    //        "matricula", 3, curso);
    //String text = "https://example.com";
    //String output = "qrcode.png";

    //CertificadoService.generate("oiii.png");

    }

    public void Cadastro(UsuarioService usuarioService){
        Curso curso = new Curso("ccomp", 123, 60, "2");
        IO.println("Cadastro \n ____________ \n");
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
                IO.println("Matricula: \n");
                matricula1 = scanner.nextLine();
                IO.println("Semestre: \n");
                semestre1 = scanner.nextInt();
                scanner.nextLine();
                Discente discente = usuarioService.cadastrarDiscente(nome, email, senha, matricula1, semestre1, curso);
                break;
            case "DiscDiretor":
                String matricula2;
                Integer semestre2;
                //Grupo grupo;
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
                IO.println("Siape: \n");
                siape = scanner.nextLine();
                IO.println("Departamento: \n");
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

    public void TelaPrincipal(AuthService authService, OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService){
        Usuario usuario = authService.getUsuarioLogado();
        switch (usuario){
            case DiscenteDiretor dd:
                IO.println("sou discente diretor");
                Oportunidade oportunidade1 = oportunidadeService.criarOportunidade("Vaga marketing DA", "Disponivel agora", TipoOportunidade.EVENTO, Modalidade.PRESENCIAL, 24, 10, usuario);
                TelaDiscenteDiretor.mostarTela(oportunidadeService, aproveitamentoService, dd);
                break;
            case Discente d:
                IO.println("soi discente");
                TelaDiscente.mostarTela(oportunidadeService, aproveitamentoService, d);
                break;
            case Docente doo:
                IO.println("Soi docente");
                TelaDocente.mostrarTela(oportunidadeService, aproveitamentoService, doo);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + usuario);

        }
        authService.logout();
        IO.println("Sessão Encerrada. Voltando...");
        return;
    }

    public void VerOportunidades(OportunidadeRepositoryImpl oportunidadeRepository){
        oportunidadeRepository.mostrarOportunidades();
    }

//public void InscreverOportunidade(Usuario usuario, UsuarioService usuarioService,OportunidadeRepositoryImpl oportunidadeRepository){
//    usuarioService.selecionarOportunidade();
//}


//discente: ver oportunidades, inscrever em oportunidade, ver aproveitamento,
// validar certificado, ver certificados

//discentediretor: ver oportunidades, inscrever em oportunidade, ver aproveitamento,
// validar certificado, ver certificados, criar oportunidade

//docente: criar oportunidade, aprovar, rejeitar