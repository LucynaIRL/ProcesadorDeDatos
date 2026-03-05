package com.mycompany.procesadordedatos;
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
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
public class Lector {
    private static class StopParsingException extends RuntimeException {}
    String archivo;
    private OPCPackage paquete;
    // Constructor que recibe la ruta del archivo excel a leer
    public Lector(String archivo){
        this.archivo = archivo;
        try {
            this.paquete = OPCPackage.open(archivo);
        } catch (InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }
    
    // Obtener un arreglo con los nombres de las hojas
    public List getHojas ()  {
        List<String> hojas = new ArrayList<>();
        try {
            XSSFReader lector = new XSSFReader (paquete);
            XSSFReader.SheetIterator iterador = (XSSFReader.SheetIterator) lector.getSheetsData();
            while (iterador.hasNext()) {
                iterador.next();
                hojas.add (iterador.getSheetName());
            }

        } catch (IOException | OpenXML4JException ex) {
            ex.printStackTrace();
        } 
                    return hojas;
    }
    //Obtener un arreglo con los nombres de las columnas
    public List<String> getColumnas(String hojaElegida) {
        List<String> columnas = new ArrayList<>();
        
        
        try{
            XSSFReader lector = new XSSFReader (paquete);
            StylesTable estilos = lector.getStylesTable();
            ReadOnlySharedStringsTable sst = new ReadOnlySharedStringsTable(paquete);
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
                
                private boolean isFirstRow = false;
                @Override
                public void startRow(int rowNum) {
                    isFirstRow = rowNum == 0;
                }

                @Override
                public void endRow(int i) {
                    if (i==0) {
                        throw new StopParsingException();
                    }
                }

                @Override
                public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                    if (isFirstRow) {
                        columnas.add(formattedValue);
                    }
                }
                
            };
            ContentHandler contentHandler = new XSSFSheetXMLHandler(estilos,sst, handler,new DataFormatter(), false);
            parser.setContentHandler(contentHandler);
            parser.parse(new InputSource(hoja));
            hoja.close();
        } catch (StopParsingException e) {
            
        }
        catch (IOException | ParserConfigurationException | OpenXML4JException | SAXException e) {
            e.printStackTrace();
        }
        return columnas;
    }
    // Obtener un HashMap que cuenta
    public Map getConteos(String hojaElegida, int columnaElegida) {
        Map<String, Integer> contador = new HashMap <>();
        
        try {
            XSSFReader lector = new XSSFReader(paquete);
            StylesTable estilos = lector.getStylesTable();
            ReadOnlySharedStringsTable sst = new ReadOnlySharedStringsTable(paquete);
            
            XSSFReader.SheetIterator iterador = (XSSFReader.SheetIterator) lector.getSheetsData();
            InputStream hoja = null;
            
            while(iterador.hasNext()) {
                InputStream is = iterador.next();
                if(hojaElegida.equals(iterador.getSheetName())) {
                    hoja = is;
                    break;
                } else is.close();
            }
            XMLReader parser =  XMLHelper.newXMLReader();
            SheetContentsHandler handler = new SheetContentsHandler() {
                private int filaActual = 0;
                @Override
                public void startRow(int rowNum) {
                    filaActual=rowNum;
                }

                @Override
                public void endRow(int rowNum) {
                    
                }

                @Override
                public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                    int columnaActual = new CellReference(cellReference).getCol();
                    if (filaActual == 0) return;
                    if (columnaActual == columnaElegida) {
                        contador.put(formattedValue,contador.getOrDefault(formattedValue, 0)+1);
                    }
                }
                
            };
            ContentHandler contentHandler = new XSSFSheetXMLHandler(estilos,sst,handler,new DataFormatter(),false);
            parser.setContentHandler(contentHandler);
            parser.parse(new InputSource(hoja));
            hoja.close();
        } catch (IOException | ParserConfigurationException | OpenXML4JException | SAXException e) {
            e.printStackTrace();
        }
        
        return contador;
    }
    // Cerrar el archivo
    public void close () {
        if (paquete != null){
            try {
                paquete.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
