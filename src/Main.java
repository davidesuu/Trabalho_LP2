import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;
import Service.OportunidadeService;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Enum.Status;

void main() {
    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    Oportunidade oportunidade = new Oportunidade("Vaga marketing DA", "Disponivel agora", TipoOportunidade.EVENTO, Modalidade.PRESENCIAL, 24, 10);

    oportunidadeService.criarOportunidade(oportunidade);

    IO.println("Oportunidade Criada " + oportunidade);   //ainda nao cadastrada

    oportunidadeService.publicar(1L);



    }