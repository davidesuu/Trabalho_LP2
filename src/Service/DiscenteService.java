package Service;

import Entity.Curso;
import Entity.Discente;
import Repository.UsuarioRepository;
import Repository.impl.OportunidadeRepositoryImpl;
import Repository.impl.UsuarioRepositoryImpl;

import java.util.Optional;

public class DiscenteService {
    //Depois faz

    public void mudarCurso(Discente discente, Curso curso){
        discente.setCurso(curso);
    }
}