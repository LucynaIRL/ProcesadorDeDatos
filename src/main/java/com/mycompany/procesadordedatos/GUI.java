package com.mycompany.procesadordedatos;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class GUI extends JFrame{
    public GUI () {
        //label
        ImageIcon shaw = new ImageIcon(getClass().getResource("/Shaw.jpg")); 
        JLabel label = new JLabel();
        label.setText("Texo superbomba"); 
        label.setIcon(shaw);
        
        //Frames
        this.add(label); // agregar el label a la ventana
        this.setSize(1280,720);
        this.setTitle("Procesador de Datos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true); //para poder cambiar el tamaño
        
        this.setVisible(true); // hacer visible (Primero tiene que haber todo agregado y cargado)
        ImageIcon imagen = new ImageIcon(getClass().getResource("/LogoChapingo.png")); //crear un icono
        this.setIconImage(imagen.getImage()); // Obtener la imagen de un objeto "ImageIcon"
 

    }
}
