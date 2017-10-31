/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mean
 */
public class prueba {
            static JuezServices j = new JuezServices();
            
            public static void main(String[] args) throws IOException, FileNotFoundException, ParseException, SQLException{
            ArrayList<String[]> ar = j.getJueces();
                for (String[] strings : ar) {
                    for (String string : strings) {
                        System.out.println(string);
                    }
                }
            
    }
}
