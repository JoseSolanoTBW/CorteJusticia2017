/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Business.Persona.Secretario;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mean
 */
public  class SecretarioServices extends Service{
    
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    public ArrayList<Secretario> getSecretarios() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Secretario> secretarioList = new ArrayList<>();
        Secretario secre;
        
        cs = con.prepareCall("Call get_secretaries");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
            secre = new Secretario(rs.getInt("idPersona"),rs.getInt("cedula"), rs.getString("nombre"), 
                    rs.getString("apellido"), rs.getInt("telefono"), 
                    rs.getString("direccion"), rs.getString("nombreUsuario"), 
                    rs.getInt("idUsuario"));
            secretarioList.add(secre);
           
        }
        
        con.close();
        
        return secretarioList;
    }
    
    public Secretario get() throws SQLException, IOException{
    con = getConnection();

        Secretario secre;
        
        cs = con.prepareCall("Call get_secretaries");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();

            secre = new Secretario(rs.getInt("idPersona"),rs.getInt("cedula"), rs.getString("nombre"), 
                    rs.getString("apellido"), rs.getInt("telefono"), 
                    rs.getString("direccion"), rs.getString("nombreUsuario"), 
                    rs.getInt("idUsuario"));

        
        con.close();
        
        return secre;
    }
    
    public void update(Secretario secre) throws SQLException, IOException {
        con = getConnection();
        
        cs = con.prepareCall("Call update_persona(?,?,?,?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1,secre.getCedula());
        cs.setString(2,secre.getNombre());
        cs.setString(3,secre.getApellido());
        cs.setInt(4, secre.getTelefono());
        cs.setString(5, secre.getDireccion());
        cs.setInt(6, secre.getIdPersona());
        cs.execute();
        
        cs = con.prepareCall("Call update_user(?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, secre.getLoginUsuario().getIdUsuario());
        cs.setString(2, secre.getLoginUsuario().getNombreUsuario());
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
