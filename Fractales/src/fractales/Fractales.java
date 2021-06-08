/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Jhonny Picado
 */
public class Fractales extends JFrame {
 
    //Funcion(g2D, x1, x2, anguloInicial, anguloRamas, profundidad, ramas, longitud, modLongitud, diametro, modDiametro)
    private double [] angulo = new double [2];
    private int profundidad; 
    private int [] ramas = new int [2]; 
    private double longitudInicial; 
    private double [] decLongitud = new double[2]; 
    private double diametroInicial; 
    private double [] decDiametro = new double [2]; 
    
    
    //Constructor de la clase, recibe todos los parametros necesarios para crear el arbol
    public Fractales(int prof, double diaI, double longI, double [] decL, double[] decD, int [] ram, double [] ang) {
        
        super("Fractal Tree");
        setBounds(100, 100, 1366, 720);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.profundidad = prof;
        this.diametroInicial = diaI;
        this.longitudInicial = longI;
        this.decLongitud = decL;
        this.decDiametro = decD;
        this.ramas = ram;
        this.angulo = ang;
    }
    
      
    //Función que dibuja el árbol
    private void drawTree(Graphics2D g, int x1, int y1, double angulo, double [] anguloRam, int profundidad, int[] ramas, double longitud, 
                          double [] decLong, double diametro, double [] decDiam) {
        
        //Si se acaban los niveles del arbol, entonces se detiene
        if (profundidad == 0) 
            return;
        
        //Configura los puntos del plano cartesiano, conforme el valor por default de 10 multiplicado de cada rama, para darle su longitud
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angulo)) * 10.0 * longitud);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angulo)) * 10.0 * longitud);
        
        
        //Le configura el anchor de la linea, para simular el diametro de cada rama/tronco
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke((float) (diametro)));
        g.drawLine(x1, y1, x2, y2);
        
        
        //Saca los randoms de los modificadores de longitud y diametro
        double modLong = RandomDouble(decLong[1], decLong[0]) / 100;
        double modDiam = RandomDouble(decDiam[1], decDiam[0]) / 100;
        
        
        //Configura la longitud y el diametro para la siguiente llamada recursiva
        longitud -= longitud * modLong;
        diametro -= diametro * modDiam;
        
        
        //Saca el random para el angulo de las ramas
        double anguloTmp = RandomDouble(anguloRam[1], anguloRam[0]);
        
        //Saca el random de cuantas ramas tiene cada nivel
        int ramasTmp = RandomInt(ramas[1], ramas[0]);
        
        //Obtiene la mitad de las ramas y lo multiplica por -1, para obtener el modificador del angulo, al poner las ramas
        int mod = (ramasTmp/2)*-1;
            
        
        //Itera sobre la cantidad de ramas dadas, para formar las mismas
        for(int i=1; i < ramasTmp+1; i++){
            
            //Llamado recursivo
            drawTree(g, x2, y2, (angulo + anguloTmp * mod), anguloRam, profundidad - 1, ramas, longitud, decLong, diametro, decDiam);
            
            //Aumenta el modificador
            mod++;
            
            //Si las ramas son pares y se encuentra en la rama media, entonces aumenta el modificador para acomodar las ramas bien
            if(ramasTmp%2==0 && i == ramasTmp/2)
                mod++;
            
        }
    }
 
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        
        //Hay que convertir el g a un grafico 2D, para aplicar el diametro
        Graphics2D g2D = (Graphics2D) g;
        
        
        //Funcion(g2D, x1, x2, anguloInicial, anguloRamas, profundidad, ramas, longitud, modLongitud, diametro, modDiametro)
        drawTree(g2D, 683, 715, -90, this.angulo, this.profundidad, this.ramas, this.longitudInicial, this.decLongitud, this.diametroInicial, this.decDiametro);
        
    }
    
    
    //Función encargada de sacar randoms en tipo de datos double
    public static double RandomDouble(double mayor, double menor){
        
        return Math.random()*( mayor - menor) + menor;
        
    }
    
    
    //Función encargada de sacar randoms en tipo de datos int
    public static int RandomInt(int mayor, int menor){
        
        return (int) Math.floor(Math.random()*(mayor - menor +1) + menor);
        
    }
}