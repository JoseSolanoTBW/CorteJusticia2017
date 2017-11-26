package data;

import Business.Caso.Caso;
import Business.Caso.Estado;
import Business.Persona.Querellante;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
       public ArrayList<Caso> getCasosNull() throws IOException, SQLException {
         con = getConnection();
         ArrayList<Caso> listaCasos = new ArrayList<>();
         Caso nCaso;
 
         cs = con.prepareCall("Call get_casosNull");
         cs.setEscapeProcessing(true);
 
         rs = cs.executeQuery();
 
         while (rs.next()) {
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
     
     
      public void create(Caso cas) throws SQLException, IOException {
         con = getConnection();
         cs = con.prepareCall("Call create_caso(?,?,?,?,?)");
        java.sql.Date sqlDate = java.sql.Date.valueOf( LocalDate.now());
        cs.setEscapeProcessing(true);
        cs.setString(1, cas.getDescripcion());
       cs.setInt(2, cas.getDenunciante().getIdPersona());
        cs.setInt(3, cas.getEstadoCaso().getIdEstado());
        cs.setDate(4, sqlDate);
         cs.setInt(5, cas.getJuezAsignado().getNumeroJuez());
        cs.execute();
        con.close();
    }
}
