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
        System.out.println("Tela do discente");

        do {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Se inscrever em uma oportunidade");
            System.out.println("2 - Ver inscrições");
            System.out.println("3 - Ver Certificados");
            System.out.println("0 - Sair");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
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
                    System.out.println("Certificados");     //apenas esse faltando
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        } while(opt != 0);

    }
    static void verOportunidadesTela(OportunidadeService oportunidadeService, InscricaoServico inscricaoService, Scanner scanner, Discente discente){
        List<Oportunidade> oportunidades = oportunidadeService.listarPublicadas();
        Long id;
        if (oportunidades.isEmpty()){
            System.out.println("Nenhuma inscrição no momento");
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
            criarInscricaoTela(inscricaoService, scanner, o, discente);
            System.out.println("Inscrição com sucesso");
        } catch (RuntimeException e){
            System.out.println("Erro ao se Inscrever");
        }
    }

    static void AproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
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
            switch (opt){
                case 1:
                    verAproveitamentosTelas(aproveitamentoService, scanner, discente);
                    break;
                case 2:
                    solicitarAproveitamentoTela(aproveitamentoService, scanner, discente);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
            }
        }while (opt != 3);
    }

    static void verAproveitamentosTelas(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
        List<Aproveitamento> solicitacoes = aproveitamentoService.listarPrivado(discente);

        if (solicitacoes.isEmpty()) {
            System.out.println("Você não possui solicitações.");
            return;
        }

        System.out.println("\nMINHAS SOLICITAÇÕES");
        solicitacoes.forEach(a -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "         + a.getId());
            System.out.println("Descrição: "  + a.getDescricao());
            System.out.println("Instituição: "+ a.getInstituicao());
            System.out.println("Horas: "      + a.getHoras());
            System.out.println("Status: "     + a.getStatus());
        });
        System.out.println("─────────────────────────────");
    }

    static void solicitarAproveitamentoTela(AproveitamentoService aproveitamentoService, Scanner scanner, Discente discente){
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
                    discente,
                    horas,
                    descricao,
                    instituicao,
                    certificadoPath
            );
            System.out.println("Solicitação enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar aproveitamento: " + e.getMessage());
        }

    }

    static void verInscricoes(InscricaoServico inscricaoServico, Discente discente){
        List<Inscricao> inscricoes = inscricaoServico.listarDiscente(discente);
        if (inscricoes.isEmpty()) {
            System.out.println("Você não possui inscrições");
            return;
        }

        System.out.println("\nMINHAS INSCRIÇÕES");
        inscricoes.forEach(i -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "            + i.getId());
            System.out.println("Oportunidade: "  + i.getOportunidade());
            System.out.println("Status: "        + i.getStatus());
            System.out.println("Motivação: "     + i.getMotivacao());

        });
        System.out.println("─────────────────────────────");
    }

    static void criarInscricaoTela(InscricaoServico inscricaoServico, Scanner scanner, Oportunidade oportunidade, Discente discente){
        System.out.println("Motivação: ");
        String motivacao = scanner.nextLine();

        try {
            inscricaoServico.criarInscricao(
                    oportunidade,
                    discente,
                    motivacao
            );
            System.out.println("Inscrição enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar aproveitamento");
        }

    }
}
