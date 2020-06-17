package albunsfx.db.entidade;

public class AlbumMusica {
    private int id_album;
    private int id_musica;

    public AlbumMusica() {
    }

    public AlbumMusica(int id_album, int id_musica) {
        this.id_album = id_album;
        this.id_musica = id_musica;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public int getId_musica() {
        return id_musica;
    }

    public void setId_musica(int id_musica) {
        this.id_musica = id_musica;
    }
    
}
