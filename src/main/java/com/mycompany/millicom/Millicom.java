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

    URL url1 = new URL("https://xkcd.com/1200/info.0.json");
    HttpURLConnection con = (HttpURLConnection) url1.openConnection();
    con.setConnectTimeout(5000);
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");
   // con1.setRequestProperty("Ocp-Apim-Subscription-Key", "d8f1102b587f4a3b888d657b00ef2910");
    con.setRequestMethod("GET");
    con.setDoOutput(true);
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
       
        JSONParser parser = new JSONParser(); 
        JSONObject json2 = (JSONObject) parser.parse(response1.toString());
        PrintWriter pw = new PrintWriter("m1.json");
        pw.write(json2.toJSONString());
        
        pw.flush();
        pw.close();
}
   static void opcion2() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException{
       Object obj = new JSONParser().parse(new FileReader("m1.json"));
       JSONObject jo = (JSONObject) obj;  
         
       Map allMillicom = ((Map)jo);
      //Despliega lo que hay en el json
        Iterator<Map.Entry> itr = allMillicom.entrySet().iterator();
        System.out.println("------------------------------------------------");       
        System.out.println("Listado de elemntos del json");       
        System.out.println("------------------------------------------------");       
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
             System.out.println("------------------------------------------------");            
            System.out.println(pair.getKey() + " : " + pair.getValue() ) ;

        }       
   }
     public static void opcion1 () throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
         //Llamamos el API y creamos un archivo
        
        //Leemos el archivo y lo castiamos a objeto JSONObject 
        Object obj = new JSONParser().parse(new FileReader("m1.json"));   
        JSONObject jo = (JSONObject) obj;
        
       Map allMillicom = ((Map)jo);
      // Ciclo en el objeto solo para ver si toda anda bien
        Iterator<Map.Entry> itr4 = allMillicom.entrySet().iterator();
        while (itr4.hasNext()) {
            Map.Entry pair = itr4.next();
           // System.out.println(pair.getKey() + " : " + pair.getValue());
        } 
        
         // obtengo img del json
        String vimg = (String) jo.get("img"); 
        //System.out.println(vimg);
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
        System.out.print("MENU\n \n ");
        System.out.print("1.  Presentar de inicio la imagen 'img' actual que viene del objeto.\n ");
        System.out.print("2.  Mapear todo el contenido del JSON y presentar cada campo\n ");
        System.out.print("3.  Salir\n ");
        System.out.print("Entre alguna opcion:");
        Scanner input = new Scanner(System.in);        
        int number = input.nextInt();
       // System.out.println("You entered " + number);
        // closing the scanner object
        input.close(); 
        
        if (number>3) {
           
            System.out.println("Opcion invalida. Adios.");
            //Scanner input1 = new Scanner(System.in);        
            //int number1 = input1.nextInt();
            //input1.close(); 
          // int number = input.nextInt();
        }
       if (number==1) {
            System.out.println("Escogio la opcion 1.");
            opcion1();
        }
       if (number==2) {
            System.out.println("Escogio la opcion 1.");
            opcion2();
        }
        
  
     }         
    
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException{
  
        System.out.println("Millicom!");
        llamaAPI();  
      menu ();
  
        
       
    }
}
