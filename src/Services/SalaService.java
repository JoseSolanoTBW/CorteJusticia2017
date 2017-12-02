/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Business.Corte.Sala;
import data.Service;
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
public class SalaService extends Service{
    private ResultSet rs;
    private CallableStatement cs;
    public ArrayList<Sala>getSalas() throws IOException, SQLException{
        ArrayList<Sala> listSala = new ArrayList<>();
        Sala sal= null;
        cs = getConnection().prepareCall("Call get_salas");
        cs.setEscapeProcessing(true);
        rs = cs.executeQuery();
        
        while(rs.next()){
            sal = new Sala(rs.getInt("idSala"), rs.getString("nombre"));
            listSala.add(sal);
        
        }
        
        return listSala;
        
    }
}
