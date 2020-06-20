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
        String sql="update album set al_titulo='#1',al_ano=#2,al_descricao='#3',al_rating=#4,gen_id=#5,art_id=#6,al_foto=? where al_id="+a.getId();
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
    public Album get(int id)
    {   Album a=null;
        String sql="select * from album where al_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        GeneroDAL gendal=new GeneroDAL();
        ArtistaDAL aldal=new ArtistaDAL();
        try{
        if(rs.next())
            a=new Album(rs.getInt("al_id"),rs.getString("al_titulo"),rs.getInt("al_ano"),rs.getString("al_descricao"), rs.getInt("al_rating"),
                    gendal.get(rs.getInt("gen_id")),aldal.get(rs.getInt("art_id")));
        }catch(Exception e){};
        return a;
    }
    public List get(String filtro)
    {   String sql="select al.* from album al"
            + " inner join artista art on art.art_id=al.art_id";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<Album> list = new ArrayList();
        GeneroDAL gendal=new GeneroDAL();
        ArtistaDAL aldal=new ArtistaDAL();
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
           while(rs.next())
               list.add(new Album(rs.getInt("al_id"),rs.getString("al_titulo"),rs.getInt("al_ano"),rs.getString("al_descricao"), rs.getInt("al_rating"),
                    gendal.get(rs.getInt("gen_id")),aldal.get(rs.getInt("art_id"))));
        }catch(Exception e){};
        return list;
    }
}
