package Telas;

import Entity.Usuario;
import Service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaAdmin extends Tela{

    public TelaAdmin(OportunidadeService oportunidadeService, AproveitamentoService aproveitamentoService, InscricaoService inscricaoService, GrupoService grupoService, UsuarioService usuarioService, PPCService ppcService, LocalDate dataAtual) {
        super(oportunidadeService, aproveitamentoService, inscricaoService, grupoService, usuarioService, ppcService, dataAtual);
    }

    @Override
    public void mostrarTela() {
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Tela do Admin");
            System.out.println("1 - Adicionar Docente ou Coordenador");
            System.out.println("2 - Remover Docente ou Coordenador");
            System.out.println("0 - Voltar");
            opt = Integer.parseInt(scanner.nextLine());

            switch (opt) {
                case 1:
                    adicionarUsuario(usuarioService, scanner);
                    break;
                case 2:
                    removerUsuario(usuarioService, scanner);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }while (opt != 0);
    }


    static void adicionarUsuario(UsuarioService service, Scanner sc){

        System.out.println("===== ADICIONAR USUÁRIO =====");

        System.out.println("1 - Docente");
        System.out.println("2 - Coordenador");

        int tipo = Integer.parseInt(sc.nextLine());

        System.out.println("Digite o nome:");
        String nome = sc.nextLine();

        System.out.println("Digite o departamento:");
        String departamento = sc.nextLine();

        System.out.println("Digite o SIAPE:");
        String siape = sc.nextLine();

        System.out.println("Digite o email:");
        String email = sc.nextLine();

        System.out.println("Digite a senha:");
        String senha = sc.nextLine();

        switch (tipo){

            case 1:

                service.cadastrarDocente(
                        nome,
                        email,
                        senha,
                        siape,
                        departamento
                );

                System.out.println("Docente cadastrado com sucesso.");
                break;

            case 2:

                service.cadastrarCoordenador(
                        nome,
                        email,
                        senha,
                        siape,
                        departamento
                );

                System.out.println("Coordenador cadastrado com sucesso.");
                break;

            default:
                System.out.println("Tipo inválido.");
        }
    }

    static void removerUsuario(UsuarioService service, Scanner sc){
        List<Usuario> usuarios = service.listarTodos();

        if(usuarios.isEmpty()){
            System.out.println("Nenhum usuario encontrado");
        }

        System.out.println("========= Usuarios =========");
        int i = 0;
        for(Usuario u:  usuarios){
            System.out.println(
                    (i + 1) + " - " +
                            u.getNome() +
                            " | " +
                            u.getEmail() +
                            " | Ativo: " +
                            u.getStatus()
            );
            i++;
        }

        System.out.println("Selecione um usuário:");
        int escolha = Integer.parseInt(sc.nextLine());

        if (escolha < 1 || escolha > usuarios.size()) {
            System.out.println("Usuário inválido.");
            return;
        }

        Usuario usuarioSelecionado = usuarios.get(escolha - 1);

        System.out.println("\nUsuário selecionado:");
        System.out.println(usuarioSelecionado);

        System.out.println("1 - Ativar");
        System.out.println("2 - Desativar");

        int acao = Integer.parseInt(sc.nextLine());

        switch (acao) {

            case 1:
                usuarioSelecionado.setAtivo(true);
                service.atualizarUsuario(usuarioSelecionado);
                System.out.println("Usuário ativado.");
                break;

            case 2:
                usuarioSelecionado.setAtivo(false);
                service.atualizarUsuario(usuarioSelecionado);
                System.out.println("Usuário desativado.");
                break;

            default:
                System.out.println("Opção inválida.");
        }


    }
}
