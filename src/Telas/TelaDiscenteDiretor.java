package Telas;

import Entity.*;
import Service.AproveitamentoService;
import Service.InscricaoServico;
import Service.OportunidadeService;
import Enum.*;
import java.util.List;
import java.util.Scanner;

public class TelaDiscenteDiretor {
    public static void mostarTela(OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService, InscricaoServico inscricaoServico, DiscenteDiretor diretor) {
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        IO.println("Tela do discente");

        do {
            IO.println("Escolha uma opção: ");
            IO.println("1 - Se inscrever em uma oportunidade");
            IO.println("2 - Ver solicitações");
            IO.println("3 - Ver Certificados");
            IO.println("4 - Nova Iniciativa");  //feito
            IO.println("5 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                IO.println("Opção inválida.");
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
                    IO.println("Certificados");     //Criar Certifficado e gerar qr code do certificado
                    break;
                case 4:
                    TelaCriarOportunidade(oportunidadeService, scanner, diretor); //Feito
                    break;
                case 5:
                    IO.println("Saindo...");
                    break;
                default:
                    IO.println("Opção inválida.");
            }
        } while (opt != 5);

    }

    static void verOportunidadesTela(OportunidadeService oportunidadeService, InscricaoServico inscricaoService, Scanner scanner, DiscenteDiretor discenteDiretor) {
        List<Oportunidade> oportunidades = oportunidadeService.listarPublicadas();
        Long id;
        //Podia fazer so uma tela que cada Tela depois herda
        if (oportunidades.isEmpty()) {
            IO.println("Nenhuma oportunidade disponivel");
        }

        oportunidades.forEach(o ->
                IO.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        IO.println("─────────────────────────────");

        IO.println("Digite o ID para se inscrever e 0 para voltar");
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("ID inválida.");
            return;
        }

        if (id == 0) return;
        Oportunidade o = oportunidadeService.buscar(id);
        try{
            criarInscricaoTela(inscricaoService, scanner, o, discenteDiretor);
            IO.println("Inscrição com sucesso");
        } catch (RuntimeException e){
            IO.println("Erro ao se Inscrever");
        }

    }

    static void TelaCriarOportunidade(OportunidadeService oportunidadeService,
                                      Scanner scanner,
                                      DiscenteDiretor diretor) {
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
                    modalidade, cargaHoraria, vagas, diretor);
            IO.println("Oportunidade criada com sucesso! Aguardando aprovação do docente.");
        } catch (RuntimeException e) {
            IO.println("Erro ao criar oportunidade");
        }
    }

    static void AproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        int opt = 0;
        do {
            IO.println("Escolha uma opção: ");
            IO.println("1 - Ver aproveitamentos");
            IO.println("2 - Solicitar aproveitamento");
            IO.println("3 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                IO.println("Opção inválida.");
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
                    IO.println("Saindo...");
                    break;
            }
        } while (opt != 3);
    }

    static void verAproveitamentosTelas(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        List<Aproveitamento> solicitacoes = aproveitamentoService.listarPrivado(diretor);

        if (solicitacoes.isEmpty()) {
            IO.println("Você não possui solicitações.");
            return;
        }

        IO.println("\nMINHAS SOLICITAÇÕES");
        solicitacoes.forEach(a -> {
            IO.println("─────────────────────────────");
            IO.println("ID: " + a.getId());
            IO.println("Descrição: " + a.getDescricao());
            IO.println("Instituição: " + a.getInstituicao());
            IO.println("Horas: " + a.getHoras());
            IO.println("Status: " + a.getStatus());
        });
        IO.println("─────────────────────────────");
    }

    static void solicitarAproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, DiscenteDiretor diretor) {
        IO.println("Descrição da atividade: ");
        String descricao = scanner.nextLine();

        IO.println("Instituição: ");
        String instituicao = scanner.nextLine();

        IO.println("Carga horária: ");
        int horas;
        try {
            horas = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("Carga horária inválida, operação cancelada.");
            return;
        }

        IO.println("Caminho do certificado (ex: /arquivos/cert.pdf): ");
        String certificadoPath = scanner.nextLine();

        try {
            aproveitamentoService.criarAproveitamento(
                    diretor,
                    horas,
                    descricao,
                    instituicao,
                    certificadoPath
            );
            IO.println("Solicitação enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            IO.println("Erro ao solicitar aproveitamento");
        }

    }

    static void verInscricoes(InscricaoServico inscricaoServico, DiscenteDiretor discenteDiretor) {
        List<Inscricao> inscricoes = inscricaoServico.listarDiscente(discenteDiretor);
        if (inscricoes.isEmpty()) {
            IO.println("Você não possui inscrições");
            return;
        }

        IO.println("\nMINHAS INSCRIÇÕES");
        inscricoes.forEach(i -> {
            IO.println("─────────────────────────────");
            IO.println("ID: " + i.getId());
            IO.println("Oportunidade: " + i.getOportunidade());
            IO.println("Status: " + i.getStatus());
            IO.println("Motivação: " + i.getMotivacao());

        });
        IO.println("─────────────────────────────");
    }

    static void criarInscricaoTela(InscricaoServico inscricaoServico, Scanner scanner, Oportunidade oportunidade, DiscenteDiretor discenteDiretor) {
        IO.println("Motivação: ");
        String motivacao = scanner.nextLine();

        try {
            inscricaoServico.criarInscricao(
                    oportunidade,
                    discenteDiretor,
                    motivacao
            );
            IO.println("Inscrição enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            IO.println("Erro ao solicitar aproveitamento");
        }

    }
}