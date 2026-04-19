package Service;

import Entity.Curso;
import Entity.Discente;
import Enum.Status;

import java.util.ArrayList;
import java.util.List;

public class CursoService {
    // tentando fazer a bostinha dos metodos
    public void atualizarPPC (Curso curso, Integer horas, String versao){
        curso.setCarga_horaria(horas);
        curso.setVersao_ppc(versao);
    }

    public List<Discente> listarAlunosPorStatus(Curso curso, boolean status){
        List<Discente> resultado = new ArrayList<>();

        for(Discente d: curso.getAlunos()){
            if(status == d.getStatus()){
                resultado.add(d);
            }
        }
        return resultado;
    }
}
