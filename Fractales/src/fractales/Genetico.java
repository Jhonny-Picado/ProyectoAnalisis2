/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import static fractales.Fractales.RandomDouble;
import static fractales.Fractales.RandomInt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author jocxa
 */
public class Genetico {
    
    //estrechar los tangos
    //explicar el codigo
    
    //Variables globales para el programa genético
    ArrayList<ListaSimple> generaciones= new ArrayList<ListaSimple>();
    ArrayList<Integer> similitudes = new ArrayList<Integer>();
    ArrayList<Integer> numeros = new ArrayList<Integer>();
    int contador = 0;
    String nombre= "Gen";
    String ruta= "imagenes\\Gen";
    
    //Constructor de la clase
    public Genetico(){
   
    }
    
    //Función que llama al procedimiento que hace la poblacion inicial
    public void poblacionInicial() throws IOException{
        fun();
    }
    
    public void seleccion() throws FileNotFoundException{
        this.numeros.clear();
        this.similitudes.clear();
        
        ListaSimple tempb= this.generaciones.get(0);
        ListaSimple temp= this.generaciones.get(this.contador);
        Fitness fit = new Fitness(this.ruta+Integer.toString(contador)+"\\");
        
        Nodo actu;
        int sim=0;
        
        for (int i =0; i<10; i++){
            sim=fit.Algoritmo(Integer.toString(i)+".jpg", "imagen.jpg");
            actu= temp.BuscarPosicion(i);
            actu.sililitud=sim;
            this.similitudes.add(sim);
            
           
           //System.out.println(fit.Algoritmo(Integer.toString(i)+".jpg", "prueba2.jpg"));
        }
        
        for(int i =0; i<5;i++){
            this.numeros.add(obtenerMayor());
            //System.out.println(this.numeros.get(i));
        }

        //tempb.imprimir();
        System.out.println(tempb.categoria);
        System.out.println();
    }
    
