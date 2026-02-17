package com.mycompany.procesadordedatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class GUI extends JFrame implements ActionListener{
    JComboBox Hojas;
    JComboBox Columnas;
    JButton siguiente;
    JButton anterior;
    JLabel pregunta;
    JPanel botones;
    JPanel panelSuperior;
    JPanel panelCentral;
    ImageIcon logo;
    JButton archivo;
    boolean cargando;
    Lector lector;
    public GUI () {
        
        //Labels del panel superior

        pregunta = new JLabel();
        pregunta.setPreferredSize(new Dimension (200,50));
        pregunta.setBackground(Color.cyan);
        pregunta.setVerticalAlignment(JLabel.CENTER);
        pregunta.setHorizontalAlignment(JLabel.CENTER);
        pregunta.setOpaque(true);
        
         Hojas = new JComboBox();
         Hojas.addActionListener(this);
         Hojas.setVisible(false);
         
         Columnas = new JComboBox();
         Columnas.addActionListener(this);
         Columnas.setVisible(false);
         
         archivo = new JButton();
         
         archivo.setText("Selecciona el archivo");
         archivo.addActionListener(this);
         
         siguiente = new JButton();
         
         siguiente.setText("siguiente");
         siguiente.addActionListener(this);
         
         
         anterior = new JButton ();
         
         anterior.setText("anterior");
         anterior.addActionListener(this);
         
         botones = new JPanel();
         botones.setBackground(Color.magenta);
         botones.add(siguiente);
         botones.add(anterior);
         botones.setOpaque(true);
         
        
        // Panel Superior
        panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.red);
        panelSuperior.setPreferredSize(new Dimension(500,100));
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 25));
        panelSuperior.add(pregunta);
        panelSuperior.add(archivo);
        panelSuperior.add(Hojas);
        panelSuperior.add(Columnas);
        panelSuperior.add(botones);
        
        //Panel Central
        panelCentral = new JPanel();
        panelCentral.setBackground(Color.blue);
        panelCentral.setPreferredSize(new Dimension(500,400));
        
        // Frame
        logo = new ImageIcon(getClass().getResource("/LogoChapingo.png"));
        this.setIconImage(logo.getImage());
        this.add(panelCentral,BorderLayout.CENTER);
        this.add(panelSuperior,BorderLayout.NORTH);
        this.setSize(700,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
 
        this.setVisible(true);

    }

    public void setHojas(ArrayList<String> arreglo) {
        cargando = true;
        this.Hojas.removeAllItems();
        for(String item : arreglo){
            Hojas.addItem(item);
        }
        cargando = false;
    }
    
    public void setColumnas(ArrayList<String>arreglo){ 
        cargando = true;
        this.Columnas.removeAllItems();
        for(String item : arreglo) {
            Columnas.addItem(item);
        }
        cargando = false;
    }
    
    public void setPregunta(String nuevaPregunta) {
        this.pregunta.setText(nuevaPregunta);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Hojas) {
            if (cargando) return;
            this.setColumnas(lector.getColumnas(Hojas.getSelectedItem().toString()));
            Columnas.setVisible(true);
        }
        if(e.getSource()==Columnas) {
            if(cargando) return;
            System.out.println(lector.getConteos(Hojas.getSelectedItem().toString(), Columnas.getSelectedIndex()));
        }
        if (e.getSource() == siguiente) {
            System.out.println("Siguiente");
        }
        if (e.getSource() == anterior) {
            System.out.println("anterior");
        }
        if (e.getSource() == archivo) {
            JFileChooser chooser = new JFileChooser();
            int resultado = chooser.showOpenDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                lector = new Lector(chooser.getSelectedFile().getAbsolutePath());
                this.setHojas(lector.getHojas());
                Hojas.setVisible(true);
            }
        }
    }
}
