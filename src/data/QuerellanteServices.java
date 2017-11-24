/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
public class QuerellanteServices extends Service {

    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;

    public ArrayList<Querellante> getQuerrellantes() throws IOException, SQLException {
        con = getConnection();
        ArrayList<Querellante> querellanteList = new ArrayList<>();
        Querellante quere;

        cs = con.prepareCall("Call get_all_querrelantes");
        cs.setEscapeProcessing(true);

        rs = cs.executeQuery();

        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            quere = new Querellante(rs.getInt("idPersona"), rs.getInt("cedula"), rs.getString("nombre"),
                    rs.getString("apellido"), rs.getInt("telefono"),
                    rs.getString("direccion"));
            querellanteList.add(quere);

        }

        con.close();

        return querellanteList;
    }

    public Querellante get(int id) throws SQLException, IOException {
        con = getConnection();
        Querellante quere = null;
        cs = con.prepareCall("Call get_querellante(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, id);

        rs = cs.executeQuery();
        while (rs.next()) {
            quere = new Querellante(rs.getInt("idPersona"), rs.getInt("cedula"), rs.getString("nombre"),
                    rs.getString("apellido"), rs.getInt("telefono"),
                    rs.getString("direccion"));
        }
        con.close();

        return quere;
    }

    public void update(Querellante quere) throws SQLException, IOException {
        con = getConnection();

        cs = con.prepareCall("Call update_persona(?,?,?,?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, quere.getCedula());
        cs.setString(2, quere.getNombre());
        cs.setString(3, quere.getApellido());
        cs.setInt(4, quere.getTelefono());
        cs.setString(5, quere.getDireccion());
        cs.setInt(6, quere.getIdPersona());
        cs.execute();
    }

    public void delete(int idPersona) throws SQLException, IOException {
        con = getConnection();

        cs = con.prepareCall("Call delete_persona(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1, idPersona);
        cs.execute();
        con.close();
    }

    public void create(Querellante quere) throws IOException, SQLException {
        con = getConnection();
        cs = con.prepareCall("Call create_persona(?,?,?,?,?,?)");
        cs.setEscapeProcessing(true);
        cs.setString(1, quere.getNombre());
        cs.setString(2, quere.getApellido());
        cs.setInt(3, quere.getCedula());
        cs.setInt(4, quere.getTelefono());
        cs.setString(5, quere.getDireccion());
        cs.setInt(6, 3);
        cs.execute();

        con.close();

    }

}
