/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import java.io.FileNotFoundException;

/**
 *
 * @author Jhonny Picado
 */
public class Fitness {
    
    
    //Constructor de la clase
    public Fitness(){
        
    }

    
    //Algoritmo de fitness, recibe los nombre de las imagenes a comparar y devuelve un boolean
    public int Algoritmo(String arbol, String imagen) throws FileNotFoundException{
        
        
        //Inicia un contador en cero
        int count=0;
        
        //Instancia un objeto para cargar las matrices
        CargarImagen c = new CargarImagen();
        int [][] mArbol = c.GenerarMatriz(arbol);
        int [][] mImagen = c.GenerarMatriz(imagen);
        
        
        //Itera sobre las matrices de las imagenes
        for (int i=0; i<mArbol.length; i++){
            
            for (int j=0; j<mArbol[0].length; j++){
                //System.out.print(m[i][j]);
                
                //Valida si los pixeles son iguales, en dado caso aumenta el contador
                if(mImagen[i][j]==mArbol[i][j]){
                    count++;
                }
                
            }
        }
        
        //Retorna el contador de similitudes
        return count;
        
    }
    
}
