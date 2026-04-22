package Telas;

import Entity.Aproveitamento;
import Entity.Discente;
import Entity.Oportunidade;
import Service.AproveitamentoService;
import Service.OportunidadeService;

import java.util.List;
import java.util.Scanner;

public class TelaDiscente {
    public static void mostarTela(OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService,
                           Discente discente){
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        IO.println("Tela do discente");

        do {
            IO.println("Escolha uma opção: ");
            IO.println("1 - Se inscrever em uma oportunidade");
            IO.println("2 - Ver solicitações");
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
                    verOportunidadesTela(oportunidadeService, scanner);        //Falta Inscriçao
                    break;
                case 2:
                    IO.println("Solicitaçoes");
                    AproveitamentoTela(aproveitamentoService, scanner, discente); //Feito
                    break;
                case 3:
                    IO.println("Certificados");     //apenas esse faltando
                    break;
                case 4:
                    IO.println("Saindo...");
                    break;
            }
        }while(opt != 4);

    }
    static void verOportunidadesTela(OportunidadeService oportunidadeService, Scanner scanner){
        List<Oportunidade> oportunidades = oportunidadeService.listarPublicadas();

        if (oportunidades.isEmpty()){
            IO.println("Nenhuma oportunidade disponivel");
        }

        oportunidades.forEach(o ->
                IO.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        IO.println("─────────────────────────────");

        IO.println("Digite o ID para se inscrever e 0 para voltar");
        int id = scanner.nextInt();

        if (id == 0) return;
        //Completar depois com inscricao;


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
}
