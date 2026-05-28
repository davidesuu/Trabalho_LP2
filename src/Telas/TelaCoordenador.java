package Telas;

import Entity.*;
import Service.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TelaCoordenador extends TelaDocente{
    protected final Coordenador coordenador;

    public TelaCoordenador(OportunidadeService oportunidadeService,
                           AproveitamentoService aproveitamentoService,
                           InscricaoService inscricaoService,
                           GrupoService grupoService, UsuarioService usuarioService, Coordenador coordenador){
        super(oportunidadeService,
              aproveitamentoService,
              inscricaoService,
              grupoService, usuarioService, coordenador);
        this.coordenador = coordenador;
    }
   @Override
    public void mostrarTela (){
        int opt = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tela do Coordenador");

        do {
            System.out.println("\nEscolha uma opção: ");
            System.out.println("1 - Criar uma oportunidade");
            System.out.println("2 - Verificar novos planos de Atividades");
            System.out.println("3 - Verificar aproveitamentos");
            System.out.println("4 - Verificar inscrição de discentes em Oportunidades");
            System.out.println("5 - Criar Grupos");
            System.out.println("0 - Sair");

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opt) {
                case 1:
                    TelaOportunidade.CriarOportunidade(oportunidadeService, scanner, coordenador); // Feito
                    break;
                case 2:
                    aprovarOportunidades(oportunidadeService, scanner, coordenador);
                    // Falta mudar pra generalizar tbm, criar listar por enum
                    break;
                case 3:
                    verificarAproveitamentos(aproveitamentoService, scanner, coordenador); //Feito
                    break;
                case 4:
                    verificarInscricoes(inscricaoService, scanner);  //Feito
                    break;
                case 5:
                    criarGruposTela(grupoService, usuarioService, scanner, coordenador); //Feito
                    break;
                case 0:
                    System.out.print("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida.");
                    break;
            }
        } while (opt != 0);
    }


    static void aprovarOportunidades(OportunidadeService oportunidadeService,
                                     Scanner scanner,
                                     Docente docente) {
        List<Oportunidade> oportunidades = oportunidadeService.listarPendentes();

        if (oportunidades.isEmpty()) {
            System.out.println("Nenhuma oportunidade pendente.");
            return;
        }

        oportunidades.forEach(o ->
                System.out.println("[" + o.getId() + "] " + o.getTitulo() + " | " + o.getTipo()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine()); // ← nextLine + parse, sem nextLong
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        if (id == 0) return;

        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");

        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }

        switch (opc) {
            case 1:
                try {
                    oportunidadeService.publicarOpurtunidade(id, docente);
                    System.out.println("Oportunidade aprovada!");
                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    oportunidadeService.rejeitarOportunidade(id, docente);
                    System.out.println("Oportunidade rejeitada.");
                } catch (RuntimeException | IOException e) {
                    System.out.println("Erro!");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida");
        }

    }
    static void verificarAproveitamentos(AproveitamentoService aproveitamentoService, Scanner scanner, Coordenador coordenador){
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarPendentes();

        aproveitamentos.forEach(a ->
                System.out.println("[" + a.getId() + "] " + a.getDescricao() + " | " + a.getDiscente()));
        System.out.println("─────────────────────────────");
        System.out.println("Digite o ID da oportunidade (0 para voltar): ");
        Long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID invalido");
            return;
        }
        if (id == 0) return;

        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine()); // ← mesmo padrão
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                aproveitamentoService.aprovarAproveitamento(id, coordenador);
                System.out.println("Aproveitamento aprovado com sucesso!");
                break;
            case 2:
                aproveitamentoService.rejeitarAproveitamento(id, coordenador);
                System.out.println("Aproveitamento negado com sucesso!");
                break;
            case 3:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção invalida");
                break;
        }
    }

    static void verificarInscricoes(InscricaoService inscricaoService, Scanner scanner){
        List<Inscricao> inscricoes = inscricaoService.listarPendente();

        inscricoes.forEach(i -> {
            System.out.println("─────────────────────────────");
            System.out.println("ID: "            + i.getId());
            System.out.println("Discente: "      + i.getDiscente());
            System.out.println("Oportunidade: "  + i.getOportunidade());
            System.out.println("Status: "        + i.getStatus());
            System.out.println("Motivação: "     + i.getMotivacao());

        });
        System.out.println("─────────────────────────────");
        Long id;

        try{
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID invalido");
            return;
        }
        if (id == 0) return;

        System.out.println("1 - Aprovar");
        System.out.println("2 - Rejeitar");
        System.out.println("0 - Voltar");
        int opc;
        try {
            opc = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opc){
            case 1:
                inscricaoService.aprovar(id);
                System.out.println("Inscrição aprovado com sucesso!");
                break;
            case 2:
                inscricaoService.rejeitar(id);
                System.out.println("Inscrição rejeitada com sucesso!");
                break;
            case 3:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção invalida");
                break;
        };

    }

    static void criarGruposTela(GrupoService grupoService, UsuarioService usuarioService, Scanner scanner, Coordenador coordenador){
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Descricao: ");
        String descricao = scanner.nextLine();
        System.out.println("SIAPE do prof. responsavel: ");
        String siape = scanner.nextLine();
        try {

            Docente doc = usuarioService.buscarSiape(siape);

            grupoService.criarGrupo(
                    nome,
                    email,
                    descricao,
                    doc,
                    coordenador
            );

            System.out.println("Grupo criado com sucesso!");

        } catch (RuntimeException e) {

            e.printStackTrace();

        }
    }
}
