package Telas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entity.*;
import Service.AproveitamentoService;
import Service.GrupoService;
import Service.InscricaoServico;
import Service.OportunidadeService;
import Enum.*;

import javax.print.Doc;


public class TelaDocente {
    public static void mostrarTela (OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService, GrupoService grupoService, InscricaoServico inscricaoServico, Docente docente){
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
            System.out.println("6 - Sair");

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opt) {
                case 1:
                    TelaCriarOportunidade(oportunidadeService, scanner, docente); // Feito
                    break;
                case 2:
                    verOportunidadesTela(oportunidadeService, scanner, docente); // Feito
                    break;
                case 3:
                    verificarAproveitamentosTela(aproveitamentoService, scanner, docente); //Feito
                    break;
                case 4:
                    verificarInscricoes(inscricaoServico, scanner);  //Feito
                    break;
                case 5:
                    verificarGruposTela(grupoService, scanner, docente); //Feito
                    break;
                case 6:
                    System.out.print("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida.");
                    break;
            }
        } while (opt != 6);
    }

    static void TelaCriarOportunidade(OportunidadeService oportunidadeService,
                                      Scanner scanner,
                                      Docente docente) {
        System.out.println("\nCRIAR OPORTUNIDADE");

        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.println("Tipo (1-PROJETO, 2-CURSO, 3-EVENTO, 4-OFICINA): ");
        TipoOportunidade tipo;
        try {
            int optTipo = Integer.parseInt(scanner.nextLine());
            tipo = switch (optTipo) {
                case 1 -> TipoOportunidade.PROJETO;
                case 2 -> TipoOportunidade.CURSO;
                case 3 -> TipoOportunidade.EVENTO;
                case 4 -> TipoOportunidade.OFICINA;
                default -> throw new IllegalArgumentException("Tipo inválido.");
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo inválido, operação cancelada.");
            return;
        }

        System.out.println("Modalidade (1-PRESENCIAL, 2-REMOTO, 3-HIBRIDO): ");
        Modalidade modalidade;
        try {
            int optMod = Integer.parseInt(scanner.nextLine());
            modalidade = switch (optMod) {
                case 1 -> Modalidade.PRESENCIAL;
                case 2 -> Modalidade.REMOTO;
                case 3 -> Modalidade.HIBRIDO;
                default -> throw new IllegalArgumentException("Modalidade inválida.");
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Modalidade inválida, operação cancelada.");
            return;
        }

        System.out.println("Carga horária: ");
        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Carga horária inválida, operação cancelada.");
            return;
        }

        System.out.println("Vagas: ");
        int vagas;
        try {
            vagas = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número de vagas inválido, operação cancelada.");
            return;
        }

        try {
            oportunidadeService.criarOportunidade(titulo, descricao, tipo,
                    modalidade, cargaHoraria, vagas, docente);
            System.out.println("Oportunidade criada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao criar oportunidade: " + e.getMessage());
        }
    }

    static void verOportunidadesTela(OportunidadeService oportunidadeService,
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

        Long id;
        try {
            id = Long.parseLong(scanner.nextLine()); // ← nextLine + parse, sem nextLong
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
                }
                break;
            case 2:
                try {
                    oportunidadeService.rejeitarOportunidade(id, docente);
                    System.out.println("Oportunidade rejeitada.");
                } catch (RuntimeException e) {
                    System.out.println("Erro!");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida");
        }

    }
    static void verificarAproveitamentosTela(AproveitamentoService aproveitamentoService, Scanner scanner, Docente docente){
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarPendentes();

        aproveitamentos.forEach(a ->
                System.out.println("[" + a.getId() + "] " + a.getDescricao() + " | " + a.getDiscente()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");
        long id;
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

    static void verificarGruposTela(GrupoService grupoService, Scanner scanner, Docente docente){
        int opt = 0;
        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Ver Grupos");
            System.out.println("2 - Criar um novo Grupo");
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
                    criarGruposTela(grupoService, scanner, docente);
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
            System.out.println("Você não possui solicitações.");
            return;
        }

        System.out.println("\nMEUS GRUPOS");
        grupos.forEach(g -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "         + g.getId());
            System.out.println("Nome: "  + g.getNome());
            System.out.println("Descrição: "+ g.getDescricao());
            System.out.println("Tipo: "      + g.getTipo());
        });
        System.out.println("─────────────────────────────");
    }

    static void criarGruposTela(GrupoService grupoService, Scanner scanner, Docente docente){
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Tipo: ");
        String tipo = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Descricao: ");
        String descricao = scanner.nextLine();

        try {
            grupoService.criarGrupo(
                    nome,
                    tipo,
                    email,
                    descricao,
                    docente
            );
            System.out.println("Grupo criado com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao criar grupo");
        }
    }

    static void verificarInscricoes(InscricaoServico inscricaoServico, Scanner scanner){
        List<Inscricao> inscricoes = inscricaoServico.listarPendente();

                inscricoes.forEach(i -> {
                    System.out.println("─────────────────────────────");
                    System.out.println("ID: "            + i.getId());
                    System.out.println("Discente: "      + i.getDiscente());
                    System.out.println("Oportunidade: "  + i.getOportunidade());
                    System.out.println("Status: "        + i.getStatus());
                    System.out.println("Motivação: "     + i.getMotivacao());

                });
        System.out.println("─────────────────────────────");
        long id;
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
                inscricaoServico.aprovar(id);
                System.out.println("Inscrição aprovado com sucesso!");
                break;
            case 2:
                inscricaoServico.rejeitar(id);
                System.out.println("Inscrição rejeitada com sucesso!");
                break;
            case 3:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção invalida");
                break;
        };


    }
}