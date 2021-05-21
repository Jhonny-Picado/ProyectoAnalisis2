/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Jhonny Picado
 */
public class CargarImagen {
    
    
    //https://www.youtube.com/watch?v=ih_4qGlh0-Y
    public CargarImagen(){       
    }
    
    
    public int[][] GenerarMatriz(String nombre) throws FileNotFoundException{
        
        
        
        int [][] matriz=null;
        
        
        try {
            InputStream entrada =  new FileInputStream("imagenes\\arbol5.png");
            
            ImageInputStream imagenEntrada =  ImageIO.createImageInputStream(entrada);
            BufferedImage imagen = ImageIO.read(imagenEntrada);
            
            
            int filas = imagen.getHeight();
            int columnas = imagen.getWidth();
            
            int [][] tmp = new int [filas][columnas];
            
            int gg;
            for(int i=0; i<filas; i++){
                
                for(int j=0; j<columnas; j++){
                    
                    gg=imagen.getRGB(j, i);
                    
                    if(gg != 0){
                        tmp[i][j] = 1;
                    }
                    if (gg == 0 || gg == -1){
                        tmp[i][j] = 0;
                    } 
                }
                
            }
            matriz = tmp;
        } catch (IOException ex) {
            Logger.getLogger(CargarImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matriz;
    }
    
}
