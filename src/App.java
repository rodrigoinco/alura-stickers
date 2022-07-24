import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    // Constantes
    public static final String WHITE = "\u001b[37m";
    public static final String BOLD = "\u001b[1m";
    public static final String RESET = "\u001b[m";
    public static final String BKMAGENTA = "\u001b[45m";
    public static final String YELLOW = "\u001b[33m";

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os 250 filmes
        //String url = "https://imdb-api.com/en/API/MostPopularMovies/k_texyfc4f";
        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        String url = "https://api.nasa.gov/planetary/apod?api_key=TIbBHNbGBKywyHmr7Cbpdn1x80Z0im01bAm9AtnD&start_date=2022-07-01";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
        
        ClienteHttp http = new ClienteHttp();
        String json = http.buscaDados(url);

        //exibir e manipular os dados
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 10; i++) { 

            Conteudo conteudo = conteudos.get(i);

            System.out.println(conteudo.getTitulo());

            InputStream inputStream = new URL(conteudo.getUrlImage()).openStream();
            String nomeArquivo = conteudo.getTitulo() + ".png";
            
            String nota = conteudo.getNota(); 
            
            geradora.cria(inputStream, nomeArquivo, nota);
        }
    }
}
