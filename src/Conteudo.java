public class Conteudo {

    private final String titulo;
    private final String urlImage;
    private final String nota;

    public Conteudo(String titulo, String urlImage, String nota) {
        this.titulo = titulo;
        this.urlImage = urlImage;
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getUrlImage() {
        return urlImage;
    }

    public String getNota(){
        return nota;
    }
}
