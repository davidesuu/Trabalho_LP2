package Telas;

import Entity.Aproveitamento;
import Entity.Discente;
import Service.AproveitamentoService;
import Telas.TelaDiscente;

import java.util.List;
import java.util.Scanner;

public class TelaAproveitamento {

    public static void mostrarTela(AproveitamentoService service,
                                          Scanner scanner,
                                          Discente discente) {
        int opt = -1;
        do {
            System.out.println("1 - Ver aproveitamentos");
            System.out.println("2 - Solicitar aproveitamento");
            System.out.println("0 - Voltar");
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }
            switch (opt) {
                case 1 -> listar(service, discente);
                case 2 -> solicitar(service, scanner, discente);
            }
        } while (opt != 0);
    }

    private static void listar(AproveitamentoService service, Discente discente) {
        List<Aproveitamento> lista = service.listarPrivado(discente);
        if (lista.isEmpty()) { System.out.println("Nenhum aproveitamento."); return; }

        lista.forEach(a -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "          + a.getId());
            System.out.println("Descrição: "   + a.getDescricao());
            System.out.println("Instituição: " + a.getInstituicao());
            System.out.println("Horas: "       + a.getHoras());
            System.out.println("Status: "      + a.getStatus());
        });
        System.out.println("─────────────────────────────");
    }

    private static void solicitar(AproveitamentoService service,
                                  Scanner scanner,
                                  Discente discente) {
        System.out.println("Descrição: ");   String descricao    = scanner.nextLine();
        System.out.println("Instituição: "); String instituicao  = scanner.nextLine();
        System.out.println("Carga horária: ");
        int horas;
        try { horas = Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Valor inválido."); return; }

        System.out.println("Caminho do certificado: ");
        String path = scanner.nextLine();

        try {
            service.criarAproveitamento(discente, horas, descricao, instituicao, path);
            System.out.println("Solicitação enviada!");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}