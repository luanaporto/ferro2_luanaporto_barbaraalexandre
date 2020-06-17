package albunsfx.db.entidade;

public class Musica {
    private int id;
    private String titulo;
    private double duracao;
    private String url;
    private Artista artista;
    private Genero genero;

    public Musica() {
    }

    public Musica(int id, String titulo, double duracao, String url, Artista artista, Genero genero) {
        this.id = id;
        this.titulo = titulo;
        this.duracao = duracao;
        this.url = url;
        this.artista = artista;
        this.genero = genero;
    }

    public Musica(String titulo, double duracao, String url, Artista artista, Genero genero) {
        this.id = 0;
        this.titulo = titulo;
        this.duracao = duracao;
        this.url = url;
        this.artista = artista;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
}
