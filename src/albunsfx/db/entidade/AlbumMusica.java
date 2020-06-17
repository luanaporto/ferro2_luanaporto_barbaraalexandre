package albunsfx.db.entidade;

public class AlbumMusica {
    private Album album;
    private Musica musica;

    public AlbumMusica() {
    }

    public AlbumMusica(Album album, Musica musica) {
        this.album = album;
        this.musica = musica;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }
    
}
