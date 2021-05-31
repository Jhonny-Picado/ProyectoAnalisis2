/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractales;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Jhonny
 */
public class GuardarImagen {

    public GuardarImagen(){
        
    }
    
    public void saveImagen(String nombre) throws IOException {
        JFrame f = new Fractales();

        File fichero = new File(nombre+".jpg");
        String formato = "jpg";

        // Creamos la imagen para dibujar en ella.
        BufferedImage imagen = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Hacemos el dibujo
        Graphics g = imagen.getGraphics();
        f.paint(g);

        // Escribimos la imagen en el archivo.
        try {

            ImageIO.write(imagen, formato, fichero);
            
            Imagen obj = new Imagen(nombre+".jpg");
            obj.binarizarImagen(50);
            BufferedImage img = obj.imprimirImagen();
            
            ImageIO.write(img, formato, fichero);
        } catch (IOException e) {
            System.out.println("Error de escritura");
        }
    }
    
}