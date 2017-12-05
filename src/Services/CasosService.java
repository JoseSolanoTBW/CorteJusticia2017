package Services;

import Business.Caso.Caso;
import Business.Caso.DetalleHistorial;
import Business.Caso.Estado;
import Business.Persona.Querellante;
import data.Service;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mean
 */
public class CasosService extends Service{
    private CallableStatement cs;
    private ResultSet rs;
    
    /**
     *
     * @param id
     * @return Retorna todos los casos que ha creado ese querellante
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<Caso> getCasosPorQuerellante(int id) throws IOException, SQLException {
        
        ArrayList<Caso> querellanteList = new ArrayList<>();
        Caso casos;

        cs = getConnection().prepareCall("Call get_cases_by_querellante(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            casos = new Caso(rs.getInt("numeroCaso"), rs.getString("descripcion"),
                    rs.getDate("fechaCreacion"), rs.getInt("idEstado"),rs.getString("Estado"));
            querellanteList.add(casos);
        }

        

        return querellanteList;
    }
    
    /**
     *
     * @param id
     * @return Retorna todos los casos que ha creado ese Juez
     * @throws IOException
     * @throws SQLException
     */
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

    /**
     *
     * @return Consigue todos los casos que estan en null
     * @throws IOException
     * @throws SQLException
     */
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
     
    /**
     *  Crea un caso
     * @param cas
     * @throws SQLException
     * @throws IOException
     */
    public void create(Caso cas) throws SQLException, IOException {
        con = getConnection();
        cs = con.prepareCall("Call create_caso(?,?,?,?,?)");
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        cs.setEscapeProcessing(true);
        cs.setString(1, cas.getDescripcion());
        cs.setInt(2, cas.getDenunciante().getIdPersona());
        cs.setInt(3, cas.getEstadoCaso().getIdEstado());
        cs.setDate(4, sqlDate);
        cs.setInt(5, cas.getJuezAsignado().getNumeroJuez());
        cs.execute();
        con.close();
    }
      
    /**
     *  Actualiza un estado de un caso
     * @param idCaso
     * @param nuevoEstado
     * @throws SQLException
     * @throws IOException
     */
    public void actualizarEstado(int idCaso, int nuevoEstado) throws SQLException, IOException
     {
        con = getConnection();
        cs = con.prepareCall("Call update_estado_caso(?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idCaso);
        cs.setInt(2, nuevoEstado);
        cs.execute();
        con.close();
     } 
    
    /**
     *  Inserta una resolucion al estado del caso
     * @param idCaso
     * @param text
     * @throws SQLException
     * @throws IOException
     */
    public void insertarResolucionCasoEstado(int idCaso, String text ) throws SQLException, IOException
     {
        con = getConnection();
        cs = con.prepareCall("Call insertResolutionCaso(?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idCaso);
        cs.setString(2, text);
        cs.execute();
        con.close();
     }
     
    /**
     *
     * @param id
     * @return consigue un caso en especifico
     * @throws IOException
     * @throws SQLException
     */
    public Caso getCasos(int id) throws IOException, SQLException {
        con = getConnection();
        Caso casos;

        cs = con.prepareCall("Call get_cases_by_id(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        rs.next();
            casos = new Caso(rs.getInt("numeroCaso"), rs.getString("descripcion"),
                    rs.getDate("fechaCreacion"), rs.getInt("idEstado"),rs.getString("Estado"),rs.getInt("Persona_idPersona"),
                    rs.getString("QuerellateNombre"), rs.getString("apellido"), rs.getString("resolucion"));
        
        con.close();

        return casos;
    }
    
    /**
     * Agrega un registro del al historial de ese caso
     * @param idEstado
     * @param idCaso
     * @throws SQLException
     * @throws IOException
     */
    public void createHistorial(int idEstado, int idCaso) throws SQLException, IOException {
        con = getConnection();
        cs = con.prepareCall("Call create_detalle_caso(?,?,?)");
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        cs.setEscapeProcessing(true);
        cs.setDate(1, sqlDate);
        cs.setInt(2,idEstado);
        cs.setInt(3, idCaso);
        cs.execute();
        con.close();
    }
        
    /**
     *
     * @param idCaso
     * @return obtiene los historiales por el id del caso
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<DetalleHistorial> getCasoId(int idCaso) throws IOException, SQLException {
         con = getConnection();
         ArrayList<DetalleHistorial> listaCasos = new ArrayList<>();
         DetalleHistorial nCaso;
 
         cs = con.prepareCall("Call get_histrial_by_Caso(?)");
         cs.setEscapeProcessing(true);
         cs.setInt(1, idCaso);
         rs = cs.executeQuery();
 
         while (rs.next()) {
            nCaso = new DetalleHistorial();
            nCaso.setEstadoHistorial(new Estado());
            nCaso.getEstadoHistorial().setNombre(rs.getString("nombre"));
            nCaso.setFechaActualizacion(rs.getDate("fechaModificacion"));
             listaCasos.add(nCaso);
         }
 
         con.close();
         return listaCasos;
 
     }
}
