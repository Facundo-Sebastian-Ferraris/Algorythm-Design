/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package propositoEspecifico;

/**
 *
 * @author Lord Efarrsir
 */
public class NodoAVLDicc {

    private Comparable clave;
    private Object elem;
    private NodoAVLDicc hijoIzquierdo;
    private NodoAVLDicc hijoDerecho;
    private int altura;

    public NodoAVLDicc(Comparable clave, Object elem, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho) {
        this.clave = clave;
        this.elem = elem;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.altura = 0;
    }
    
    public Comparable getClave(){
        return this.clave;
    }

    public Object getElemento() {
        return this.elem;
    }

    public NodoAVLDicc getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public NodoAVLDicc getHijoDerecho() {
        return this.hijoDerecho;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setHijoIzquierdo(NodoAVLDicc hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
        this.altura = recalcularAlturaAux(this);
    }

    public void setHijoDerecho(NodoAVLDicc hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
        this.altura = recalcularAlturaAux(this);
    }

    public void recalcularAltura() {
        this.altura = recalcularAlturaAux(this);
    }

    private int recalcularAlturaAux(NodoAVLDicc n) {
        int alt = -1;
        if (n != null) {
            if (n.getHijoIzquierdo() != null) {
                alt = n.getHijoIzquierdo().altura;
            }
            int alturaDerecha = -1;
            if (n.getHijoDerecho() != null) {
                alturaDerecha = n.getHijoDerecho().altura;
            }
            if (alt < alturaDerecha) {
                alt = alturaDerecha;
            }
            alt++;
        }
        return alt;
    }

}
