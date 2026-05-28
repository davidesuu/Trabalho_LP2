package Telas;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Entity.*;
import Repository.UsuarioRepository;
import Service.*;


public class TelaDocente extends Tela{
    protected final Docente docente;

    public TelaDocente( OportunidadeService oportunidadeService,
                        AproveitamentoService aproveitamentoService,
                        InscricaoService inscricaoService,
                        GrupoService grupoService, UsuarioService usuarioService,
                        Docente docente) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService, usuarioService);
        this.docente = docente;
    }

    @Override
    public void mostrarTela (){
        int opt = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tela do Docente");


        do {
            System.out.println("\nEscolha uma opção: ");
            System.out.println("1 - Criar uma oportunidade");
            System.out.println("2 - Verificar novos planos de Atividades");
            System.out.println("3 - Verificar aproveitamentos");
            System.out.println("4 - Verificar inscrição de discentes em Oportunidades");
            System.out.println("5 - Verificar Grupos");
            
            System.out.println("0 - Sair");

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opt) {
                case 1:
                    TelaOportunidade.CriarOportunidade(oportunidadeService, scanner, docente); // Feito
                    break;
                case 2:
                    aprovarOportunidades(oportunidadeService, scanner, docente);
                    // Falta mudar pra generalizar tbm, criar listar por enum
                    break;
                case 3:
                    verificarAproveitamentos(aproveitamentoService, scanner, docente); //Feito
                    break;
                case 4:
                    verificarInscricoes(inscricaoService, scanner);  //Feito
                    break;
                case 5:
                    verificarGruposTela(grupoService, usuarioService, scanner, docente); //Feito
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
    static void verificarAproveitamentos(AproveitamentoService aproveitamentoService, Scanner scanner, Docente docente){
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
                aproveitamentoService.aprovarAproveitamento(id, docente);
                System.out.println("Aproveitamento aprovado com sucesso!");
                break;
            case 2:
                aproveitamentoService.rejeitarAproveitamento(id, docente);
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

    static void verificarGruposTela(GrupoService grupoService, UsuarioService usuarioService, Scanner scanner, Docente docente){
        int opt = 0;
        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Ver Grupos");
            System.out.println("2 - Adicionar novos membros");
            System.out.println("3 - Sair");
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
                    System.out.println("Saindo...");
                    break;
            }
        }while (opt != 3);
    }

    static void verGruposTelas(GrupoService grupoService, Docente docente){
        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        if (grupos.isEmpty()) {
            System.out.println("Você não é responsável por nenhum grupo.");
            return;
        }

        System.out.println("\nMEUS GRUPOS");
        grupos.forEach(g -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "         + g.getId());
            System.out.println("Nome: "  + g.getNome());
            System.out.println("Descrição: "+ g.getDescricao());
            System.out.println("Membros: "+ g.getMembros());
        });
        System.out.println("─────────────────────────────");
    }

    static void AdicionarMembrosTela(GrupoService grupoService, UsuarioService usuarioService,Scanner scanner, Docente docente){
        List<Grupo> grupos = grupoService.listarPorDocente(docente);
        List<Long> keyset = grupoService.ListarIndice(grupos);
        Integer id;
        String matricula;
        if (grupos.isEmpty()){
            System.out.println("Você não é responsável por nenhum grupo.");
            return;
        }

        grupos.forEach(o ->
                System.out.println("[" + o.getId() + "] " + o.getNome() + " | " + o.getMembros()));

        System.out.println("Digite o ID do grupo em que será adicionado e 0 para voltar");
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválida.");
            return;
        }

        System.out.println("Digite a matricula do discente que será adicionado e enter para voltar");
        try { //mudar pq é str
            matricula = scanner.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("Matricula inválida.");
            return;
        }

        if (id == 0 || matricula == null) return;
        try{
            Discente disc = usuarioService.buscarMatricula(matricula);
            grupoService.adicionarMembro(keyset.get(id-1), disc); //bagunça rola solta
        } catch (RuntimeException e){
            System.out.println("Erro ao se adicionar membro"); //se for null isso acontece
        }
    }

    static void verificarInscricoes(InscricaoService inscricaoService, Scanner scanner){
        List<Inscricao> inscricoes = inscricaoService.listarPendente();

        inscricoes.forEach(i -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "            + i.getId());
            System.out.println("Discente: "      + i.getDiscente());
            System.out.println("Oportunidade: "  + i.getOportunidade());
            System.out.println("Status: "        + i.getStatus());
            System.out.println("Motivação: "     + i.getMotivacao());

        });
        System.out.println("─────────────────────────────");
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
            opc = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                inscricaoService.aprovar(id);
                System.out.println("Inscrição aprovado com sucesso!");
                break;
            case 2:
                inscricaoService.rejeitar(id);
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
}