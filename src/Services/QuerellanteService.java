package Services;

import Business.Persona.Querellante;
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
public class QuerellanteService extends Service{
    
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    /**
     *
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<Querellante> getSecretarios() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Querellante> querellantesList = new ArrayList<>();
        Querellante quere;
        
        cs = con.prepareCall("Call get_all_querrelantes");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
            quere = new Querellante(
                    rs.getInt("idPersona"),rs.getInt("cedula"), 
                    rs.getString("nombre"), rs.getString("apellido"), 
                    rs.getInt("telefono"), rs.getString("direccion"));
            querellantesList.add(quere);           
        }
        
        con.close();
        
        return querellantesList;
    }

}
