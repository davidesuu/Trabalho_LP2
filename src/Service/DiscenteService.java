package Service;

import Entity.Curso;
import Entity.Discente;

public class DiscenteService {
    //Depois faz
    public void mudarCurso(Discente discente, Curso curso){
        discente.setCurso(curso);
    }
}