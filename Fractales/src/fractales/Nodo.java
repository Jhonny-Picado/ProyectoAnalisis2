/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractales;

import javax.swing.JFrame;

/**
 *
 * @author jocxa
 */
public class Nodo {
    public double [] angulo = new double [2];
    public int profundidad; 
    public int [] ramas = new int [2]; 
    public double longitudInicial; 
    public double [] decLongitud = new double[2]; 
    public double diametroInicial; 
    public double [] decDiametro = new double [2];
    public Nodo siguiente;
    public Nodo padrea;
    public Nodo padreb;
    public JFrame imagen;
    public int sililitud;
    public int contador;
    public boolean mutado;

    //Constructor del nodo
    public Nodo(int prof, double diaI, double longI, double [] decL, double[] decD, int [] ram, double [] ang, JFrame f, int con, boolean Mutado) {
        this.profundidad = prof;
        this.diametroInicial = diaI;
        this.longitudInicial = longI;
        this.decLongitud = decL;
        this.decDiametro = decD;
        this.ramas = ram;
        this.angulo = ang;
        this.siguiente = null;
        this.imagen=f;
        this.contador=con;
        this.mutado = Mutado;
    }
}
