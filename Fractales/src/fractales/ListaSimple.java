/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

/**
 *
 * @author jocxa
 */
public class ListaSimple {
    //Atributos de la lista
    public Nodo primerNodo, ultimoNodo;
    public String categoria;
    
    
    //Constructor de la lista
    public ListaSimple(String cat){
        this.primerNodo=null;
        this.categoria= cat;
    }
    
    
    //Metodo que inserta un nuevo nodo al final de la lista
    public void insertar(Nodo nuevo){
        
        if(primerNodo==null){
            this.primerNodo=this.ultimoNodo=nuevo;
        }
        else{
            this.ultimoNodo.siguiente = nuevo;
            this.ultimoNodo= nuevo;
        }   
    }

    //Metodo que imprime la lista en consola
    public void imprimir(){
        
        Nodo tmp = this.primerNodo;
        
        
 
        while(tmp!=null){
            System.out.print(tmp.contador +": ");
            //System.out.print(tmp.angulo[0]);
            //System.out.print(" - ");
            //System.out.println(tmp.angulo[1]);
            tmp= tmp.siguiente;
        }
    }
    
    //Funcion que retorna el largo de la lista
    public int largoLista(){
        int cant=0;
        Nodo tmp = this.primerNodo;

        while(tmp!=null){
            cant +=1;
            tmp= tmp.siguiente;
        }

        return cant;
    }
    
    
    public void limpiar(){
         Nodo tmp = this.primerNodo;
        
         if(this.primerNodo==this.ultimoNodo){
             this.primerNodo=this.ultimoNodo=null;
         }
         
         else{
            while(tmp!=null){
                tmp= tmp.siguiente;
                this.primerNodo=null;
                this.primerNodo=tmp;
            }
            
            this.ultimoNodo=this.primerNodo;
         
         }
         
 
    }
    
    //Metodo que busca por posicion en la lista
    public Nodo BuscarPosicion(int index){
        
        Nodo tmp= this.primerNodo;
        while(tmp!=null){
            
            if(tmp.contador==index) return tmp;
            
            tmp=tmp.siguiente;
        }
        
        //Aca no llega nunca
        return null;
    }
    
}
