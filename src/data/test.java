/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Business.Persona.Secretario;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author mean
 */
public class test {
    
       static SecretarioServices sSecre = new SecretarioServices();
    public static void main(String args[]) throws SQLException, IOException{
        Secretario sec = new Secretario(2, 302450025, "Adrian", "Obando", 84793258,"Alajuelita", "AdrianLeiton", 2);
        sSecre.update(sec);
        
    }

 

}
