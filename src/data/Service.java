/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Christian Adrian Obando Leiton
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {

    private static String host;
    private static String username;
    private static String password;
    private static String database;
    protected Connection con = null;

    private void jsonParse() throws  IOException {
        try {
            JSONParser parser = new JSONParser();
            InputStream configStream = this.getClass().getResourceAsStream("ConnectionSettings.json");
            
          
            String file = IOUtils.toString(configStream, Charset.defaultCharset());
            
            
            Object obj = parser.parse(file); //the location of the file
            JSONObject jsonObject = (JSONObject) obj;
            host = (String) jsonObject.get("Hostname");
            username = (String) jsonObject.get("Username");
            password = (String) jsonObject.get("Password");
            database = (String) jsonObject.get("DbName");
        }catch(ParseException   e){
        
        }

    }

    protected Connection getConnection() throws IOException{

        try {
            jsonParse();
            System.out.println(host);
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Connection Failed! Check output console " + e.toString());

        }

        return con;
    }

    protected void finalize() {
        try {
            con.close();
        } catch (Exception e) {
            /*este mï¿½todo es llamado por el
			 *garbage collector, por lo tanto
			 *se atrapa la excepciï¿½n pero no se
			 *reporta*/
        }
    }

    private void PrintInputStream(InputStream resourceAsStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void InputStream(InputStream resourceAsStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
