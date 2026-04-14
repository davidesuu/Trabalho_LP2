import Entity.Oportunidade;
import Repository.impl.OportunidadeRepositoryImpl;
import Service.OportunidadeService;


void main() {
    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    Oportunidade oportunidade = new Oportunidade("Vaga marketing DA", "Disponivel agora");

    oportunidadeService.criarOportunidade(oportunidade);

    IO.println("Oportunidade Criada " + oportunidade);   //ainda nao cadastrada

    oportunidadeService.publicar(1L);



    }