    public void cruce() throws IOException{
        ListaSimple temp= this.generaciones.get(this.contador);
        this.contador = this.contador + 1;
        
        double []decDiametro = new double [2];
        //decDiametro[0] = 30;
        //decDiametro[1] = 65;
        
        double []decLongitud = new double [2];
        //decLongitud[0] = 25;
        //decLongitud[1] = 70;
        
        int [] ramas = new int[2];
        //ramas[0] = 2;
        //ramas[1] = 5;
        
        double [] angulo = new double [2];
        //angulo[0] = 33;
        //angulo[1] = 40;
        
        ListaSimple n= new ListaSimple(this.nombre+Integer.toString(this.contador));
        int prof = 0;
        double dia= 0;
        double lon= 0 ;
        int indice=0;
        
        
        for (int i=0; i< this.numeros.size();i++){
            Nodo nodo = temp.BuscarPosicion(this.numeros.get(i));
            double long1 = nodo.longitudInicial;
            double dia1 = nodo.diametroInicial;
            int prof1 = nodo.profundidad;
            

            double[] rdiametros1 =new double[2];
            System.arraycopy(nodo.decDiametro, 0, rdiametros1, 0, 2);

            double [] rlongitudes1 = new double[2];
            System.arraycopy(nodo.decLongitud, 0, rlongitudes1, 0, 2);

            double [] rangulos1 =  new double[2];
            System.arraycopy(nodo.angulo, 0, rangulos1, 0, 2);

            int [] rramas1 = new int[2];
            System.arraycopy( nodo.ramas, 0, rramas1, 0, 2);
            
            for (int j = i+1; j<this.numeros.size();j++){
                Nodo nodob = temp.BuscarPosicion(this.numeros.get(j));
                double long2 = nodob.longitudInicial;
                double dia2 = nodob.diametroInicial;
                int prof2 = nodob.profundidad;
                
                double[] rdiametros2 =new double[2];
                System.arraycopy(nodob.decDiametro, 0, rdiametros2, 0, 2);
                
                double [] rlongitudes2 = new double[2];
                System.arraycopy(nodob.decLongitud, 0, rlongitudes2, 0, 2);
                
                double [] rangulos2 =  new double[2];
                System.arraycopy(nodob.angulo, 0, rangulos2, 0, 2);
                
                int [] rramas2 = new int[2];
                System.arraycopy( nodob.ramas, 0, rramas2, 0, 2);
                
                lon = obtenerrandomPadresD(long1,long2);
                dia= obtenerrandomPadresD(dia1,dia2);
                prof = obtenerrandomPadresI(prof1,prof2);
                
                decLongitud = obtenerrandomPadresRangosD(rlongitudes1,rlongitudes2);
                decDiametro = obtenerrandomPadresRangosD(rdiametros1,rdiametros2);
                angulo = obtenerrandomPadresRangosD(rangulos1,rangulos2);
                ramas = obtenerrandomPadresRangosI(rramas1,rramas2);
                
                //Variable para indicar si se muta o no
                boolean mutado = false;
            
                //Muta segun un 3 de probabilidad
                if (RandomInt(100, 1) < 4){
                    double values[] = new double [3];
                    mutacion(prof, dia, lon, decLongitud, decDiametro, ramas, angulo, values);
                    prof = (int)values[0];
                    dia = values[1];
                    lon = values[2];
                    mutado = true;
                }
                
                //Crea un frame de fractales
                JFrame f = new Fractales(prof, dia, lon, decLongitud, decDiametro, ramas, angulo);

                //crea el nodo y guarda el frame en la lista
                Nodo tmp = new Nodo(prof, dia, lon, decLongitud, decDiametro, ramas, angulo, f, indice, mutado);
                tmp.padrea=nodo;
                tmp.padreb=nodob;
                n.insertar(tmp);

                //Guarda la imagen creada a partir del frame de fractales
                GuardarImagen imagen = new GuardarImagen();
                imagen.saveImagen(ruta+Integer.toString(contador)+"\\",Integer.toString(indice),f);
                indice +=1;
            }        
        }
        
        //Añade la generacion a la lista de generaciones
        this.generaciones.add(n);
    }
    
    
    //Funcion encarga da mutar un individuo
    public void mutacion(int prof, double diaI, double longI, double [] decL, double[] decD, int [] ram, double [] ang, double[] values){
        
        //Setea el arreglo, para estos valores
        values[0] = prof;
        values[1] = diaI;
        values[2] = longI;
        
        //Obtiene random entre 1 y 4, para determinar la cantidad de cromosomas a mutar
        int cromosomas = RandomInt(4, 1);
          
        //Muta los cromosomas, segun su cantidad
        for(int i=0; i<cromosomas; i++){
            
            int tipo = RandomInt(7, 1);
            
            /*Saca un random y muta el tipo de cromosoma
            1: Profundidad, 2: diametro inicial, 3: longitud inicial, 4: decremento de long, 5: decremento de diametro, 6: ramas, 7: angulo de ramas*/
            switch (tipo) {
                case 1:
                    values[0] = Mutar(prof);
                    break;
                case 2:
                    values[1] = Mutar(diaI);
                    break;
                case 3:
                    values[2] = Mutar(longI);
                    break;
                case 4:
                    MutacionRangosD(decL);
                    break;
                case 5:
                    MutacionRangosD(decD);
                case 6:
                    MutacionRangosInt(ram);
                case 7:
                    MutacionRangosD(ang);
                default:
                    break;
            }
            
        }
        
    }
    
    
    //Función que convierte los valores a binario y los muta
    public static double Mutar(double numero){

        //captura los decimales para después
        double decimales = numero - (int) numero;
        
        //Convierte el numero a un binario
        String binario = Integer.toBinaryString((int)numero);

        //Captura la longitud del string binario
        int cantidad = binario.length();
        
        //Captura la cantidad de digitos a cambiar (la subsecuencia)
        int subsecuencia = RandomInt(cantidad/2, 1);
        
        //Contadores para la mutacion
        int posisicion = RandomInt(cantidad-1,0);
        int i=0;

        
        //Ciclo que va modificando el binario
        while (i<subsecuencia && posisicion < cantidad){
            
            //Si el char donde esta la posicion es un uno, tons lo cambia a cero
            if(binario.charAt(posisicion)=='1'){
                binario = binario.substring(0, posisicion) + '0'+ binario.substring(posisicion + 1);
            }else{ 
                binario = binario.substring(0, posisicion) + '1'+ binario.substring(posisicion + 1);
            }
            //Aumenta los contadores
            i++;
            posisicion++;
        }
        
        //Retorna el resultado de la mutacion
        return ((double)Integer.parseInt(binario, 2)+decimales);
    }
    
    
    //Realiza de mutacion de rangos int
    public void MutacionRangosInt(int[] cromosoma){
        
        //Dos temporales con las mutaciones
        int tmp = (int)Mutar(cromosoma[0]);
        int tmp2 = (int) Mutar(cromosoma[1]);
        
        //Las acomoda correctamente
        if(tmp < tmp2){
            cromosoma[0] = tmp;
            cromosoma[1] = tmp2;
        }
        else{
            cromosoma[0] = tmp2;
            cromosoma[1] = tmp;
        }    
    }
    
    
    //Realiza de mutacion de rangos double
    public void MutacionRangosD(double[] cromosoma){
        
        //Dos temporales con las mutaciones
        double tmp = Mutar(cromosoma[0]);
        double tmp2 = Mutar(cromosoma[1]);
        
        //Las acomoda correctamente
        if(tmp < tmp2){
            cromosoma[0] = tmp;
            cromosoma[1] = tmp2;
        }
        else{
            cromosoma[0] = tmp2;
            cromosoma[1] = tmp;
        }
    }
    
