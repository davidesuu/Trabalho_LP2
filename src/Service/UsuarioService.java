package Service;

import Entity.Inscricao;
import Entity.Oportunidade;
import Entity.Usuario;
import Repository.InscricaoRepository;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.InscricoesRepositoryImpl;
import Enum.Status;

import java.time.LocalDate;
import java.util.Scanner;

public class UsuarioService {
    public void mudarSenha (Usuario usuario, String nova_senha){
        usuario.setSenha(nova_senha);
    }
    public void selecionarOportunidade(OportunidadeRepositoryImpl repository, InscricoesRepositoryImpl inscricoes){
        //tirar o inscricaoRepositoryImpl depois e usar o service
        repository.mostrarOportunidades();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione o id da oportunidade: ");
        Long s = scanner.nextLong();
        Oportunidade c = repository.buscaPorId(s);
        Inscricao inscricao = new Inscricao(c, Status.PENDENTE, "motivacao", LocalDate.now());
        inscricoes.salvar(inscricao);
    }
}
