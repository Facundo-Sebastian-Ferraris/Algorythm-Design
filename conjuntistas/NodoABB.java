/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;

/**
 *
 * @author San Sebastian
 */
public class NodoABB {

    Comparable elemento;
    NodoABB izquierdo;
    NodoABB derecho;

    //constructor
    public NodoABB(Comparable elemento, NodoABB izquierdo, NodoABB derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    //seters
    public void setElemento(Comparable elemento) {
        this.elemento = elemento;
    }

    public void setIzquierdo(NodoABB izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoABB derecho) {
        this.derecho = derecho;
    }

    //geters
    public Comparable getElemento() {
        return elemento;
    }

    public NodoABB getHijoIzquierdo() {
        return izquierdo;
    }

    public NodoABB getDerecho() {
        return derecho;
    }
    
    

}
