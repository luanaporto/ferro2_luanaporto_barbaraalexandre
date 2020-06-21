package albunsfx.db.dal;

import albunsfx.db.entidade.AlbumMusica;
import albunsfx.db.entidade.Musica;
import albunsfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlbumMusicaDAL {
    public boolean salvar(int al, List<Musica> mus)
    {
        String comand = "";
        for(Musica m:mus)
        {
            String sql="insert into album_musica (al_id,mus_id) values (#1,#2)";
            sql=sql.replace("#1", ""+al);
            sql=sql.replace("#2", ""+m.getId());
            comand = comand+" "+sql;
        }
        return Banco.getConexao().manipular(comand);
    }
    
    public boolean salvar(int al, Musica m)
    {
        String sql="insert into album_musica (al_id,mus_id) values (#1,#2)";
        sql=sql.replace("#1", ""+al);
        sql=sql.replace("#2", ""+m.getId());
        
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean alterar(int al, List<Musica> m)
    {
        Banco.getConexao().manipular("delete from album_musica where al_id="+al);
        return salvar(al, m);
    }
    
    public boolean apagar(int al, List<Musica> mus)
    {
        String comand = "";
        for(Musica m:mus)
        {
            comand = comand + " delete from album_musica where al_id="+al+" and mus_id="+m.getId();
        }
        return Banco.getConexao().manipular(comand);
    }
    
    public boolean apagar(int al, Musica m)
    {
        String sql ="delete from album_musica where al_id="+al+" and mus_id="+m.getId();
        
        return Banco.getConexao().manipular(sql);
    }
    
    public List<Musica> get(int id)
    {   
        List<Musica> am=null;
        String sql="select m.* from musica m"
                + " inner join album_musica am on am.mus_id=m.mus_id"
                + " where am.al_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        ArtistaDAL adal = new ArtistaDAL();
        GeneroDAL gdal = new GeneroDAL();
        try{
        if(rs.next())
            am.add(new Musica(rs.getInt("mus_id"),rs.getString("mus_titulo"),rs.getDouble("mus_duracao"),rs.getString("mus_url"),
                    adal.get(rs.getInt("art_id")),gdal.get(rs.getInt("gen_id"))));
        }catch(Exception e){};
        return am;
    }
    public List<Musica> get(String filtro)
    {   String sql="select m.* from musica m"
            + " inner join album_musica am on am.mus_id=m.mus_id"
            + " inner join album a on a.al_id=am.al_id";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<Musica> list = new ArrayList();
        ResultSet rs=Banco.getConexao().consultar(sql);
        ArtistaDAL adal = new ArtistaDAL();
        GeneroDAL gdal = new GeneroDAL();
        try{
           while(rs.next())
               list.add(new Musica(rs.getInt("mus_id"),rs.getString("mus_titulo"),rs.getDouble("mus_duracao"),rs.getString("mus_url"),
                    adal.get(rs.getInt("art_id")),gdal.get(rs.getInt("gen_id"))));
        }catch(Exception e){};
        return list;
    }
}
