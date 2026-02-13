package com.mycompany.procesadordedatos;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
public class GUI extends JFrame{
    public GUI () {
        //label
        ImageIcon shaw = new ImageIcon(getClass().getResource("/Shaw.jpg")); 
        JLabel label = new JLabel();
        Border border = BorderFactory.createLineBorder(Color.cyan,20,false); // Create a border (Don't forget to add it to the label) (Color,thickness, rounded)
        label.setText("Texo superbomba"); 
        label.setIcon(shaw);
        label.setHorizontalTextPosition(JLabel.CENTER);   //Set text position relative to the icon (LEFT,RIGHT, CENTER)
        label.setVerticalTextPosition(JLabel.TOP);    //Set text position relative to the icon (TOP, BOTTOM, CENTER)
        label.setForeground(Color.BLUE); //Set text color
        label.setFont(new Font ("Cambria",Font.PLAIN,14)); // Set font (Needs size and style)
        label.setIconTextGap(50); // Distance between text and icon, negative numbers set it on the other direction
        label.setBackground(Color.magenta); // Set background color
        label.setOpaque(true); // Make the label visible (mainly background)
        label.setBorder(border); //Add border to the label
        label.setVerticalAlignment(JLabel.CENTER); //Set vertical position of icon + text within label
        label.setHorizontalAlignment(JLabel.CENTER); //Set horizontal position of icon + text within label
        label.setBounds(150, 150, 500, 500);
                
        //Frames
        this.add(label); // agregar el label a la ventana
        this.setSize(800,800);
        this.setTitle("Procesador de Datos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true); //para poder cambiar el tamaño
        this.setLayout(null); // Eliminar el layout existente
        this.setVisible(true); // hacer visible (Primero tiene que haber todo agregado y cargado)
        ImageIcon imagen = new ImageIcon(getClass().getResource("/LogoChapingo.png")); //crear un icono
        this.setIconImage(imagen.getImage()); // Obtener la imagen de un objeto "ImageIcon"
 

    }
}
