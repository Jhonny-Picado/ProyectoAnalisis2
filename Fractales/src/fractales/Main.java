/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

//import static fractales.Fractales.fun;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author jocxa
 */
public class Main {
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        VistaI n = new VistaI();
          
        /*
        //Crea un frame de fractales
        double[] dl = new double [2];
        dl[0]= 16;
        dl[1]= 54;
        
        double[] dd = new double [2];
        dd[0]= 26;
        dd[1]= 45;
        
        double[] an = new double [2];
        an[0]= 34;
        an[1]= 45;
        
        int [] r= new int[2];
        r[0] = 3;
        r[1] = 5;
        
        for(int i = 0; i<10; i++){
            JFrame f = new Fractales(6, 22, 16, dl, dd, r, an);
            f.setVisible(true);
            
            //Salva las imagenes
            GuardarImagen imagen = new GuardarImagen();
            imagen.saveImagen("imagenes\\","Hola",f);//Integer.toString(i),f);
        }

        CargarImagen d = new CargarImagen();
    
        d.GenerarMatriz("imagenes\\", "prueba3.jpg");
        */
        //Genetico n = new Genetico();
        //n.poblacionInicial();
        //n.seleccion();

    }  
}
