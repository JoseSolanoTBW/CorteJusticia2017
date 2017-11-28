/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Business.Corte.Sala;
import Business.Persona.Juez;
import Business.Persona.Usuario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mean
 */
import data.Service;
import java.util.Random;

public class JuezServices  extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    public ArrayList<Juez> getJueces() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Juez> juezList = new ArrayList<>();
        Juez juez;
        
        cs = con.prepareCall("Call get_jueces");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
           Juez jue = new Juez();
           Sala sal = new Sala();
           Usuario usu = new Usuario();
           sal.setNombreSala(rs.getString("sala"));
           usu.setIdUsuario(rs.getInt("idUsuario"));
           usu.setNombreUsuario(rs.getString("nombreUsuario"));
           jue.setIdPersona(rs.getInt("idPersona"));
           jue.setCedula(rs.getInt("cedula"));
           jue.setNombre(rs.getString("nombre"));
           jue.setNumeroJuez(rs.getInt("numeroJuez"));
           jue.setSalaJustica(sal);
           jue.setLoginUsuario(usu);
           
           juezList.add(jue);
           
           
           
        }
        
        con.close();
        
        return juezList;
    }
    
    public Juez get() throws SQLException, IOException{
     con = getConnection();
       Juez jue= null;
        
        cs = con.prepareCall("Call get_jueces");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
           jue = new Juez();
           Sala sal = new Sala();
           Usuario usu = new Usuario();
           sal.setNombreSala(rs.getString("sala"));
           usu.setIdUsuario(rs.getInt("idUsuario"));
           usu.setNombreUsuario(rs.getString("nombreUsuario"));
           jue.setIdPersona(rs.getInt("idPersona"));
           jue.setCedula(rs.getInt("cedula"));
           jue.setNombre(rs.getString("nombre"));
           jue.setNumeroJuez(rs.getInt("numeroJuez"));
           jue.setSalaJustica(sal);
           jue.setLoginUsuario(usu);
           
        
           
           
           
        }
        
        con.close();
        
        return jue;
    }
    
    public void update(Juez juez) throws SQLException, IOException {
        con = getConnection();
        
        cs = con.prepareCall("Call update_persona(?,?,?,?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1,juez.getCedula());
        cs.setString(2,juez.getNombre());
        cs.setString(3,juez.getApellido());
        cs.setInt(4, juez.getTelefono());
        cs.setString(5, juez.getDireccion());
        cs.setInt(6, juez.getIdPersona());
        cs.execute();
        
        cs = con.prepareCall("Call update_user(?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, juez.getLoginUsuario().getIdUsuario());
        cs.setString(2, juez.getLoginUsuario().getNombreUsuario());
        cs.execute();
        
        cs = con.prepareCall("Call update_juez(?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1,juez.getNumeroJuez());
        cs.setInt(2,juez.getSalaJustica().getNumeroSala());
        cs.setInt(3,juez.getLoginUsuario().getIdUsuario());
        cs.execute();
        
        con.close();                  
    }
    
    public void delete(int idPersona) throws SQLException, IOException{
        con = getConnection();
        
        cs = con.prepareCall("Call delete_persona(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idPersona);
        cs.execute();        
    }
    
    public Sala getSalaByUserName(String userName) throws SQLException, IOException
    {
        con = getConnection();

        Sala salaJuez;
        
        cs = con.prepareCall("Call get_sala_by_userName(?)");
        cs.setEscapeProcessing(true);
        cs.setString(1, userName);
        rs = cs.executeQuery();
        rs.next();
           salaJuez = new Sala(rs.getInt("idSala"), rs.getString("nombre"));     
        con.close();
        
        return salaJuez;
        
    }    
     public int getRandomJuez() throws IOException, SQLException{
        ArrayList<Integer> listId = new ArrayList<>();
        Random random = new Random();
    con = getConnection();
        cs = con.prepareCall("Call get_idJuez");
        rs = cs.executeQuery();
         while(rs.next())
        {
            listId.add(rs.getInt("idJuez"));
           
        }
    
        
        return listId.get(random.nextInt(listId.size()));
    }

}
