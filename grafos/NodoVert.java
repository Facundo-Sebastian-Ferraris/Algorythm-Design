/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

/**
 *
 * @author San Sebastian
 */
public class NodoVert {

    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object elemen, NodoVert sigVertice, NodoAdy primerAdy) {
        this.elem = elemen;
        this.sigVertice = sigVertice;
        this.primerAdy = primerAdy;
    }

    public Object getElem() {
        return elem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setElem(Object elemen) {
        this.elem = elemen;
    }

    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

}
