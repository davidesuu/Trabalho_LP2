import Entity.*;
import Repository.impl.InscricoesRepositoryImpl;
import Repository.impl.OportunidadeRepositoryImpl;
import Service.OportunidadeService;
import Enum.TipoOportunidade;
import Enum.Modalidade;
import Enum.Status;
import Service.UsuarioService;
import Service.CertificadoService;

void main() {
    OportunidadeRepositoryImpl oportunidadeRepository = new OportunidadeRepositoryImpl();
    InscricoesRepositoryImpl inscricoesRepository = new InscricoesRepositoryImpl();
    Docente docente = new Docente("Geraldo", "dfad", "senha", "sda", "2131", "dmat");
    DiscenteDiretor rochel = new DiscenteDiretor("rochel", "email", "senha", "as", "a",4, new Curso("ccomp", 12, 132, "ws"), "sda", 12);
    OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepository);
    Oportunidade oportunidade1 = oportunidadeService.criarOportunidade("Vaga marketing DA", "Disponivel agora", TipoOportunidade.EVENTO, Modalidade.PRESENCIAL, 24, 10, rochel);
    Oportunidade oportunidade2 = oportunidadeService.criarOportunidade("Vaga PETCOMP", "Disponivel agora", TipoOportunidade.PROJETO, Modalidade.PRESENCIAL, 64, 12, rochel);

    IO.println("Oportunidade Criada " + oportunidade1);   //ainda nao cadastrada
    IO.println("Oportunidade Criada " + oportunidade2);
    oportunidadeService.publicarOpurtunidade(1L, docente);
    oportunidadeService.publicarOpurtunidade(2L, docente);
    Curso curso = new Curso("ccomp", 123, 60, "2");
    Usuario sam = new Discente("nome", "email", "senha", "papel",
            "matricula", 3, curso);
    UsuarioService src = new UsuarioService();
    src.selecionarOportunidade(oportunidadeRepository, inscricoesRepository);

    String text = "https://example.com";
    String output = "qrcode.png";

    CertificadoService.generate("oiii.png");

    }