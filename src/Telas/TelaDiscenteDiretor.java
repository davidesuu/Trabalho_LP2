package Telas;

import Entity.*;
import Service.AproveitamentoService;
import Service.GrupoService;
import Service.InscricaoService;
import Service.OportunidadeService;

import java.util.List;
import java.util.Scanner;

public class TelaDiscenteDiretor extends TelaDiscente{
    protected final DiscenteDiretor diretor;

    public TelaDiscenteDiretor(OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService,
                               InscricaoService inscricaoService,
                               GrupoService grupoService, DiscenteDiretor diretor) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService, diretor);
        this.diretor = diretor;
    }

    @Override
    public void mostrarTela() {
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
                    TelaOportunidade.verOportunidades(oportunidadeService, inscricaoService, scanner, diretor); //Feito
                    break;
                case 2:
                    TelaAproveitamento.mostrarTela(aproveitamentoService, scanner, diretor); //Feito
                    break;
                case 3:
                    System.out.println("Certificados");     //Criar Certifficado e gerar qr code do certificado
                    break;
                case 4:
                    TelaOportunidade.CriarOportunidade(oportunidadeService, scanner, diretor); //Feito
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opt != 0);

    }

}