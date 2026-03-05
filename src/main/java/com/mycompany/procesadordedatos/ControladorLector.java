package com.mycompany.procesadordedatos;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ControladorLector {
    @PostMapping("/hojas")
    public List<String> getHojas (@RequestParam("archivo")String archivo) throws IOException{
        Lector lector = new Lector(archivo);
        List <String> hojas = lector.getHojas();
        lector.close();
        return hojas;
    }
    @PostMapping("/columnas")
    public List<String> getColumnas (@RequestParam("archivo")String archivo,@RequestParam("hoja") String hojaElegida) throws IOException{
        Lector lector = new Lector(archivo);
        List<String> columnas = lector.getColumnas(hojaElegida);
        lector.close();
        return columnas;
    }
    @PostMapping("/conteos")
    public Map<String, Integer> getConteos(@RequestParam("archivo") String archivo,
                                           @RequestParam("hoja") String hoja,
                                           @RequestParam("columna") int columna) throws IOException {
        Lector lector = new Lector(archivo);
        Map<String, Integer> conteos = lector.getConteos(hoja, columna);
        lector.close();
        return conteos;
    }
}
