package Telas;

import Entity.Aproveitamento;
import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;
import Service.AproveitamentoService;
import Service.InscricaoServico;
import Service.OportunidadeService;

import java.util.List;
import java.util.Scanner;

public class TelaDiscente {
    public static void mostarTela(OportunidadeService oportunidadeService,
                                  AproveitamentoService aproveitamentoService,
                                  InscricaoServico inscricaoService,
                                  Discente discente){
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        IO.println("Tela do discente");

        do {
            IO.println("Escolha uma opção: ");
            IO.println("1 - Se inscrever em uma oportunidade");
            IO.println("2 - Ver inscrições");
            IO.println("3 - Ver Certificados");
            IO.println("4 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                IO.println("Opção inválida.");
                continue;
            }
            switch (opt){
                case 1:
                    verOportunidadesTela(oportunidadeService, inscricaoService, scanner, discente);        //Falta Inscriçao
                    break;
                case 2:
                    verInscricoes(inscricaoService, discente);
                    break;
                case 3:
                    AproveitamentoTela(aproveitamentoService, scanner, discente); //Feito
                    break;
                case 4:
                    IO.println("Certificados");     //apenas esse faltando
                    break;
                case 5:
                    IO.println("Saindo...");
                    break;
            }
        }while(opt != 4);

    }
    static void verOportunidadesTela(OportunidadeService oportunidadeService, InscricaoServico inscricaoService, Scanner scanner, Discente discente){
        List<Oportunidade> oportunidades = oportunidadeService.listarPublicadas();
        Long id;
        if (oportunidades.isEmpty()){
            IO.println("Nenhuma inscrição no momento");
            return;
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
            criarInscricaoTela(inscricaoService, scanner, o, discente);
            IO.println("Inscrição com sucesso");
        } catch (RuntimeException e){
            IO.println("Erro ao se Inscrever");
        }
    }

    static void AproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
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
            switch (opt){
                case 1:
                    verAproveitamentosTelas(aproveitamentoService, scanner, discente);
                    break;
                case 2:
                    solicitarAproveitamentoTela(aproveitamentoService, scanner, discente);
                    break;
                case 3:
                    IO.println("Saindo...");
                    break;
            }
        }while (opt != 3);
    }

    static void verAproveitamentosTelas(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
        List<Aproveitamento> solicitacoes = aproveitamentoService.listarPrivado(discente);

        if (solicitacoes.isEmpty()) {
            IO.println("Você não possui solicitações.");
            return;
        }

        IO.println("\nMINHAS SOLICITAÇÕES");
        solicitacoes.forEach(a -> {
            IO.println("─────────────────────────────");
            IO.println("ID: "         + a.getId());
            IO.println("Descrição: "  + a.getDescricao());
            IO.println("Instituição: "+ a.getInstituicao());
            IO.println("Horas: "      + a.getHoras());
            IO.println("Status: "     + a.getStatus());
        });
        IO.println("─────────────────────────────");
    }

    static void solicitarAproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
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
                    discente,
                    horas,
                    descricao,
                    instituicao,
                    certificadoPath
            );
            IO.println("Solicitação enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            IO.println("Erro ao solicitar aproveitamento: " + e.getMessage());
        }

    }

    static void verInscricoes(InscricaoServico inscricaoServico, Discente discente){
        List<Inscricao> inscricoes = inscricaoServico.listarDiscente(discente);
        if (inscricoes.isEmpty()) {
            IO.println("Você não possui inscrições");
            return;
        }

        IO.println("\nMINHAS INSCRIÇÕES");
        inscricoes.forEach(i -> {
            IO.println("─────────────────────────────");
            IO.println("ID: "            + i.getId());
            IO.println("Oportunidade: "  + i.getOportunidade());
            IO.println("Status: "        + i.getStatus());
            IO.println("Motivação: "     + i.getMotivacao());

        });
        IO.println("─────────────────────────────");
    }

    static void criarInscricaoTela(InscricaoServico inscricaoServico, Scanner scanner, Oportunidade oportunidade, Discente discente){
        IO.println("Motivação: ");
        String motivacao = scanner.nextLine();

        try {
            inscricaoServico.criarInscricao(
                    oportunidade,
                    discente,
                    motivacao
            );
            IO.println("Inscrição enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            IO.println("Erro ao solicitar aproveitamento");
        }

    }
}
