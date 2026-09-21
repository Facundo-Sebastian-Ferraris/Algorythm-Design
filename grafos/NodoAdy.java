/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

/**
 *
 * @author Ferraris Facundo
 */
public class NodoAdy {

    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;

    public NodoAdy(Object etiqueta, NodoVert vertice, NodoAdy sigAdyacente) {
        this.vertice = vertice;
        this.sigAdyacente = sigAdyacente;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public Object getEtiqueta() {
        return etiqueta;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

    public void setEtiqueta(Object etiqueta) {
        this.etiqueta = etiqueta;
    }

}
