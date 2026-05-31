package Telas;

import Entity.Aproveitamento;
import Entity.Discente;
import Entity.Inscricao;
import Entity.Oportunidade;
import Service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class TelaDiscente extends Tela{
    protected final Discente discente;

    public TelaDiscente(OportunidadeService oportunidadeService,
                        AproveitamentoService aproveitamentoService,
                        InscricaoService inscricaoService,
                        GrupoService grupoService,
                        UsuarioService usuarioService,
                        Discente discente, PPCService ppcService, LocalDate dataAtual) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService, usuarioService, ppcService, dataAtual);
        this.discente = discente;
    }

    @Override
    public void mostrarTela(){
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tela do discente");

        do {
            oportunidadeService.finalizarOportunidades();
            int horas_compridas = this.discente.getVinculo().getChTotalCumprida();
            int total = ppcService.buscarPorId(this.discente.getVinculo().getPpcId()).getCargaHorariaTotal();

            double progresso = (double)horas_compridas/total;
            int tamanho_barra = 20;
            int preenchido = (int)(progresso * tamanho_barra);

            String barra = "#".repeat(preenchido) + "-".repeat(tamanho_barra - preenchido);

            System.out.println("[" + barra + "}" + horas_compridas + "/" + total + "h(" + String.format("%.1f", progresso * 100) + "%)");

            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Se inscrever em uma oportunidade");
            System.out.println("2 - Ver inscrições");
            System.out.println("3 - Ver Certificados");
            System.out.println("4 - Cancelar inscrição");
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
                    TelaAproveitamento.mostrarTela(aproveitamentoService, scanner, discente);
                    break;
                case 4:
                    cancelarInscricao(inscricaoService, scanner, discente);
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

    static void cancelarInscricao(InscricaoService inscricaoService, Scanner scanner, Discente discente){
        List<Inscricao> inscricoes = inscricaoService.listarDiscente(discente);
        List<Long> keyset = inscricaoService.ListarIndice(inscricoes);

        if (inscricoes.isEmpty()) {
            System.out.println("Você não possui inscrições");
            return;
        }

        inscricoes.forEach(o ->
                System.out.println("[" + o.getId() + "] " + o.getOportunidade() + " | " + o.getStatus()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        if (id == 0) return;

        Inscricao i = inscricaoService.buscar(keyset.get(id-1));

        inscricaoService.cancelarInscricao(i);
    }
}