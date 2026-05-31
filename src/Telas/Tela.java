package Telas;

import Service.*;

import java.time.LocalDate;
import java.util.Scanner;

public abstract class Tela {

    protected final OportunidadeService oportunidadeService;
    protected final AproveitamentoService aproveitamentoService;
    protected final InscricaoService inscricaoService;
    protected final GrupoService grupoService;
    protected final UsuarioService usuarioService;
    protected final Scanner scanner;
    protected final PPCService ppcService;
    protected final LocalDate dataAtual;

    protected Tela(OportunidadeService oportunidadeService,
                   AproveitamentoService aproveitamentoService,
                   InscricaoService inscricaoService,
                   GrupoService grupoService,
                   UsuarioService usuarioService,
                   PPCService ppcService,
                   LocalDate dataAtual) {
        this.oportunidadeService   = oportunidadeService;
        this.aproveitamentoService = aproveitamentoService;
        this.inscricaoService      = inscricaoService;
        this.grupoService          = grupoService;
        this.usuarioService        = usuarioService;
        this.ppcService            = ppcService;
        this.scanner               = new Scanner(System.in);
        this.dataAtual = dataAtual;
    }

    public abstract void mostrarTela();

    protected int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return -1;
        }
    }

    protected long lerLong(String mensagem) {
        System.out.println(mensagem);
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return -1;
        }
    }

    protected int lerInt(String mensagem) {
        System.out.println(mensagem);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return -1;
        }
    }

    protected String lerTexto(String mensagem) {
        System.out.println(mensagem);
        return scanner.nextLine();
    }
}