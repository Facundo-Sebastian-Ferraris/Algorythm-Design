/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas;

/**
 *
 * @author San Sebastian
 */
public class NodoGen {

    private Object elem;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;

    public NodoGen(Object elemento, NodoGen HI, NodoGen HerD) {
        this.elem = elemento;
        this.hijoIzquierdo = HI;
        this.hermanoDerecho = HerD;
    }

    public Object getElemento() {
        return this.elem;
    }

    public NodoGen getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public NodoGen getHermanoDerecho() {
        return this.hermanoDerecho;
    }

    public void setElemento(Object elem) {
        this.elem = elem;
    }

    public void setHijoIzquierdo(NodoGen HI) {
        this.hijoIzquierdo = HI;
    }

    public void setHermanoDerecho(NodoGen HerD) {
        this.hermanoDerecho = HerD;
    }

}
