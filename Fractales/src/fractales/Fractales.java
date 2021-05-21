/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

/**
 *
 * @author Jhonny Picado
 */
public class Fractales extends JFrame {
 
    public Fractales() {
        super("Fractal Tree");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
 
    private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
        if (depth == 0) return;
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 10.0);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 10.0);
        g.drawLine(x1, y1, x2, y2);
        drawTree(g, x2, y2, angle - 20, depth - 1);
        drawTree(g, x2, y2, angle + 20, depth - 1);
    }
 
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        drawTree(g, 400, 500, -90, 9);
    }
 
    public static void main(String[] args) throws FileNotFoundException {
        new Fractales().setVisible(true);
        CargarImagen c = new CargarImagen();
        int [][] m = c.GenerarMatriz("arbol1.png");
        
        for (int i=0; i<m.length; i++){
            
            for (int j=0; j<m[0].length; j++){
                System.out.print(m[i][j]);
            }
            System.out.println();
        }
    }
}