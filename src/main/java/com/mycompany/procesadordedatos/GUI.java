package com.mycompany.procesadordedatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
public class GUI extends JFrame implements ActionListener{
    //ComboBoxes
    JComboBox Hojas;
    JComboBox Columnas;
    //Botones
    JButton archivo;
    //Contenedores
    JPanel panelSuperior;
    JPanel panelCentral;
    ScrollPane scrollpane;
    //Tabla
    JTable tabla;
    DefaultTableModel modelo;
    //Logo
    ImageIcon logo;
    //Variables
    boolean cargando;
    //Clases del proyecto
    Lector lector;
    
    
    
//Cionstructor de la GUI
    public GUI () {
//Componentes panel superior

//Boton selector de archivos
         archivo = new JButton();
         archivo.setText("Selecciona el archivo");
         archivo.addActionListener(this);
         
//Boton para elegir la hoja a leer
         Hojas = new JComboBox();
         Hojas.addActionListener(this);
         Hojas.setVisible(false);
         
// Columnas a elegir
         Columnas = new JComboBox();
         Columnas.addActionListener(this);
         Columnas.setVisible(false);

//Creación del Panel Superior
        panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(1,51,101));
        panelSuperior.setPreferredSize(new Dimension(500,100));
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 25));

// adds del Panel Superior
        panelSuperior.add(archivo);
        panelSuperior.add(Hojas);
        panelSuperior.add(Columnas);
        
//Creación del Panel Central
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        
// Creación del Default Table Model para rellenar la tabla
        modelo = new DefaultTableModel();
        modelo.addColumn("Valor");
        modelo.addColumn("Repeticiones");
        
// Creación de la JTable
        tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
//Creación del contenedor de la tabla
        scrollpane = new ScrollPane();
        scrollpane.setVisible(false);
        scrollpane.add(tabla);
        panelCentral.add(scrollpane,BorderLayout.CENTER);
        
// Frame
        logo = new ImageIcon(getClass().getResource("/LogoChapingo.png"));
        this.setIconImage(logo.getImage());
        this.setTitle("Procesador de Excel");
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// adds del frame
        this.add(panelCentral,BorderLayout.CENTER);
        this.add(panelSuperior,BorderLayout.NORTH);
        this.setVisible(true);

    }
    
// función para agregar las hojas leídas al combobox hojas
    public void setHojas(ArrayList<String> arreglo) {
        cargando = true;
        this.Hojas.removeAllItems();
        for(String item : arreglo){
            Hojas.addItem(item);
        }
        cargando = false;
    }
    
// función para agregar las columnas leídas al combobox columnas
    public void setColumnas(ArrayList<String>arreglo){ 
        cargando = true;
        this.Columnas.removeAllItems();
        for(String item : arreglo) {
            Columnas.addItem(item);
        }
        cargando = false;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
// Seleccionar archivo a leer
        if (e.getSource() == archivo) {
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Excel files","xlsx");
            chooser.setFileFilter(filter);
            int resultado = chooser.showOpenDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                lector = new Lector(chooser.getSelectedFile().getAbsolutePath());
                this.setHojas(lector.getHojas());
                Hojas.setVisible(true);
                Columnas.setVisible(false);
            }
        }
        
// Leer la hoja elegida y obtener las columnas disponibles
        if (e.getSource() == Hojas) {
            if (cargando) return;
            this.setColumnas(lector.getColumnas(Hojas.getSelectedItem().toString()));
            Columnas.setVisible(true);
            
        }
        
// Leer la columna elegida y generar una tabla
        if(e.getSource()==Columnas) {
            tabla.setEnabled(true);
            if(cargando) return;
            modelo.setRowCount(0);
            Map<String, Integer> conteos = lector.getConteos(Hojas.getSelectedItem().toString(), Columnas.getSelectedIndex());
            for (Map.Entry<String,Integer> entry : conteos.entrySet()) {
                modelo.addRow(new Object[] {entry.getKey(),entry.getValue()});
            }
            tabla.setEnabled(false);
            scrollpane.setVisible(true);
        }
        
    }
}
