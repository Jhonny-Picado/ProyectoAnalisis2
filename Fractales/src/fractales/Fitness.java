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
    String ruta;
    
    //Constructor de la clase
    public Fitness(String rutas){
        this.ruta=rutas;
    }

    
    //Algoritmo de fitness, recibe los nombre de las imagenes a comparar y devuelve un boolean
    public float[] Algoritmo(String arbol, String imagen) throws FileNotFoundException{
        
        //Inicia un contador en cero
        float [] count= new float[3];
        count[0]=0;
        count[1]=0;
        count[2]=0;
        
        //Instancia un objeto para cargar las matrices
        CargarImagen c = new CargarImagen();
        int [][] mArbol = c.GenerarMatriz(this.ruta + arbol);
        int [][] mImagen = c.GenerarMatriz(imagen);

        //Itera sobre las matrices de las imagenes
        for (int i=0; i<mArbol.length; i++){
            
            for (int j=0; j<mArbol[0].length; j++){

                //Valida si los pixeles son iguales, en dado caso aumenta el contador
                if(mImagen[i][j]==mArbol[i][j] && mImagen[i][j]==0){
                    count[0]++;
                } else if (mImagen[i][j]==mArbol[i][j]){
                    count[1]++;
                }
                
                if(mArbol[i][j]==0){
                    count[2]++;
                }
                
            }
        }

        //Retorna el contador de similitudes
        return count; 
    }
    
    
    //Retorna la cantidad de pixeles de la silueta del arbol
    public float[] PixelesArbol(String arbol) throws FileNotFoundException{
        
         //Inicia un contador en cero
        float [] count = new float [2];
        count[0]=0;
        count[1]=0;
        
        //Instancia un objeto para cargar las matrices
        CargarImagen c = new CargarImagen();
        int [][] mArbol = c.GenerarMatriz(arbol);
        
        
        //For que recorre la matriz del arbol y va sumando cada vez que se coloca en un pixel de su silueta
        for(int i=0; i<mArbol.length; i++){
            
            for(int j=0; j<mArbol[0].length; j++){
                
                if(mArbol[i][j] == 0){
                    count[0]++;
                }
                else
                    count[1]++;
            }
        }
        
        return count;
    }
    
}
