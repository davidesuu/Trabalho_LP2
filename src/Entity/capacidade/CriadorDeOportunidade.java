package Entity.capacidade;

import Entity.DiscenteDiretor;
import Entity.Oportunidade;

import javax.swing.text.html.parser.Entity;
import Enum.Modalidade;
import Enum.TipoOportunidade;

public interface CriadorDeOportunidade {
    public Oportunidade criarOportunidade(String titulo, String descricao, TipoOportunidade tipo,
                                          Modalidade modalidade, int cargaHoraria, int vagas);
}
