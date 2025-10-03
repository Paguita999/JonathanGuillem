/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ad.ieseljust.monaco2017_schedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
/**
 *
 * @author jasb
 */
public class Monaco2017_schedule {

    public Document OpenXML(String name) throws IOException, SAXException, ParserConfigurationException, FileNotFoundException {

        // Create an instance of DocumentBuilderFactory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // Using the DocumentBuilderFactory instance we create a DocumentBuilder
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //And we use DocumentBuilder's "parse" method to get the document
        Document doc = dBuilder.parse(new File(name));

        return doc;
    }

    public void carregaXML(String filename) {
        
        try {
            
            Document root = OpenXML(filename);
            
            NodeList resultatsPilots = root.getElementsByTagName("Result");
             
             //Recorrer els resultats dels pilots i crear instancies de ResultadoCarrera. Posteriorment mostrar el contingut amb el mètode toString de ResultadoCarrera(). 
             
                                                
            
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            System.out.println("Error:" + ex.getMessage());
        }

    }

    public ArrayList<ResultadoCarrera> carregaResultatsXML(String nomXML) {
        ArrayList<ResultadoCarrera> elsResultats = new ArrayList<>();
        try {
            Document arrel = OpenXML(nomXML);
            NodeList resultatsPilots = arrel.getElementsByTagName("Result");
            for (int i = 0; i < resultatsPilots.getLength(); i++) {
                Element resultatElement = (Element) resultatsPilots.item(i);
                ResultadoCarrera resultat = new ResultadoCarrera(resultatElement);
                elsResultats.add(resultat);
            }
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        return elsResultats;
    }

    public static void main(String[] args) {
        // Crear l'app a partir de la classe i cridar a carregaXML amb el nom de l'arxiu .xml que retornarà
        // (funció ArrayList<ResultadoCarrera> carregaResultatsXML(String nomXML))
        //      - Carregar en memòria el .xml  (openXML)
        //      - Obtindre el NodeList dels "Results", recórrer-los i crear un objecte ResultadoCarrera passant-li l'item (Element) del NodeList. 
        //      - afegir-lo a una ArrayList ArrayList<ResultadoCarrera> elsResultats = new ArrayList() que retornarem;

        // Exemple d'ús de la funció carregaResultatsXML
        Monaco2017_schedule app = new Monaco2017_schedule();
        ArrayList<ResultadoCarrera> elsResultats = app.carregaResultatsXML("monaco_2017.xml");
        for (ResultadoCarrera resultat : elsResultats) {
            System.out.println(resultat);
        }

        app.carregaXML("monaco_2017.xml");

    }
}
