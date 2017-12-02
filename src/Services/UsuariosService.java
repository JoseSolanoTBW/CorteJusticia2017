package Services;

import Business.Persona.Persona;
import Business.Persona.Usuario;
import data.Service;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuariosService extends Service{
    
    private CallableStatement cs;
    private ResultSet rs;
    private Connection con;
    
    public ArrayList<Usuario> getUsuarios() throws IOException, SQLException{
    con = getConnection();
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        Usuario user;
        
        cs = con.prepareCall("Call get_all_user");
        cs.setEscapeProcessing(true);
        
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next())
        {
            user = new Usuario(
                    rs.getInt("Persona_idPersona"),
                    rs.getString("nombreUsuario"), 
                    rs.getString("clave") 
                   );
            usuarioList.add(user);           
        }
        
        con.close();
        
        return usuarioList;
    }
    
    public Persona getPersonaById (int userId) throws IOException, SQLException  {
      
        con = getConnection();
        Persona pers;
        
        cs = con.prepareCall("Call get_persona_byidPersona(?)");
        cs.setEscapeProcessing(true);
        cs.setInt(1,userId);
        rs = cs.executeQuery();
        
        int columnCount = rs.getMetaData().getColumnCount();
        rs.next();
               pers = new Persona(rs.getInt("idPersona"),rs.getInt("cedula"), 
                          rs.getString("personaNombre"), rs.getString("apellido"),
                          rs.getInt("telefono"), rs.getString("direccion"),
                          rs.getString("nombre"));            
        
        con.close();        
        return pers;
    }    
}
