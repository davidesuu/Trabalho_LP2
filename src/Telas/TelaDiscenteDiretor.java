package Telas;

import Entity.*;
import Service.AproveitamentoService;
import Service.InscricaoServico;
import Service.OportunidadeService;
import Enum.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TelaDiscenteDiretor {
    public static void mostarTela(OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService, InscricaoServico inscricaoServico, DiscenteDiretor diretor) {
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tela do discente");

        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Se inscrever em uma oportunidade");
            System.out.println("2 - Ver solicitações");
            System.out.println("3 - Ver Certificados");
            System.out.println("4 - Nova Iniciativa");  //feito
            System.out.println("0 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }
            switch (opt) {
                case 1:
                    verOportunidadesTela(oportunidadeService, inscricaoServico, scanner, diretor); //Feito
                    break;
                case 2:
                    AproveitamentoTela(aproveitamentoService, scanner, diretor); //Feito
                    break;
                case 3:
                    System.out.println("Certificados");     //Criar Certifficado e gerar qr code do certificado
                    break;
                case 4:
                    TelaCriarOportunidade(oportunidadeService, scanner, diretor); //Feito
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opt != 0);

    }

    static void verOportunidadesTela(OportunidadeService oportunidadeService, InscricaoServico inscricaoService, Scanner scanner, DiscenteDiretor discenteDiretor) {
        List<Oportunidade> oportunidades = oportunidadeService.listarPublicadas();
        Long id;
        //Podia fazer so uma tela que cada Tela depois herda
        if (oportunidades.isEmpty()) {
            System.out.println("Nenhuma oportunidade disponivel");
            return;
        }

        oportunidades.forEach(o ->
                System.out.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        System.out.println("─────────────────────────────");

        System.out.println("Digite o ID para se inscrever e 0 para voltar");
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválida.");
            return;
        }

        if (id == 0) return;
        Oportunidade o = oportunidadeService.buscar(id);
        try{
            criarInscricaoTela(inscricaoService, scanner, o, discenteDiretor);
            System.out.println("Inscrição com sucesso");
        } catch (RuntimeException e){
            System.out.println("Erro ao se Inscrever");
        }

    }

    static void TelaCriarOportunidade(OportunidadeService oportunidadeService,
                                      Scanner scanner,
                                      DiscenteDiretor diretor) {
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
                    modalidade, cargaHoraria, vagas, diretor);
            System.out.println("Oportunidade criada com sucesso! Aguardando aprovação do docente.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao criar oportunidade");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void AproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        int opt = 0;
        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Ver aproveitamentos");
            System.out.println("2 - Solicitar aproveitamento");
            System.out.println("3 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }
            switch (opt) {
                case 1:
                    verAproveitamentosTelas(aproveitamentoService, scanner, diretor);
                    break;
                case 2:
                    solicitarAproveitamentoTela(aproveitamentoService, scanner, diretor);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
            }
        } while (opt != 3);
    }

    static void verAproveitamentosTelas(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        List<Aproveitamento> solicitacoes = aproveitamentoService.listarPrivado(diretor);

        if (solicitacoes.isEmpty()) {
            System.out.println("Você não possui solicitações.");
            return;
        }

        System.out.println("\nMINHAS SOLICITAÇÕES");
        solicitacoes.forEach(a -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: " + a.getId());
            System.out.println("Descrição: " + a.getDescricao());
            System.out.println("Instituição: " + a.getInstituicao());
            System.out.println("Horas: " + a.getHoras());
            System.out.println("Status: " + a.getStatus());
        });
        System.out.println("─────────────────────────────");
    }

    static void solicitarAproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        System.out.println("Descrição da atividade: ");
        String descricao = scanner.nextLine();

        System.out.println("Instituição: ");
        String instituicao = scanner.nextLine();

        System.out.println("Carga horária: ");
        int horas;
        try {
            horas = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Carga horária inválida, operação cancelada.");
            return;
        }

        System.out.println("Caminho do certificado (ex: /arquivos/cert.pdf): ");
        String certificadoPath = scanner.nextLine();

        try {
            aproveitamentoService.criarAproveitamento(
                    diretor,
                    horas,
                    descricao,
                    instituicao,
                    certificadoPath
            );
            System.out.println("Solicitação enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar aproveitamento");
        }

    }

    static void verInscricoes(InscricaoServico inscricaoServico, DiscenteDiretor discenteDiretor) {
        List<Inscricao> inscricoes = inscricaoServico.listarDiscente(discenteDiretor);
        if (inscricoes.isEmpty()) {
            System.out.println("Você não possui inscrições");
            return;
        }

        System.out.println("\nMINHAS INSCRIÇÕES");
        inscricoes.forEach(i -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: " + i.getId());
            System.out.println("Oportunidade: " + i.getOportunidade());
            System.out.println("Status: " + i.getStatus());
            System.out.println("Motivação: " + i.getMotivacao());

        });
        System.out.println("─────────────────────────────");
    }

    static void criarInscricaoTela(InscricaoServico inscricaoServico, Scanner scanner, Oportunidade oportunidade, DiscenteDiretor discenteDiretor) {
        System.out.println("Motivação: ");
        String motivacao = scanner.nextLine();

        try {
            inscricaoServico.criarInscricao(
                    oportunidade,
                    discenteDiretor,
                    motivacao
            );
            System.out.println("Inscrição enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar aproveitamento");
        }

    }
}