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
    
    public  ArrayList<String[]> getJueces() throws IOException, FileNotFoundException, ParseException, SQLException{
        con = getConnection();
        ArrayList<String[]> jueces = new ArrayList<>();
        
        cs = con.prepareCall("Call get_querellante(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, 1);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
            String[] row = new String[columnCount];
            for (int i=0; i <columnCount ; i++)
            {
                row[i] = rs.getString(i + 1);
            }
            jueces.add(row);
        }
        
        con.close();
        
        return jueces;
        
    }
    
    public Juez GetJuezObject()
    {
        return new Juez();
    }

    
}
