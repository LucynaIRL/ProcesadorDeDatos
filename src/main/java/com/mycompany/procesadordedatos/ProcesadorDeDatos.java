package com.mycompany.procesadordedatos;
public class ProcesadorDeDatos {

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Planeación\\OneDrive - Universidad Autónoma Chapingo\\Documentos\\Servicio Social Edgar\\Prueba Excel.xlsx";
        String hojaElegida = "Hoja1";
        int columnaElegida =0;

        Lector lector = new Lector(ruta);
        System.out.println(lector.getConteos(hojaElegida, columnaElegida));        
        GUI gui = new GUI();
        
    }
}
