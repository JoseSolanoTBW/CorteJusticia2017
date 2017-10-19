/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mean
 */
public class JuezServices extends Connect{
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ArrayList<String[]> getJueces() throws IOException, FileNotFoundException, ParseException, SQLException{
        Connection con = getConnection();
        ArrayList<String[]> jueces = new ArrayList<>();
        
        ps = con.prepareStatement("exec getJueces");
        ps.setEscapeProcessing(true);
        rs = ps.executeQuery();
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
}
