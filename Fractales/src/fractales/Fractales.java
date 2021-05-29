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
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jhonny Picado
 */
public class Fractales extends JFrame {
 
    public Fractales() {
        super("Fractal Tree");
        setBounds(100, 100, 1366, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    
    //Función que dibuja el árbol
    private void drawTree(Graphics2D g, int x1, int y1, double angulo, double anguloRam, int profundidad, int ramas, double longitud, 
                          double decLong, double diametro, double decDiam) {
        
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
        
        //Configura la longitud y el diametro para la siguiente llamada recursiva
        longitud -= longitud * decLong;
        diametro-= diametro * decDiam;
        
        //Obtiene la mitad de las ramas y lo multiplica por -1, para obtener el modificador del angulo, al poner las ramas
        int mod = (ramas/2)*-1;
            
        
        //Itera sobre la cantidad de ramas dadas, para formar las mismas
        for(int i=1; i < ramas+1; i++){
            
            //Llamado recursivo
            drawTree(g, x2, y2, (angulo + anguloRam * mod), anguloRam, profundidad - 1, ramas, longitud, decLong, diametro, decDiam);
            
            //Aumenta el modificador
            mod++;
            
            //Si las ramas son pares y se encuentra en la rama media, entonces aumenta el modificador para acomodar las ramas bien
            if(ramas%2==0 && i == ramas/2)
                mod++;
            
        }
    }
 
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        
        //Hay que convertir el g a un grafico 2D, para aplicar el diametro
        Graphics2D g2D = (Graphics2D) g;
        
        
        //Funcion(g2D, x1, x2, anguloInicial, anguloRamas, profundidad, ramas, longitud, modLongitud, diametro, modDiametro)
        drawTree(g2D, 683, 715, -90, 40, 7, 3, 18, 0.3, 15, 0.6);
    }
    
    
 
    public static void main(String[] args) throws FileNotFoundException {
        
        JFrame f = new Fractales();
        f.setVisible(true);


        /*
        CargarImagen c = new CargarImagen();
        int [][] m = c.GenerarMatriz("arbol2.png");
        
        for (int i=0; i<m.length; i++){
            
            for (int j=0; j<m[0].length; j++){
                System.out.print(m[i][j]);
            }
            System.out.println();
        }*/
    }
    
    
    //codigo para hacer una pausa (Después se borra)
    public static void pausa(JFrame f){
        
        
        String name="1";
        
        while(!"".equals(name)){
            name = JOptionPane.showInputDialog("pausa");
           
        }
        f.setVisible(false);

    }
}