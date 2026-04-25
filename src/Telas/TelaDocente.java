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
        IO.println("Tela do Docente");


        do {
            IO.println("\nEscolha uma opção: ");
            IO.println("1 - Criar uma oportunidade");
            IO.println("2 - Verificar novos planos de Atividades");
            IO.println("3 - Verificar aproveitamentos");
            IO.println("4 - Verificar inscrição de discentes em Oportunidades");
            IO.println("5 - Verificar Grupos");
            IO.println("6 - Sair");

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                IO.println("Opção inválida.");
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
                    IO.print("Saindo...");
                    break;
                default:
                    IO.println("Opção Inválida.");
                    break;
            }
        } while (opt != 6);
    }

    static void TelaCriarOportunidade(OportunidadeService oportunidadeService,
                                      Scanner scanner,
                                      Docente docente) {
        IO.println("\nCRIAR OPORTUNIDADE");

        IO.println("Título: ");
        String titulo = scanner.nextLine();

        IO.println("Descrição: ");
        String descricao = scanner.nextLine();

        IO.println("Tipo (1-PROJETO, 2-CURSO, 3-EVENTO, 4-OFICINA): ");
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
            IO.println("Tipo inválido, operação cancelada.");
            return;
        }

        IO.println("Modalidade (1-PRESENCIAL, 2-REMOTO, 3-HIBRIDO): ");
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
            IO.println("Modalidade inválida, operação cancelada.");
            return;
        }

        IO.println("Carga horária: ");
        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("Carga horária inválida, operação cancelada.");
            return;
        }

        IO.println("Vagas: ");
        int vagas;
        try {
            vagas = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("Número de vagas inválido, operação cancelada.");
            return;
        }

        try {
            oportunidadeService.criarOportunidade(titulo, descricao, tipo,
                    modalidade, cargaHoraria, vagas, docente);
            IO.println("Oportunidade criada com sucesso!");
        } catch (RuntimeException e) {
            IO.println("Erro ao criar oportunidade: " + e.getMessage());
        }
    }

    static void verOportunidadesTela(OportunidadeService oportunidadeService,
                                     Scanner scanner,
                                     Docente docente) {
        List<Oportunidade> oportunidades = oportunidadeService.listarPendentes();

        if (oportunidades.isEmpty()) {
            IO.println("Nenhuma oportunidade pendente.");
            return;
        }

        oportunidades.forEach(o ->
                IO.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        IO.println("─────────────────────────────");
        IO.println("Digite o ID da oportunidade (0 para voltar): ");

        Long id;
        try {
            id = Long.parseLong(scanner.nextLine()); // ← nextLine + parse, sem nextLong
        } catch (NumberFormatException e) {
            IO.println("ID inválido.");
            return;
        }

        if (id == 0) return;

        IO.println("1 - Aprovar");
        IO.println("2 - Rejeitar");
        IO.println("0 - Voltar");

        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            IO.println("Opção inválida.");
            return;
        }

        switch (opc) {
            case 1:
                try {
                    oportunidadeService.publicarOpurtunidade(id, docente);
                    IO.println("Oportunidade aprovada!");
                } catch (RuntimeException e) {
                    IO.println("Erro: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    oportunidadeService.rejeitarOportunidade(id, docente);
                    IO.println("Oportunidade rejeitada.");
                } catch (RuntimeException e) {
                    IO.println("Erro!");
                }
                break;
            case 0:
                return;
            default:
                IO.println("Opção inválida");
        }

    }
    static void verificarAproveitamentosTela(AproveitamentoService aproveitamentoService, Scanner scanner, Docente docente){
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarPendentes();

        aproveitamentos.forEach(a ->
                IO.println("[" + a.getId() + "] " + a.getDescricao() + " | " + a.getDiscente()));
        IO.println("─────────────────────────────");
        IO.println("Digite o ID da oportunidade (0 para voltar): ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            IO.println("ID invalido");
            return;
        }
        if (id == 0) return;

        IO.println("1 - Aprovar");
        IO.println("2 - Rejeitar");
        IO.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            IO.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                aproveitamentoService.aprovarAproveitamento(id, docente);
                IO.println("Aproveitamento aprovado com sucesso!");
                break;
            case 2:
                aproveitamentoService.rejeitarAproveitamento(id, docente);
                IO.println("Aproveitamento negado com sucesso!");
                break;
            case 3:
                IO.println("Voltando...");
                break;
            default:
                IO.println("Opção invalida");
                break;
        }
    }

    static void verificarGruposTela(GrupoService grupoService, Scanner scanner, Docente docente){
        int opt = 0;
        do {
            IO.println("Escolha uma opção: ");
            IO.println("1 - Ver Grupos");
            IO.println("2 - Criar um novo Grupo");
            IO.println("3 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                IO.println("Opção inválida.");
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
                    IO.println("Saindo...");
                    break;
            }
        }while (opt != 3);
    }

    static void verGruposTelas(GrupoService grupoService, Docente docente){
        List<Grupo> grupos = grupoService.listarPorDocente(docente);

        if (grupos.isEmpty()) {
            IO.println("Você não possui solicitações.");
            return;
        }

        IO.println("\nMEUS GRUPOS");
        grupos.forEach(g -> {
            IO.println("─────────────────────────────");
            IO.println("ID: "         + g.getId());
            IO.println("Nome: "  + g.getNome());
            IO.println("Descrição: "+ g.getDescricao());
            IO.println("Tipo: "      + g.getTipo());
        });
        IO.println("─────────────────────────────");
    }

    static void criarGruposTela(GrupoService grupoService, Scanner scanner, Docente docente){
        IO.println("Nome: ");
        String nome = scanner.nextLine();
        IO.println("Tipo: ");
        String tipo = scanner.nextLine();
        IO.println("Email: ");
        String email = scanner.nextLine();
        IO.println("Descricao: ");
        String descricao = scanner.nextLine();

        try {
            grupoService.criarGrupo(
                    nome,
                    tipo,
                    email,
                    descricao,
                    docente
            );
            IO.println("Grupo criado com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            IO.println("Erro ao criar grupo");
        }
    }

    static void verificarInscricoes(InscricaoServico inscricaoServico, Scanner scanner){
        List<Inscricao> inscricoes = inscricaoServico.listarPendente();

                inscricoes.forEach(i -> {
                    IO.println("─────────────────────────────");
                    IO.println("ID: "            + i.getId());
                    IO.println("Discente: "      + i.getDiscente());
                    IO.println("Oportunidade: "  + i.getOportunidade());
                    IO.println("Status: "        + i.getStatus());
                    IO.println("Motivação: "     + i.getMotivacao());

                });
        IO.println("─────────────────────────────");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            IO.println("ID invalido");
            return;
        }
        if (id == 0) return;

        IO.println("1 - Aprovar");
        IO.println("2 - Rejeitar");
        IO.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            IO.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                inscricaoServico.aprovar(id);
                IO.println("Inscrição aprovado com sucesso!");
                break;
            case 2:
                inscricaoServico.rejeitar(id);
                IO.println("Inscrição rejeitada com sucesso!");
                break;
            case 3:
                IO.println("Voltando...");
                break;
            default:
                IO.println("Opção invalida");
                break;
        };


    }
}