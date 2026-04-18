package Service;

import Entity.Oportunidade;
import Entity.Usuario;
import Repository.impl.OportunidadeRepositoryImpl;

import java.util.Scanner;

public class UsuarioService {
    public void mudarSenha (Usuario usuario, String nova_senha){
        usuario.setSenha(nova_senha);
    }
    public void selecionarOportunidade(OportunidadeRepositoryImpl repository){
        repository.mostrarOportunidades();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione o id da oportunidade: ");
        Long s = scanner.nextLong();
        Oportunidade c = repository.buscaPorId(s);
        //Inscricao inscricao = new Inscricao(c);
    }
}
