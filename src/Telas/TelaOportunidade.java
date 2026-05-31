package Telas;

import Entity.*;
import Service.InscricaoService;
import Service.OportunidadeService;
import Enum.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class TelaOportunidade{

    static void verOportunidades(OportunidadeService oportunidadeService, InscricaoService inscricaoService, Scanner scanner, Discente discente){
        List<Oportunidade> oportunidades = oportunidadeService.listarOportunidadesPossiveis(discente);
        List<Long> keyset = oportunidadeService.ListarIndice(oportunidades);
        Integer id;
        if (oportunidades.isEmpty()){
            System.out.println("Nenhuma Oportunidade disponivel");
            return;
        }

        System.out.println("Digite o ID para se inscrever e 0 para voltar");
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválida.");
            return;
        }

        if (id == 0) return;
        Oportunidade o = oportunidadeService.buscar(keyset.get(id-1));
        try{
            criarInscricao(inscricaoService, scanner, o, discente);
        } catch (RuntimeException e){
            System.out.println("Erro ao se Inscrever");
        }
    }

    static void criarInscricao(InscricaoService inscricaoService, Scanner scanner, Oportunidade oportunidade, Discente discente){
        System.out.println("Motivação: ");
        String motivacao = scanner.nextLine();

        try {
            inscricaoService.criarInscricao(
                    oportunidade,
                    discente,
                    motivacao
            );
            System.out.println("Inscrição enviada com sucesso! Aguardando avaliação.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar aproveitamento");
        }

    }

    static void CriarOportunidade(OportunidadeService oportunidadeService,
                                      Scanner scanner,
                                          Usuario usuario, LocalDate dataAtual) {
        System.out.println("\nCRIAR OPORTUNIDADE");

        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.println("Tipo (1-PROJETO, 2-CURSO, 3-EVENTO, 4-OFICINA): ");
        TipoOportunidade tipo;
        try {
            int optTipo = Integer.parseInt(scanner.nextLine());
            tipo = switch (optTipo) {
                case 1 -> TipoOportunidade.PROJETO;
                case 2 -> TipoOportunidade.CURSO;
                case 3 -> TipoOportunidade.EVENTO;
                case 4 -> TipoOportunidade.OFICINA;
                default -> throw new IllegalArgumentException("Tipo inválido.");
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo inválido, operação cancelada.");
            return;
        }

        System.out.println("Modalidade (1-PRESENCIAL, 2-REMOTO, 3-HIBRIDO): ");
        Modalidade modalidade;
        try {
            int optMod = Integer.parseInt(scanner.nextLine());
            modalidade = switch (optMod) {
                case 1 -> Modalidade.PRESENCIAL;
                case 2 -> Modalidade.REMOTO;
                case 3 -> Modalidade.HIBRIDO;
                default -> throw new IllegalArgumentException("Modalidade inválida.");
            };
        } catch (IllegalArgumentException e) {
            System.out.println("Modalidade inválida, operação cancelada.");
            return;
        }

        System.out.println("Carga horária: ");
        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Carga horária inválida, operação cancelada.");
            return;
        }

        System.out.println("Vagas: ");
        int vagas;
        try {
            vagas = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número de vagas inválido, operação cancelada.");
            return;
        }


        System.out.println("Data de início (dd/MM/yyyy): ");
        LocalDate dataInicio;
        try {
            String dataInicioStr = scanner.nextLine();
            dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (dataInicio.isBefore(dataAtual)) {
                System.out.println("Data de início não pode ser no passado.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Data inválida, operação cancelada.");
            return;
        }

        System.out.println("Data de fim (dd/MM/yyyy): ");
        LocalDate dataFim;
        try {
            String dataFimStr = scanner.nextLine();
            dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (dataFim.isBefore(dataInicio)) {
                System.out.println("Data de fim não pode ser antes da data de início.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Data inválida, operação cancelada.");
            return;
        }

        System.out.println("Período: " + dataInicio + " até " + dataFim);
        try {
            oportunidadeService.criarOportunidade(titulo, descricao, tipo,
                    modalidade, cargaHoraria, vagas, dataInicio, dataFim, usuario);
            System.out.println("Oportunidade criada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao criar oportunidade");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
