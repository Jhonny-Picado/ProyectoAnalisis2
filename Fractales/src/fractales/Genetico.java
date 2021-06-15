/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import static fractales.Fractales.RandomDouble;
import static fractales.Fractales.RandomInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    String iruta;
    //Constructor de la clase
    public Genetico(){
   
    }
    
    
    /* --------------------------------------- Flujo del Programa -------------------------------------  */
    
    //Flujo del algoritmo genético
    public void Algoritmo() throws IOException{
        //Crea la primer carpeta y hace la población inicial
        CrearCarpeta(0);
        poblacionInicial();
        
        System.out.println("MAX Gen0: "+ Collections.max(similitudes));
        
        //Inicia un contador en 1, para el nombre de cada generación
        int i=1;

        //Itera mientras no se cumpla la condicion de parada
        while(Collections.max(similitudes)<85){
            
            //Primero crea la carpeta donde guardar las imagenes, realiza el cruce y hace la selección
            CrearCarpeta(i);
            cruce();
            seleccion();
            
            System.out.println("MAX Gen"+Integer.toString(i)+": "+ Collections.max(similitudes));
            
            i++;
        }
        
        
    }
    
    
    //Método que crea una carpeta por cada generación
    public void CrearCarpeta(int i) {
        
        //Crea un nuevo directorio
        File directorio = new File(ruta + Integer.toString(i));
        
        //Valida si existe, sino, lo crea
        if (!directorio.exists()) {
            directorio.mkdir();
        } else {
            
            //Captura las cosas de la carpeta y borra todo
            File[] ficheros = directorio.listFiles();

            for (File fichero : ficheros) {
                fichero.delete();
            }
            directorio.delete();
            directorio.mkdir(); //La vuelve a hacer
        }
    }
    
    
    /* --------------------------------------- Población Inicial -------------------------------------  */
    
    //Función que llama al procedimiento que hace la poblacion inicial
    public void poblacionInicial() throws IOException{
        fun();
        seleccion();
    }
    
    
    //Funcion para la poblacion inicial
    public void fun() throws IOException{
        
        //Nueva lista simple para guardar la generacion
        ListaSimple n= new ListaSimple(this.nombre+Integer.toString(this.contador));
        
        //Inicializa los valores para cada parametro, de los 7 posibles
        double [] longitud = new double [2];
        longitud [0] = 16;
        longitud [1] = 24;
        
        double [] diametro = new double [2];
        diametro [0] = 13;
        diametro [1] = 24;
        
        int [] profundidad = new int [2];
        profundidad [0] = 5;
        profundidad [1] = 9;
        
        double []decDiametro = new double [2];
        decDiametro[0] = 24;
        decDiametro[1] = 56;
        
        double []decLongitud = new double [2];
        decLongitud[0] = 23;
        decLongitud[1] = 63;
        
        int [] ramas = new int[2];
        ramas[0] = 2;
        ramas[1] = 5;
        
        double [] angulo = new double [2];
        angulo[0] = 30;
        angulo[1] = 43;
                
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
    
    
    /* --------------------------------------- Cruce -------------------------------------  */
    
    //Función que realiza el cruce de cada generación
    public void cruce() throws IOException{
        
        //Instancia una lista temporal para almacenar la ultima generación y aumenta el contador de las generaciones
        ListaSimple temp= this.generaciones.get(this.contador);
        this.contador = this.contador + 1;
        
        //Variables donde se almacenan los parametros de cada individuo
        double []decDiametro = new double [2];
        double []decLongitud = new double [2];
        int [] ramas = new int[2];
        double [] angulo = new double [2];
        int prof = 0;
        double dia= 0;
        double lon= 0 ;
        int indice=0;
        
        //Instancia una nueva lista para la nueva generación
        ListaSimple n= new ListaSimple(this.nombre+Integer.toString(this.contador));
        
        
        //Itera sobre los cinco mejores individuos de la ultima generación
        for (int i=0; i< this.numeros.size();i++){
            
            //Captura el individuo mejor segun el arreglo global numeros
            Nodo nodo = temp.BuscarPosicion(this.numeros.get(i));
            
            //Almacena temporalmente los valores de este individuo
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
            
            
            //Itera sobre los otrso mejores individuos (los otros mejores 4)
            for (int j = i+1; j<this.numeros.size();j++){
                
                //Guarda el individuo según el j
                Nodo nodob = temp.BuscarPosicion(this.numeros.get(j));
                
                //También almacena temporalmente estos valores
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
                prof = obtenerrandomPadresI(8,5);
                
                decLongitud = obtenerrandomPadresRangosD(rlongitudes1,rlongitudes2);
                decDiametro = obtenerrandomPadresRangosD(rdiametros1,rdiametros2);
                angulo = obtenerrandomPadresRangosD(rangulos1,rangulos2);
                ramas = obtenerrandomPadresRangosI(rramas1,rramas2);
                
                /*
                //Primero cruzar valores no rangos
                lon = Cruzar(long1, long2);
                dia = Cruzar(dia1, dia2);
                prof = (int) Cruzar(prof1, prof2);
                
                //Después se cruzan los rangos
                decLongitud = CruceRangosD(rlongitudes1, rlongitudes2);
                decDiametro = CruceRangosD(rdiametros1, rdiametros2);
                angulo = CruceRangosD(rangulos1, rangulos2);
                ramas = CruceRangosInt(rramas1, rramas2);
                */
                
                //Variable para indicar si se muta o no
                boolean mutado = false;
            
                //Muta segun un 3 de probabilidad
                if (RandomInt(100, 1) < 4){
                    
                    //Llama a la funcion que muta y guarda algunos valores necesarios
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
    
    
    //Función que realiza propiamente el cruce, recibe los padres y los cruza
    public double Cruzar(double padre, double madre){
        
        //Primero almacena los decimales        
        double decimales = obtenerrandomPadresD((padre - (int)(padre)),(madre - (int)(madre))); 
        
        //Los convierte a binario
        String binarioPadre = Integer.toBinaryString((int)padre);
        String binarioMadre = Integer.toBinaryString((int)madre);

        //Les ajusta el mismo tamaño, para que no haya error a la hora de cruzar
        if(binarioPadre.length() < binarioMadre.length()){
            int bits = binarioMadre.length()-binarioPadre.length();
            binarioPadre = AcomodarBinario(binarioPadre, bits);
        } 
        else if(binarioPadre.length() > binarioMadre.length()){
            int bits = binarioPadre.length()-binarioMadre.length();
            binarioMadre = AcomodarBinario(binarioMadre, bits);
        } 
        
        //Saca un random para el punto de cruce, donde debe empezar
        int puntoCruce = RandomInt(binarioPadre.length()-1, 0);
        String cruce;
        
        //Saca otro random, si es menor a 6, entonces cruza de una forma
        if(RandomInt(10, 1) < 6){
            cruce = binarioPadre.substring(0, puntoCruce) + binarioMadre.substring(puntoCruce);
        }else{
            cruce = binarioMadre.substring(0, puntoCruce) + binarioPadre.substring(puntoCruce);
        }
        
        //Retorna el valor convertirdo a double
        return ((double)Integer.parseInt(cruce, 2)+decimales);
    }
    
    
    //Funcion que acomoda un binario, para que tenga el mismo tamaño que el otro
    public String AcomodarBinario(String binario, int bits){
        
        //Recibe el numero binario
        String acomodo = binario;
        
        //Le va agregando ceros a la izquierda
        for(int i=0; i<bits; i++){
            acomodo = '0'+acomodo;
        }
        return acomodo;
    }
    
    
    //Función que cruza los rangos tipo double
    public double[] CruceRangosD(double [] padre, double [] madre){
        
        //Realiza el cruce
        double gen1 = Cruzar(padre[0], madre[0]);
        double gen2 = Cruzar(padre[1], madre[1]);
        double[] genes = new double[2];
        
        //Los acomoda y después los retorna
        if(gen1<gen2){
            genes[0]=gen1;
            genes[1]=gen2;
        } else{
            genes[0]=gen2;
            genes[1]=gen1;
        }
         
        return genes;
    }
    
    //Función que cruza los rangos tipo int
    public int[] CruceRangosInt(int [] padre, int[] madre){
        
        //Realiza los cruces
        int gen1 = (int)Cruzar(padre[0], madre[0]);
        int gen2 = (int) Cruzar(padre[1], madre[1]);
        int[] genes = new int[2];
        
        //Los acomoda y regresa
        if(gen1<gen2){
            genes[0]=gen1;
            genes[1]=gen2;
        } else{
            genes[0]=gen2;
            genes[1]=gen1;
        }
        return genes;
    }
    
    
    /* --------------------------------------- Mutación -------------------------------------  */
    
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

    
    /* --------------------------------------- Selección -------------------------------------  */
    
    //Funcion de selección
    public void seleccion() throws FileNotFoundException{
        
        //Primero limpia los arreglos a utilizar
        this.numeros.clear();
        this.similitudes.clear();
        
        //Captura la ultima generación creada
        ListaSimple temp= this.generaciones.get(this.contador);
        
        //Instancia un fitness
        Fitness fit = new Fitness(this.ruta+Integer.toString(contador)+"\\");
        
        //Variables temporales
        Nodo actu;
        
        /*
        for (int i =0; i<10; i++){
            sim=fit.Algoritmo(Integer.toString(i)+".jpg", "imagen.jpg");
            actu= temp.BuscarPosicion(i);
            actu.sililitud=sim;
            this.similitudes.add(sim);           
           //System.out.println(fit.Algoritmo(Integer.toString(i)+".jpg", "prueba2.jpg"));
        }*/
        

        //almacena la cantidad de pixeles que tiene el arbol
        float [] pixelesArbol = fit.PixelesArbol(this.iruta);

        //itera sobre todos los individuos de la generación
        for(int i=0; i<10; i++){
            
            //Llama al fitness con el individuo y la imagen en uso
            float [] similitud=fit.Algoritmo(Integer.toString(i)+".jpg", this.iruta);
            
            //Realiza la conversion del porcentaje
            float porcentaje = ((similitud[0]/pixelesArbol[0])*100.0f)*0.2f + ((similitud[1]/pixelesArbol[1])*100f)*0.8f;
            
            //Guarda la similitud en el individuo y en el arreglo global
            actu = temp.BuscarPosicion(i);
            actu.sililitud=(int)porcentaje;
            this.similitudes.add((int)porcentaje);
            System.out.println(porcentaje);
        }
        
        //Aca se almacenan los indices de los mejores individuos 
        for(int i =0; i<5;i++){
            this.numeros.add(obtenerMayor());
        }

        System.out.println("Selección lista");
        System.out.println();
    }

    
    /* --------------------------------------- Métodos Extras -------------------------------------  */

    public double obtenerrandomPadresD(double pa, double pb){
        
        double lon= 0.0;
        
        if(pa>pb){
           //Genera random de long
           lon = RandomDouble(pa, pb);
           return lon;
        } else if (pa==pb){
            return pa;
        } else{
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
        } else if (pa==pb){
            return pa;
        } else{
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
        } else{
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
        } else{
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
