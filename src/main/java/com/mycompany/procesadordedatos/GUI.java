package com.mycompany.procesadordedatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class GUI extends JFrame{
    public GUI () {
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.red);
        panelSuperior.setPreferredSize(new Dimension(500,100));
        panelSuperior.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.blue);
        
        this.add(panelCentral,BorderLayout.CENTER);
        this.add(panelSuperior,BorderLayout.NORTH);
        this.setSize(500,500);
        
        
        
 
        this.setVisible(true);

    }
}
