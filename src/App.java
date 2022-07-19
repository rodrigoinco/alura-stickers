import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        
        //fazer uma conexão HTTP e buscar os 250 filmes
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        //extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println(listaDeFilmes.size());
        
        //exibir e manipular os dados
        String white = "\u001b[37m";
        String bold = "\u001b[1m";
        String reset = "\u001b[m";
        String backMagenta = "\u001b[45m";
        String yellowBold = "\u001b[1;33m";
        String tituloTxt = white.concat("Título: ").concat(bold);
        String imageTxt = white.concat("Imagem: ").concat(bold);
        String classificacaoTxt = white.concat(backMagenta).concat("Classificação: ");

        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println(tituloTxt + filme.get("title") + reset);
            System.out.println(imageTxt + filme.get("image") + reset);
            System.out.println(classificacaoTxt + filme.get("imDbRating") + reset);
            System.out.println(yellowBold + "*".repeat((int) Double.parseDouble(filme.get("imDbRating"))) + reset);
        }
    }
}
