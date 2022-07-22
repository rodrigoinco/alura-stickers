import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    // Constantes
    public static final String WHITE = "\u001b[37m";
    public static final String BOLD = "\u001b[1m";
    public static final String RESET = "\u001b[m";
    public static final String BKMAGENTA = "\u001b[45m";
    public static final String YELLOW = "\u001b[33m";

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os 250 filmes
        String url = "https://imdb-api.com/en/API/MostPopularMovies/k_texyfc4f";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 20; i++) { 
            String[] urlImageArr = listaDeFilmes.get(i).get("image").split("@._");
            String urlImage = urlImageArr.length > 1 ? urlImageArr[0] + "@.jpg" : urlImageArr[0];
            String titulo = listaDeFilmes.get(i).get("title").replaceAll(":", "");

            System.out.println(titulo);

            InputStream inputStream = new URL(urlImage).openStream();
            String nomeArquivo = titulo + ".png";
            
            String nota = listaDeFilmes.get(i).get("imDbRating") == "" ? "0": listaDeFilmes.get(i).get("imDbRating");
            
            geradora.cria(inputStream, nomeArquivo, nota);
        }
    }
}
