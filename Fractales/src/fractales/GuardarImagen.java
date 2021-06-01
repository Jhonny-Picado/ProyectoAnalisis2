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

    
    //Constructor de la clase
    public GuardarImagen(){
        
    }
    
    
    //Funcion que guarda una imagen, recibe el nombre de la misma
    public void saveImagen(String nombre) throws IOException {
        
        //Crea un frame de fractales
        JFrame f = new Fractales();
        
        //Hace un nuevo archivo
        File fichero = new File("imagenes//"+nombre+".jpg");
        String formato = "jpg";

        // Creamos la imagen para dibujar en ella.
        BufferedImage imagen = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Hacemos el dibujo
        Graphics g = imagen.getGraphics();
        f.paint(g);

        // Escribimos la imagen en el archivo.
        try {
            
            
            //La binariza para dejarla a blanco y negro
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