    public void terminacion(){
    
    
    }
    
    //Funcion para la poblacion inicial
    public void fun() throws IOException{
        
        //Nueva lista simple para guardar la generacion
        ListaSimple n= new ListaSimple(this.nombre+Integer.toString(this.contador));
        
        //Inicializa los valores para cada parametro, de los 7 posibles
        double [] longitud = new double [2];
        longitud [0] = 13;
        longitud [1] = 25;
        
        double [] diametro = new double [2];
        diametro [0] = 12;
        diametro [1] = 23;
        
        int [] profundidad = new int [2];
        profundidad [0] = 5;
        profundidad [1] = 10;
        
        double []decDiametro = new double [2];
        decDiametro[0] = 30;
        decDiametro[1] = 65;
        
        double []decLongitud = new double [2];
        decLongitud[0] = 25;
        decLongitud[1] = 70;
        
        int [] ramas = new int[2];
        ramas[0] = 2;
        ramas[1] = 5;
        
        double [] angulo = new double [2];
        angulo[0] = 33;
        angulo[1] = 40;
                
        //Crea una generación de 10 individuos
        for (int i=0; i<10; i++){
           
            //Genera random de long, diametro y profundidad
            double lon = RandomDouble(longitud[1], longitud[0]);       
            double dia = RandomDouble(diametro[1], diametro[0]);
            int prof = RandomInt(profundidad[1], profundidad[0]);
            
            //Crea un frame de fractales
            JFrame f = new Fractales(prof, dia, lon, decLongitud, decDiametro, ramas, angulo);
            
            //crea el nodo y guarda el frame en la lista
            Nodo tmp = new Nodo(prof, dia, lon, decLongitud, decDiametro, ramas, angulo, f, i, false);
            n.insertar(tmp);

            //Salva las imagenes
            GuardarImagen imagen = new GuardarImagen();
            imagen.saveImagen(ruta+Integer.toString(contador)+"\\",Integer.toString(i),f);
        }
        
        //Añade la primera generacion a la lista de generaciones
        this.generaciones.add(n);
    }
    
    
    
    public double obtenerrandomPadresD(double pa, double pb){
        double lon= 0.0;
        if(pa>pb){
           //Genera random de long
           lon = RandomDouble(pa, pb);
           return lon;
        }
        
        else if (pa==pb){
            return pa;
        }
        
        else{
            lon = RandomDouble(pb, pa);
            return lon;
        }
        
        
    }
    
    
    public int obtenerrandomPadresI(int pa, int pb){
        int lon=0;
        if(pa>pb){
           //Genera random de long
           lon = RandomInt(pa, pb);
           return lon;
        }
        
        else if (pa==pb){
            return pa;
        }
        
        else{
            lon = RandomInt(pb, pa);
            return lon;
        }
    }
    
    
    public double[] obtenerrandomPadresRangosD(double[] pa, double[] pb){
        double[] sale = new double[2];
        
        sale[0]= obtenerrandomPadresD(pa[0],pb[0]);
        sale[1]= obtenerrandomPadresD(pa[1],pb[1]);
        
        if(sale[0]<sale[1]){
            //return sale;
        }
        
        else{
            double tmp = sale[0];
            sale[0]=sale[1];
            sale[1]=tmp;
            //pa[0]=sale[1];
            //pa[1]=sale[0];
            //return pa;
        }
        return sale;
    }
    
    
    public int[] obtenerrandomPadresRangosI(int[] pa, int[] pb){
        int[] sale = new int[2];
        
        sale[0]= obtenerrandomPadresI(pa[0],pb[0]);
        sale[1]= obtenerrandomPadresI(pa[1],pb[1]);
        
        if(sale[0]<sale[1]){
            //return sale;
        }
        
        else{
            int tmp = sale[0];
            sale[1]=sale[0];
            sale[0]= tmp;
            //pa[0]=sale[1];
            //pa[1]=sale[0];
            //return pa;
        }
        return sale;
    }
    

    public int obtenerMayor(){
        int mayor=0;
        int indice=0;
        for(int i=0; i<this.similitudes.size();i++){
            if(mayor<this.similitudes.get(i) && !buscaindice(i)){
                mayor= this.similitudes.get(i);
                indice = i;
            }
        }
        return indice;
    }
    
    
    public boolean buscaindice(int n){
        for (int i=0;i <this.numeros.size();i++){
            if (n==this.numeros.get(i)){
                return true;
            }
        }
        return false;
    }
    
}
