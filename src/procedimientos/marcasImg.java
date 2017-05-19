/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedimientos;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author alex
 */
public class marcasImg {
    
    public static void marcarImagen(File origen, File destino, String marca, Color color, Float transparencia)
    {
       
    
        
        
        //Obtengo la extension del fichero 
        try {
            
        String extension = "";
        int i = origen.getName().lastIndexOf('.');
        if (i > 0) {
        extension = origen.getName().substring(i+1).toLowerCase();
        }
            
            
        BufferedImage sourceImage = ImageIO.read(origen);
    
        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
 
        // inicializo las propiedades de fuentes, color, transparencia
       // AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparencia);
        g2d.setComposite(alphaChannel);
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(marca, g2d);
 
        // calculo las coordenadas donde poner la marca en este caso la centro
        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;
 
        // dibujo la marca
        g2d.drawString(marca, centerX, centerY);
        
 
        ImageIO.write(sourceImage, extension, destino);
        g2d.dispose();
        JOptionPane.showMessageDialog(null, "Mark was added.");
 
        System.out.println("Todo correcto");
        } catch (IOException ex) {
               System.err.println(ex);
               JOptionPane.showMessageDialog(null, "Error: You MUST select valid data in every field", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
}
