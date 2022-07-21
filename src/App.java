import java.net.URI;
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
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println(listaDeFilmes.size());

        // exibir e manipular os dados

        String tituloTxt = WHITE + "Título: " + BOLD;
        String imageTxt = WHITE + "Imagem: " + BOLD;
        String classificacaoTxt = WHITE + BKMAGENTA + "Classificação: ";

        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(tituloTxt + filme.get("title") + RESET);
            System.out.println(imageTxt + filme.get("image") + RESET);
            System.out.println(classificacaoTxt + filme.get("imDbRating") + RESET);
            System.out.println(YELLOW + BOLD + "*".repeat((int) Double.parseDouble(filme.get("imDbRating"))) + RESET);
        }
    }
}
