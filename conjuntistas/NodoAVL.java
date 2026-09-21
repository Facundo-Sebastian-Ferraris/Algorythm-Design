/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;

/**
 *
 * @author San Sebastian
 */
public class NodoAVL {

    private Comparable elem;
    private NodoAVL hijoIzquierdo;
    private NodoAVL hijoDerecho;
    private int altura;

    public NodoAVL(Comparable elem, NodoAVL hijoIzquierdo, NodoAVL hijoDerecho) {
        this.elem = elem;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.altura = 0;
    }

    public Comparable getElem() {
        return elem;
    }

    public NodoAVL getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoAVL getHijoDerecho() {
        return hijoDerecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public void setHijoIzquierdo(NodoAVL hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
        this.altura = recalcularAltura(this);
    }

    public void setHijoDerecho(NodoAVL hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
        this.altura = recalcularAltura(this);
    }
  

    private int recalcularAltura(NodoAVL n) {
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
