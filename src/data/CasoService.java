/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Business.Caso.Caso;
import Business.Caso.Estado;
import Business.Persona.Querellante;
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
public class CasoService extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    public ArrayList<Caso> getCasosNull() throws IOException, SQLException{
    con = getConnection();
    ArrayList<Caso> listaCasos = new ArrayList<>();
     Caso nCaso;
     
     cs = con.prepareCall("Call get_casosNull");
      cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
       
        while(rs.next())
        {
            nCaso = new Caso();
            Querellante quere = new Querellante();
            Estado est = new Estado();
            est.setNombre(rs.getString("estado"));
            quere.setNombre(rs.getString("querellante"));
            nCaso.setNumeroCaso(rs.getInt("numeroCaso"));
            nCaso.setDescripcion(rs.getString("descripcion"));
            nCaso.setFechaCfeacion(rs.getDate("fechaCreacion"));
            nCaso.setDenunciante(quere);
            nCaso.setEstadoCaso(est);
            listaCasos.add(nCaso);
        }
        
        con.close();
        return listaCasos;
        
    
    }
    
}
