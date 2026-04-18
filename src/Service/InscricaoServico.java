package Service;

import Entity.Inscricao;
import Enum.Status;
import java.time.LocalDate;

public class InscricaoServico {
    public void aprovar(LocalDate data, Inscricao inscricao){
        inscricao.setCreated_at(data);
    }

    public void rejeitar(Inscricao inscricao){
        inscricao.setStatus(Status.REJEITADO);
    }
}
