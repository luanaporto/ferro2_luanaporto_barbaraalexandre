package albunsfx.db.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import albunsfx.db.entidade.Album;
import albunsfx.db.util.Banco;
import java.io.InputStream;

public class AlbumDAL {
    public boolean salvar(Album a, InputStream is)
    {
        String sql="insert into album (al_id,al_titulo,al_ano,al_descricao,al_rating,gen_id,art_id,al_foto) values (default,'#1',#2,'#3',#4,#5,#6,?)";
        sql=sql.replace("#1", a.getTitulo());
        sql=sql.replace("#2", ""+a.getAno());
        sql=sql.replace("#3", a.getDescricao());
        sql=sql.replace("#4", ""+a.getRating());
        sql=sql.replace("#5", ""+a.getGenero().getId());
        sql=sql.replace("#6", ""+a.getArtista().getId());
        return Banco.getConexao().gravarAlterarComFoto(sql,is);
    }
    public InputStream getFoto(int id)
    {
        return Banco.getConexao().carregarFoto("select al_foto from album where al_id="+id);
    }
    public boolean alterar(Album a, InputStream is)
    {
        String sql="update album set al_titulo='#1',al_ano=#2,al_descricao='#3',al_rating=#4,gen_id=#5,art_id=#6,art_foto=? where al_id="+a.getId();
        sql=sql.replace("#1", a.getTitulo());
        sql=sql.replace("#2", ""+a.getAno());
        sql=sql.replace("#3", a.getDescricao());
        sql=sql.replace("#4", ""+a.getRating());
        sql=sql.replace("#5", ""+a.getGenero().getId());
        sql=sql.replace("#6", ""+a.getArtista().getId());
        return Banco.getConexao().gravarAlterarComFoto(sql,is);
    }
    public boolean apagar(Album a)
    {
        return Banco.getConexao().manipular("delete from album where al_id="+a.getId());
    }
    public Artista get(int id)
    {   Artista a=null;
        String sql="select * from artista where art_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        TipoArtistaDAL tadal=new TipoArtistaDAL();
        try{
        if(rs.next())
            a=new Artista(rs.getInt("art_id"),rs.getString("art_nome"),rs.getString("art_origem"),
                    rs.getDate("art_dtnasc").toLocalDate(),tadal.get(rs.getInt("ta_id")));
        }catch(Exception e){};
        return a;
    }
    public List get(String filtro)
    {   String sql="select * from artista";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<Artista> list = new ArrayList();
        TipoArtistaDAL tadal=new TipoArtistaDAL();
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
           while(rs.next())
               list.add(new Artista(rs.getInt("art_id"),rs.getString("art_nome"),rs.getString("art_origem"),
                    rs.getDate("art_dtnasc").toLocalDate(),tadal.get(rs.getInt("ta_id"))));
        }catch(Exception e){};
        return list;
    }
}
