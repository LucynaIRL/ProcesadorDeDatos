package com.mycompany.procesadordedatos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
public class Lector {
    
    File archivo;
    ReadOnlySharedStringsTable sst;
    InputStream sheetStream;
    // Constructor que recibe la ruta del archivo excel a leer
    public Lector(File archivo){
        this.archivo = archivo;
    }
    
    // Obtener un arreglo con los nombres de las hojas
    public List getHojas ()  {
        List<String> hojas = new ArrayList<>();
        try (OPCPackage paquete = OPCPackage.open(archivo)){
            XSSFReader lector = new XSSFReader (paquete);
            XSSFReader.SheetIterator iterador = (XSSFReader.SheetIterator) lector.getSheetsData();
            while (iterador.hasNext()) {
                iterador.next();
                hojas.add (iterador.getSheetName());
            }
        } catch (InvalidFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (OpenXML4JException ex) {
            ex.printStackTrace();
        }
            return hojas;
    } 
    
    //Obtener un arreglo con los nombres de las columnas
    public List<String> getColumnas(String hojaElegida) {
        List<String> columnas = new ArrayList<>();
        
        
        try (OPCPackage paquete = OPCPackage.open(archivo)) {
            XSSFReader lector = new XSSFReader (paquete);
            StylesTable estilos = lector.getStylesTable();
            sst = new ReadOnlySharedStringsTable(paquete);
            //Buscamos la hoja primero
            XSSFReader.SheetIterator iterador = (XSSFReader.SheetIterator) lector.getSheetsData();
            InputStream hoja = null;
            while(iterador.hasNext()) {
                InputStream is = iterador.next();
                if (iterador.getSheetName().equals(hojaElegida)) {
                    hoja = is;
                } else is.close();
            }
            //Hoja encontrada (InputStream)
            //Procesamos la hoja
            
            XMLReader parser = XMLHelper.newXMLReader();
            SheetContentsHandler handler = new SheetContentsHandler() {
                @Override
                public void startRow(int i) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void endRow(int i) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void cell(String string, String string1, XSSFComment xssfc) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
                
            };
            
        } catch (InvalidFormatException ex) {
            ex.printStackTrace();
        } catch (IOException | SAXException | ParserConfigurationException | OpenXML4JException ex) {
            ex.printStackTrace();
        }
        
        return columnas;
    }
    // Obtener un HashMap que cuenta
    public Map getConteos(String hojaElegida, int columnaElegida) {
        Map<String, Integer> contador = new HashMap <>();
        
        return contador;
    }
}
