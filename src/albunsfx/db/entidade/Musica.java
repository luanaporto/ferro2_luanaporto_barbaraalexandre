package albunsfx.db.entidade;

public class Musica {
    private int id;
    private String titulo;
    private double duracao;
    private String url;
    private int id_artista;
    private int id_genero;

    public Musica() {
    }

    public Musica(int id, String titulo, double duracao, String url, int id_artista, int id_genero) {
        this.id = id;
        this.titulo = titulo;
        this.duracao = duracao;
        this.url = url;
        this.id_artista = id_artista;
        this.id_genero = id_genero;
    }

    public Musica(String titulo, double duracao, String url, int id_artista, int id_genero) {
        this.id = 0;
        this.titulo = titulo;
        this.duracao = duracao;
        this.url = url;
        this.id_artista = id_artista;
        this.id_genero = id_genero;
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

    public int getId_artista() {
        return id_artista;
    }

    public void setId_artista(int id_artista) {
        this.id_artista = id_artista;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }
    
}
