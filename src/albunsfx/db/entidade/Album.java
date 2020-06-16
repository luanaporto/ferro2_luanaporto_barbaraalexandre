package albunsfx.db.entidade;

public class Album {
    private int id;
    private String titulo;
    private int ano;
    private String descricao;
    private int rating;
    private int id_genero;
    private int id_artista;

    public Album() {
    }

    public Album(int id, String titulo, int ano, String descricao, int rating, int id_genero, int id_artista) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.descricao = descricao;
        this.rating = rating;
        this.id_genero = id_genero;
        this.id_artista = id_artista;
    }

    public Album(String titulo, int ano, String descricao, int rating, int id_genero, int id_artista) {
        this.id = 0;
        this.titulo = titulo;
        this.ano = ano;
        this.descricao = descricao;
        this.rating = rating;
        this.id_genero = id_genero;
        this.id_artista = id_artista;
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

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getId_artista() {
        return id_artista;
    }

    public void setId_artista(int id_artista) {
        this.id_artista = id_artista;
    }
    
}
