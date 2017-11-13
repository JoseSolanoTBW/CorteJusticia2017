/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Business.Persona.Juez;
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

public class JuezServices  extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    public ArrayList<Juez> getSecretarios() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Juez> secretarioList = new ArrayList<>();
        Juez juez;
        
        cs = con.prepareCall("Call get_all_judges");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
           juez = new Juez(rs.getInt("idPersona"),rs.getInt("cedula"), rs.getString("nombre"), 
                           rs.getString("apellido"), rs.getInt("telefono"), rs.getString("direccion"), 
                           rs.getInt("numeroJuez"),rs.getInt("numeroSala"),rs.getString("nombresala"), 
                           rs.getString("nombreUsuario"), rs.getInt("idUsuario"));
            secretarioList.add(juez);
           
        }
        
        con.close();
        
        return secretarioList;
    }
    
    public Juez get() throws SQLException, IOException{
    con = getConnection();

        Juez juez;
        
        cs = con.prepareCall("Call get_secretaries");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();

           juez = new Juez(rs.getInt("idPersona"),rs.getInt("cedula"), rs.getString("nombre"), 
                           rs.getString("apellido"), rs.getInt("telefono"), rs.getString("direccion"), 
                           rs.getInt("numeroJuez"),rs.getInt("numeroSala"),rs.getString("nombresala"), 
                           rs.getString("nombreUsuario"), rs.getInt("idUsuario"));     
        con.close();
        
        return juez;
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
    

    
}
