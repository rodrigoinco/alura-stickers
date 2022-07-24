import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json) {
        // extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        //popular a lista de conteudos
        for (Map<String, String> atributos : listaDeAtributos) {
            String titulo = atributos.get("title").replaceAll(":", "");

            String[] urlImageArr = atributos.get("image").split("@._");
            String urlImage = urlImageArr.length > 1 ? urlImageArr[0] + "@.jpg" : urlImageArr[0];

            String nota = atributos.get("imDbRating");
            
            var conteudo = new Conteudo(titulo, urlImage, nota);

            conteudos.add(conteudo);
        }

        return conteudos;
    }
    
}
