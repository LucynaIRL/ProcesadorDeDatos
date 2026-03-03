package com.mycompany.procesadordedatos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ControladorLector {
    @PostMapping("/hojas")
    public List<String> getHojas (@RequestParam("archivo")MultipartFile archivo) throws IOException{
        File temp = File.createTempFile("TempExcel", ".xlsx");
        archivo.transferTo(temp);
        Lector lector = new Lector(temp);
        List <String> hojas = lector.getHojas();
        lector.close();
        temp.delete();
        return hojas;
    }
    @PostMapping("/columnas")
    public List<String> getColumnas (@RequestParam("archivo")MultipartFile archivo,@RequestParam("hoja") String hojaElegida) throws IOException{
        File temp = File.createTempFile("tempExcel", ".xlsx");
        archivo.transferTo(temp);
        Lector lector = new Lector(temp);
        List<String> columnas = lector.getColumnas(hojaElegida);
        lector.close();
        temp.delete();
        return columnas;
    }
    @PostMapping("/conteos")
    public Map<String, Integer> getConteos(@RequestParam("archivo") MultipartFile archivo,
                                           @RequestParam("hoja") String hoja,
                                           @RequestParam("columna") int columna) throws IOException {
        File temp = File.createTempFile("tempExcel", ".xlsx");
        archivo.transferTo(temp);
        Lector lector = new Lector(temp);
        Map<String, Integer> conteos = lector.getConteos(hoja, columna);
        lector.close();
        temp.delete();
        return conteos;
    }
}
