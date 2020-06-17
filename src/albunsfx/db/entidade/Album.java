package albunsfx.db.entidade;

public class Album {
    private int id;
    private String titulo;
    private int ano;
    private String descricao;
    private int rating;
    private Genero genero;
    private Artista artista;

    public Album() {
    }

    public Album(int id, String titulo, int ano, String descricao, int rating, Genero genero, Artista artista) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.descricao = descricao;
        this.rating = rating;
        this.genero = genero;
        this.artista = artista;
    }

    public Album(String titulo, int ano, String descricao, int rating, Genero genero, Artista artista) {
        this.id = 0;
        this.titulo = titulo;
        this.ano = ano;
        this.descricao = descricao;
        this.rating = rating;
        this.genero = genero;
        this.artista = artista;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    
}
