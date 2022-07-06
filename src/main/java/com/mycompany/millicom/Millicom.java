/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.millicom;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author eliec
 */
public class Millicom {
     
    public static void llamaAPI () throws IOException, ParseException, org.json.simple.parser.ParseException {
  // URL fuente del json
    //Se configura el llamado GET al API
  URL url1 = new URL("https://xkcd.com/1200/info.0.json");
    HttpURLConnection con = (HttpURLConnection) url1.openConnection();
    con.setConnectTimeout(5000);
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");
    con.setRequestMethod("GET");
    con.setDoOutput(true);
    //Se crea un strin builder para tener el Response
        StringBuilder response1 = new StringBuilder();    
        String readLine = null;
        
          int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));

        while ((readLine = in.readLine()) != null) {
            response1.append(readLine);
        } in.close();
    } else {
        System.out.println("GET NOT WORKED");
    }
      //Se parsea el response en Json y se escribe en un file 
        JSONParser parser = new JSONParser(); 
        JSONObject json2 = (JSONObject) parser.parse(response1.toString());
        PrintWriter pw = new PrintWriter("m1.json");
        pw.write(json2.toJSONString());
        pw.flush();
        pw.close();
        
}
   static void opcion2() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException{
       //Se lee el json y se asigna el objeto JSONObject
       Object obj = new JSONParser().parse(new FileReader("m1.json"));
       JSONObject jo = (JSONObject) obj;  
        
       //Covertimos el objeto en Map
       Map allMillicom = ((Map)jo);
      //Despliega lo que hay en el json
        Iterator<Map.Entry> itr = allMillicom.entrySet().iterator();
        System.out.println("------------------------------------------------");       
        System.out.println("Listado de elementos del json");       
        System.out.println("------------------------------------------------");       
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
             System.out.println("------------------------------------------------");            
            System.out.println(pair.getKey() + " : " + pair.getValue() ) ;

        }       
   }
     public static void opcion1 () throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
       
        //Leemos el archivo y lo castiamos a objeto JSONObject 
        Object obj = new JSONParser().parse(new FileReader("m1.json"));   
        JSONObject jo = (JSONObject) obj;
        
       Map allMillicom = ((Map)jo);
      // Ciclo en el objeto solo para ver si toda anda bien
        Iterator<Map.Entry> itr4 = allMillicom.entrySet().iterator();
        while (itr4.hasNext()) {
            Map.Entry pair = itr4.next();
        } 
        
         // obtengo img del json
        String vimg = (String) jo.get("img"); 
        // se busca la imagen en intenet y se graba
        URL url5 = new URL(vimg);
        InputStream in = new BufferedInputStream(url5.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
           out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response15 = out.toByteArray();
        FileOutputStream fos = new FileOutputStream("authorization.png");
        fos.write(response15);
        fos.close();

        // se convierte en base64 y se despliega
        String base64;
        InputStream iSteamReader = new FileInputStream("authorization.png");
        byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
        base64 = Base64.getEncoder().encodeToString(imageBytes);
        System.out.println("Imagen en base64: " + base64);        
      
        
     }
     public static void  menu () throws IOException, FileNotFoundException, org.json.simple.parser.ParseException {
       // Se despliega el MENU   
         System.out.print("MENU\n \n ");
        System.out.print("1.  Presentar de inicio la imagen 'img' actual que viene del objeto.\n ");
        System.out.print("2.  Mapear todo el contenido del JSON y presentar cada campo\n ");
        System.out.print("3.  Salir\n ");
        System.out.print("Entre alguna opcion:");
        Scanner input = new Scanner(System.in);        
        int number = input.nextInt();
        input.close(); 
        
        if (number>3) {
           
            System.out.println("Opcion invalida. Adios.");
        }
       if (number==1) {
            System.out.println("Escogio la opcion 1.");
            opcion1();
        }
       if (number==2) {
            System.out.println("Escogio la opcion 2.");
            opcion2();
        }
       if (number==3) {
            System.out.println("Escogio la opcion 3.");
            System.out.println("Adios.");
        }        
  
     }         
    
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException{
  
        System.out.println("Millicom!");
        llamaAPI();  
        menu ();
       
    }
}
