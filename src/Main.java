import Entity.Discente;
import Entity.Oportunidade;
import Entity.Usuario;
import Repository.impl.InscricoesRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Service.OportunidadeService;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Enum.Status;
import Service.UsuarioService;

void main() {
    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    Oportunidade oportunidade1 = new Oportunidade("Vaga marketing DA", "Disponivel agora", TipoOportunidade.EVENTO, Modalidade.PRESENCIAL, 24, 10);
    Oportunidade oportunidade2 = new Oportunidade("Vaga PETCOMP", "Disponivel agora", TipoOportunidade.PROJETO, Modalidade.PRESENCIAL, 64, 12);
    oportunidadeService.criarOportunidade(oportunidade1);
    oportunidadeService.criarOportunidade(oportunidade2);

    IO.println("Oportunidade Criada " + oportunidade1);   //ainda nao cadastrada
    IO.println("Oportunidade Criada " + oportunidade2);
    oportunidadeService.publicar(1L);
    oportunidadeService.publicar(2L);
    Usuario sam = new Discente("nome", "email", "senha", "papel", "matricula", 3);
    UsuarioService src = new UsuarioService();
    src.selecionarOportunidade(oportunidadeRepository, inscricoesRepository);
    }