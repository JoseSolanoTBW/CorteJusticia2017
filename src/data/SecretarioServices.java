/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Classes.Persona.Secretario;
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
public class SecretarioServices extends Service{
    
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    public ArrayList<Secretario> get() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Secretario> secretarioList = new ArrayList<>();
        Secretario secre;
        
        cs = con.prepareCall("Call get_querellante(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, 1);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
            //secre = new Secretario(rs.getInt("idPersona"), rs.getString("nombre"), Apellido, columnCount, Direccion, NombreUsuario, Password)
           
        }
        
        con.close();
        
        //return jueces;
    }
}
