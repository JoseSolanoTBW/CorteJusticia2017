/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

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
import data.Service;
import java.util.Random;

/**
 *
 * @author mean
 */
public class JuezServices  extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    /**
     *
     * @return Retorna todos los jueces registrados en el sistema
     * @throws IOException
     * @throws SQLException
     */
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
    
    /**
     *
     * @param id
     * @return retorna un juez en especifico
     * @throws SQLException
     * @throws IOException
     */
    public Juez get(int id) throws SQLException, IOException{
  
       Juez jue= null;
        
        cs = getConnection().prepareCall("Call get_juez(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
           jue = new Juez();
           Sala sal = new Sala();
           Usuario usu = new Usuario();
           sal.setNumeroSala(rs.getInt("idSala"));
           sal.setNombreSala(rs.getString("sala"));
           usu.setNombreUsuario(rs.getString("nombreUsuario"));
           jue.setIdPersona(rs.getInt("idPersona"));
           jue.setCedula(rs.getInt("cedula"));
           jue.setNombre(rs.getString("nombre"));
           jue.setApellido(rs.getString("apellido"));
           jue.setTelefono(rs.getInt("telefono"));
           jue.setDireccion(rs.getString("direccion"));
           jue.setNumeroJuez(rs.getInt("numeroJuez"));
           jue.setSalaJustica(sal);
           jue.setLoginUsuario(usu);

           
        }
        
      
        
        return jue;
    }

    /**
     *  Crea un juez en la base de datos 
     * @param juz
     * @throws IOException
     * @throws SQLException
     */
    public void create(Juez juz) throws IOException, SQLException{
    
        cs = getConnection().prepareCall("Call create_persona(?,?,?,?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setString(1, juz.getNombre());
        cs.setString(2, juz.getApellido());
        cs.setInt(3, juz.getCedula());
        cs.setInt(4, juz.getTelefono());
        cs.setString(5, juz.getDireccion());
        cs.setInt(6, 1);
        cs.execute();  
        
        cs = getConnection().prepareCall("Call create_user(?,?)");
        cs.setEscapeProcessing(true);
        cs.setString(1, juz.getLoginUsuario().getNombreUsuario());
        cs.setString(2,juz.getLoginUsuario().getPassword());
        cs.execute();
        
        cs = getConnection().prepareCall("Call create_juez(?,?)");
        cs.setEscapeProcessing(true);
        cs.setString(2,juz.getSalaJustica().getNombreSala());
        cs.setInt(1, juz.getNumeroJuez());
        
        cs.execute();    
        
    
    }
    
    /**
     * Actualiza un juez en la base de datos
     * @param juez
     * @throws SQLException
     * @throws IOException
     */
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
        cs.setInt(1, juez.getIdPersona());
        cs.setString(2, juez.getLoginUsuario().getNombreUsuario());
        cs.execute();
        
        cs = con.prepareCall("Call update_juez(?,?)");
        cs.setEscapeProcessing(true);
      
        cs.setString(1,juez.getSalaJustica().getNombreSala());
        cs.setInt(2, juez.getNumeroJuez());
       
        cs.execute();
                         
    }
    
    /**
     *  Borra un Juez en la base de datos
     * @param idPersona
     * @throws SQLException
     * @throws IOException
     */
    public void delete(int idPersona) throws SQLException, IOException{
      
       cs = getConnection().prepareCall("Call delete_juez(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idPersona);
        cs.execute();
        cs = getConnection().prepareCall("Call delete_user(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idPersona);
        cs.execute();  
        cs = getConnection().prepareCall("Call delete_persona(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idPersona);
        cs.execute();    
              
    }
    
    /**
     *
     * @param userName
     * @return Retorna la sala en base al nombre del usuario
     * @throws SQLException
     * @throws IOException
     */
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

    /**
     *
     * @return Retorna el id de un juez al azar
     * @throws IOException
     * @throws SQLException
     */
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
