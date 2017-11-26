package data;

import Business.Caso.Caso;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CasosService extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;

    public ArrayList<Caso> getCasosPorQuerellante(int id) throws IOException, SQLException {
        con = getConnection();
        ArrayList<Caso> querellanteList = new ArrayList<>();
        Caso casos;

        cs = con.prepareCall("Call get_cases_by_querellante(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            casos = new Caso(rs.getInt("numeroCaso"), rs.getString("descripcion"),
                    rs.getDate("fechaCreacion"), rs.getInt("idEstado"),rs.getString("Estado"));
            querellanteList.add(casos);
        }

        con.close();

        return querellanteList;
    }
    
     public ArrayList<Caso> getCasosPorJuez(int id) throws IOException, SQLException {
        con = getConnection();
        ArrayList<Caso> querellanteList = new ArrayList<>();
        Caso casos;

        cs = con.prepareCall("Call get_cases_by_juedge(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            casos = new Caso(rs.getInt("numeroCaso"), rs.getString("descripcion"),
                    rs.getDate("fechaCreacion"), rs.getInt("idEstado"),rs.getString("Estado"),rs.getInt("Persona_idPersona"),
                    rs.getString("QuerellateNombre"), rs.getString("apellido"));
            querellanteList.add(casos);
        }

        con.close();

        return querellanteList;
    }
}
