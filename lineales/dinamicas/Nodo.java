
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author Lord Efarrsir
 */
public class Nodo {

    private Object elemento;
    private Nodo enlace;

    //constructor
    public Nodo(Object elemento, Nodo enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    //getters
    public Object getElemento() {
        return elemento;
    }

    public Nodo getEnlace() {
        return this.enlace;
    }

    //setters
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

}