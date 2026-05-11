package Telas;

import Entity.Aproveitamento;
import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;
import Service.AproveitamentoService;
import Service.GrupoService;
import Service.InscricaoService;
import Service.OportunidadeService;

import java.util.List;
import java.util.Scanner;


public class TelaDiscente extends Tela{
    protected final Discente discente;

    public TelaDiscente(OportunidadeService oportunidadeService,
                        AproveitamentoService aproveitamentoService,
                        InscricaoService inscricaoService,
                        GrupoService grupoService,
                        Discente discente) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService);
        this.discente = discente;
    }

    @Override
    public void mostrarTela(){
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
                    TelaOportunidade.verOportunidades(oportunidadeService, this.inscricaoService, scanner, discente);
                    //Falta Inscriçao
                    break;
                case 2:
                    verInscricoes(this.inscricaoService, discente);
                    break;
                case 3:
                    TelaAproveitamento.mostrarTela(aproveitamentoService, scanner, discente); //Feito
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

    static void verInscricoes(InscricaoService inscricaoService, Discente discente){
        List<Inscricao> inscricoes = inscricaoService.listarDiscente(discente);
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

}