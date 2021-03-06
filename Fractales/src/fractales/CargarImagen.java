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
    
    
    public int[][] GenerarMatriz(String ruta) throws FileNotFoundException{
        
        int [][] matriz=null;
        
        try {
            InputStream entrada =  new FileInputStream(ruta);
            
            ImageInputStream imagenEntrada =  ImageIO.createImageInputStream(entrada);
            BufferedImage imagen = ImageIO.read(imagenEntrada);
            
            int [][] tmp = new int [imagen.getHeight()][imagen.getWidth()];
            
            for(int i=0; i<imagen.getHeight(); i++){
            
                for(int j=0; j<imagen.getWidth(); j++){
                    
                    if(imagen.getRGB(j, i)== -1){
                        tmp[i][j]=0;
                        //System.out.print(0);
                    }
                        
                    else{
                        tmp[i][j]=1;
                        //System.out.print(1);
                    }
                }
                //System.out.println();
            }
            
            matriz = tmp;
            
        } catch (IOException ex) {
            Logger.getLogger(CargarImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matriz;
    }
    
}
