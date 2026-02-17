package com.mycompany.procesadordedatos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.IOException;
public class Lector {
    
    FileInputStream fis;
    Workbook workbook;

    // Constructor que recibe la ruta del archivo excel a leer
    public Lector(String ruta) {
        try {
            this.fis = new FileInputStream(ruta);
            this.workbook = new XSSFWorkbook(fis);
        } catch (IOException ex) {
            System.getLogger(Lector.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }
    
    // Obtener un arreglo con los nombres de las hojas
    public ArrayList getHojas () {
        ArrayList<String> hojas = new ArrayList<>();
        for (int i = 0;i<workbook.getNumberOfSheets();i++){
            hojas.add(workbook.getSheetName(i));
        }
        return hojas;
    } 
    
    //Obtener un arreglo con los nombres de las columnas
    public ArrayList<String> getColumnas(String hojaElegida) {
        ArrayList<String> columnas = new ArrayList<>();
        Sheet sheet = workbook.getSheet(hojaElegida);
        Row fila = sheet.getRow(0);
        for (Cell celda : fila) {
            columnas.add(celda.toString());
        }
        return columnas;
    }
    // Obtener un HashMap que cuenta
    public Map getConteos(String hojaElegida, int columnaElegida) {
        Map<String, Integer> contador = new HashMap <>();
        Sheet hoja = workbook.getSheet(hojaElegida);
        
        for (int i = 1; i < hoja.getLastRowNum()+1;i++) {
            
            Row fila = hoja.getRow(i);
            if(fila == null) continue;
            Cell clave = fila.getCell(columnaElegida);
            if(clave == null) continue;
            
            contador.put(clave.toString(), contador.getOrDefault(clave.toString(), 0)+1);
        }
    return contador;
    }
}
