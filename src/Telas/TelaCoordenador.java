package Telas;

import Entity.*;
import Service.*;
import Enum.Cargo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TelaCoordenador extends TelaDocente{
    protected final Coordenador coordenador;

    public TelaCoordenador(OportunidadeService oportunidadeService,
                           AproveitamentoService aproveitamentoService,
                           InscricaoService inscricaoService,
                           GrupoService grupoService,
                           UsuarioService usuarioService,
                           PPCService ppcService,
                           Coordenador coordenador, LocalDate dataAtual) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService, usuarioService, ppcService, coordenador, dataAtual);
        this.coordenador = coordenador;

    }
   @Override
    public void mostrarTela (){
        int opt = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tela do Coordenador");

        do {
            System.out.println("\nEscolha uma opção: ");
            System.out.println("1 - Criar uma oportunidade");
            System.out.println("2 - Verificar novos planos de Atividades");
            System.out.println("3 - Verificar aproveitamentos");
            System.out.println("4 - Verificar inscrição de discentes em Oportunidades");
            System.out.println("5 - Verificar Grupos");
            System.out.println("6 - Cadastrar Novo PPC");
            System.out.println("0 - Sair");

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opt) {
                case 1:
                    TelaOportunidade.CriarOportunidade(oportunidadeService, scanner, coordenador, dataAtual); // Feito
                    break;
                case 2:
                    aprovarOportunidades(oportunidadeService, scanner, coordenador);
                    // Falta mudar pra generalizar tbm, criar listar por enum
                    break;
                case 3:
                    verificarAproveitamentos(aproveitamentoService, scanner, coordenador); //Feito
                    break;
                case 4:
                    verificarInscricoes(inscricaoService, scanner);  //Feito
                    break;
                case 5:
                    verificarGruposTela(grupoService, usuarioService, scanner, docente, coordenador); //Feito
                    break;
                case 6:
                    cadastrarPPC(ppcService, scanner);
                    break;
                case 0:
                    System.out.print("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida.");
                    break;
            }
        } while (opt != 0);
    }

    static void cadastrarPPC(PPCService ppcService, Scanner scanner) {

        System.out.println("ID do curso:");
        Long cursoId;
        try {
            cursoId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.println("Ano de vigência:");
        int anoVigencia;
        try {
            anoVigencia = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
            return;
        }

        System.out.println("Carga horária total exigida:");
        int chTotal;
        try {
            chTotal = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Carga horária inválida.");
            return;
        }

        ppcService.criarPPC(cursoId, anoVigencia, chTotal);
        System.out.println("PPC cadastrado com sucesso!");
    }

    static void aprovarOportunidades(OportunidadeService oportunidadeService,
                                     Scanner scanner,
                                     Docente docente) {
        List<Oportunidade> oportunidades = oportunidadeService.listarPendentes();

        if (oportunidades.isEmpty()) {
            System.out.println("Nenhuma oportunidade pendente.");
            return;
        }

        oportunidades.forEach(o ->
                System.out.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine()); // ← nextLine + parse, sem nextLong
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        if (id == 0) return;

        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");

        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }

        switch (opc) {
            case 1:
                try {
                    oportunidadeService.publicarOpurtunidade(id, docente);
                    System.out.println("Oportunidade aprovada!");
                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    oportunidadeService.rejeitarOportunidade(id, docente);
                    System.out.println("Oportunidade rejeitada.");
                } catch (RuntimeException | IOException e) {
                    System.out.println("Erro!");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida");
        }

    }
    static void verificarAproveitamentos(AproveitamentoService aproveitamentoService, Scanner scanner, Coordenador coordenador){
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarPendentes();

        aproveitamentos.forEach(a ->
                System.out.println("[" + a.getId() + "] " + a.getDescricao() + " | " + a.getDiscente()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");
        Long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID invalido");
            return;
        }
        if (id == 0) return;

        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                aproveitamentoService.aprovarAproveitamento(id, coordenador);
                System.out.println("Aproveitamento aprovado com sucesso!");
                break;
            case 2:
                aproveitamentoService.rejeitarAproveitamento(id, coordenador);
                System.out.println("Aproveitamento negado com sucesso!");
                break;
            case 3:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção invalida");
                break;
        }
    }

    static void verificarInscricoes(InscricaoService inscricaoService, Scanner scanner){
        List<Inscricao> inscricoes = inscricaoService.listarPendente();
        List<Long> keyset = inscricaoService.ListarIndice(inscricoes);
        int id;

        try{
            id = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID invalido");
            return;
        }
        if (id == 0) return;

        Inscricao i = inscricaoService.buscar(keyset.get(id - 1));
        Long index = i.getId();
        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                inscricaoService.aprovar(index);
                System.out.println("Inscrição aprovado com sucesso!");
                break;
            case 2:
                inscricaoService.rejeitar(index);
                System.out.println("Inscrição rejeitada com sucesso!");
                break;
            case 3:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção invalida");
                break;
        }
    }

    static void criarGruposTela(GrupoService grupoService, UsuarioService usuarioService, Scanner scanner, Coordenador coordenador){
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Descricao: ");
        String descricao = scanner.nextLine();
        System.out.println("SIAPE do prof. responsavel: ");
        String siape = scanner.nextLine();
        try {

            Docente doc = usuarioService.buscarSiape(siape);

            grupoService.criarGrupo(
                    nome,
                    email,
                    descricao,
                    doc,
                    coordenador
            );

            System.out.println("Grupo criado com sucesso!");

        } catch (RuntimeException e) {

            e.printStackTrace();

        }
    }

    static void verificarGruposTela(GrupoService grupoService, UsuarioService usuarioService, Scanner scanner, Docente docente, Coordenador coordenador){
        int opt = 0;
        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Ver Grupos");
            System.out.println("2 - Adicionar novos membros");
            System.out.println("3 - Promover Membros");
            System.out.println("4 - Remover Membros");
            System.out.println("5 - Criar Novo Grupo");
            System.out.println("6 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }
            switch (opt){
                case 1:
                    verGruposTelas(grupoService, docente);
                    break;
                case 2:
                    AdicionarMembrosTela(grupoService, usuarioService, scanner, docente);
                    break;
                case 3:
                    promoverMembroTela(grupoService, usuarioService, scanner, docente);
                    break;
                case 4:
                    removerMembroTela(grupoService, usuarioService, scanner, docente);
                    break;
                case 5:
                    criarGruposTela(grupoService, usuarioService, scanner, coordenador); //Feito
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
            }
        }while (opt != 6);
    }
    static void promoverMembroTela(
            GrupoService grupoService,
            UsuarioService usuarioService,
            Scanner scanner,
            Docente docente
    ){
        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        if (grupos.isEmpty()){
            System.out.println("Você não é responsável por nenhum grupo.");
            return;
        }

        System.out.println("\nGRUPOS:");

        grupos.forEach(g ->
                System.out.println("[" + g.getId() + "] " + g.getNome())
        );

        System.out.println("Digite o ID do grupo:");

        Long grupoId;

        try{
            grupoId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("ID inválido.");
            return;
        }

        Optional<Grupo> grupoOpt = grupos.stream()
                .filter(g -> g.getId().equals(grupoId))
                .findFirst();

        if (grupoOpt.isEmpty()){
            System.out.println("Grupo não encontrado.");
            return;
        }

        Grupo grupo = grupoOpt.get();

        if (grupo.getMembros().isEmpty()){
            System.out.println("Esse grupo não possui membros.");
            return;
        }

        List<Discente> membros = grupoService.getMembros(grupo);
        if (membros.isEmpty()){
            System.out.println("Esse grupo não possui membros.");
            return; }
        System.out.println("\n=== MEMBROS ===");
        membros.forEach(m -> {
            if (!(m instanceof DiscenteDiretor)) {
                System.out.println( m.getMatricula() + " - " + m.getNome() ); } });

        System.out.println("Digite a matrícula do membro:");

        String matricula = scanner.nextLine();

        Discente discente;

        try{
            discente = usuarioService.buscarMatricula(matricula);
        } catch (RuntimeException e){
            System.out.println("Discente não encontrado.");
            return;
        }

        if (!grupo.getMembros().contains(discente.getId())){
            System.out.println("Esse discente não pertence ao grupo.");
            return;
        }
        if (discente instanceof DiscenteDiretor){
            System.out.println("Esse discente já possui um cargo.");
            return;
        }

        System.out.println("\nEscolha um cargo:");
        System.out.println("1 - PRESIDENTE");
        System.out.println("2 - SECRETARIO");
        System.out.println("3 - TESOUREIRO");
        System.out.println("4 - VICE_PRESIDENTE");
        System.out.print("> ");

        Cargo cargo;
        try {
            int optMod = Integer.parseInt(scanner.nextLine());
            cargo = switch (optMod) {
                case 1 -> Cargo.PRESIDENTE;
                case 2 -> Cargo.SECRETARIO;
                case 3 -> Cargo.TESOUREIRO;
                case 4 -> Cargo.VICE_PRESIDENTE;
                default -> throw new IllegalArgumentException("Cargo inválida.");
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Cargo inválida, operação cancelada.");
            return;
        }

        System.out.println("Digite a duração do mandato:");

        Integer duracao;

        try{
            duracao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Duração inválida.");
            return;
        }

        try{
            DiscenteDiretor diretor =
                    new DiscenteDiretor(
                            discente,
                            cargo,
                            duracao,
                            grupo
                    );


            usuarioService.atualizarUsuario(diretor);
            Log log = new Log(discente.getMatricula(), discente.getNome(), grupo.getNome(), cargo.toString());

            System.out.println("Discente promovido com sucesso!");

        } catch (RuntimeException e){
            System.out.println("Erro ao promover discente.");
        }
    }

    static void removerMembroTela(
            GrupoService grupoService,
            UsuarioService usuarioService,
            Scanner scanner,
            Docente docente
    ){
        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        if (grupos.isEmpty()){
            System.out.println("Você não é responsável por nenhum grupo.");
            return;
        }

        System.out.println("\nGRUPOS:");

        grupos.forEach(g ->
                System.out.println("[" + g.getId() + "] " + g.getNome())
        );

        System.out.println("Digite o ID do grupo:");

        Long grupoId;

        try{
            grupoId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("ID inválido.");
            return;
        }

        Grupo grupo;

        try {
            grupo = grupoService.buscarGrupoPorId(grupoId);
        } catch (RuntimeException e){
            System.out.println("Erro ao buscar grupo.");
            return;
        }

        List<Discente> membros = grupoService.getMembros(grupo);

        if(membros.isEmpty()){
            System.out.println("Esse grupo não possui membros");
            return;
        }

        System.out.println("\nMEMBROS:");

        membros.forEach(m ->
                System.out.println(m.getMatricula() + " - " + m.getNome()));

        System.out.println("Digite a matrícula do membro:");

        String matricula = scanner.nextLine();

        Discente discente;

        try{
            discente = usuarioService.buscarMatricula(matricula);
        } catch (RuntimeException e){
            System.out.println("Discente não encontrado.");
            return;
        }

        if (!grupo.getMembros().contains(discente.getId())){
            System.out.println("Esse discente não pertence ao grupo.");
            return;
        }

        try{
            grupoService.removerMembro(discente, grupo);

            Long idOriginal = discente.getId();
            Vinculo vinculo = discente.getVinculo();

            discente = new Discente(discente.getNome(),
                    discente.getEmail(),
                    discente.getSenha(),
                    discente.getMatricula(),
                    discente.getSemestre(),
                    discente.getCurso());

            discente.setId(idOriginal);
            discente.setVinculo(vinculo);
            discente.setAtivo(true);

            usuarioService.atualizarUsuario(discente);

            System.out.println("Membro removido com sucesso!");

        } catch (RuntimeException e){
            System.out.println("Erro ao remover membro.");
        }
    }

    static void verGruposTelas(GrupoService grupoService, Docente docente){

        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        if (grupos.isEmpty()) {
            System.out.println("\nVocê não é responsável por nenhum grupo.");
            return;
        }

        System.out.println("\n=== MEUS GRUPOS ===");

        grupos.forEach(g -> {

            System.out.println("\nID: " + g.getId());
            System.out.println("Nome: " + g.getNome());
            System.out.println("Descrição: " + g.getDescricao());

            List<Discente> membros = grupoService.getMembros(g);
            System.out.println("Membros:");


            if(membros.isEmpty()){

                System.out.println(" - Nenhum membro");

            } else {
                membros.forEach(m -> {

                    String cargo;

                    if (m instanceof DiscenteDiretor diretor) {
                        cargo = diretor.getCargo().toString();
                        System.out.println(
                                " - "
                                        + m.getNome()
                                        + " | "
                                        + m.getMatricula()
                                        + " | "
                                        + cargo

                        );
                    } else {
                        cargo = "MEMBRO";
                        System.out.println(
                                " - "
                                        + m.getNome()
                                        + " | "
                                        + m.getMatricula()
                                        + " | "
                                        + cargo
                        );
                    }
                });
            }

            System.out.println("----------------------------");
        });
    }

    static void AdicionarMembrosTela(
            GrupoService grupoService,
            UsuarioService usuarioService,
            Scanner scanner,
            Docente docente
    ){

        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        Long id;
        String matricula;

        if (grupos.isEmpty()){
            System.out.println("Você não é responsável por nenhum grupo.");
            return;
        }

        System.out.println("\n=== GRUPOS ===");

        List<Long> keyset = grupoService.ListarIndice(grupos);

        System.out.println("\nDigite o número do grupo:");

        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        if(id <= 0 || id > keyset.size()){
            System.out.println("Grupo inválido.");
            return;
        }

        System.out.println("Digite a matrícula do discente:");

        matricula = scanner.nextLine();

        try{

            Discente disc = usuarioService.buscarMatricula(matricula);
            Grupo grupo = grupoService.buscarGrupoPorId(id);
            grupoService.adicionarMembro(id, disc);
            grupoService.atualizarGrupo(grupo);

            System.out.println("Membro adicionado com sucesso!");

        } catch (RuntimeException e){

            System.out.println("Erro ao adicionar membro.");

        }
    }
}
