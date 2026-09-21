/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas;

/**
 *
 * @author San Sebastian
 */
public class NodoArbol {

    private Object elem;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Object elem, NodoArbol izquierdo, NodoArbol derecho) {
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public Object getElem() {
        return elem;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
   
}
