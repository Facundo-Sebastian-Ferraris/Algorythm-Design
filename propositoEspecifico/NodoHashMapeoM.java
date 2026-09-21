package propositoEspecifico;

import java.util.HashMap;

/**
 *
 * @author Ferraris Facundo
 */
class NodoHashMapeoM {

    private Object dominio;
    private HashMap rango;
    private NodoHashMapeoM enlace;

    public NodoHashMapeoM(Object dom, HashMap ran, NodoHashMapeoM enlace) {
        this.dominio = dom;
        this.rango = ran;
        this.enlace = enlace;
    }

    public Object getDominio() {
        return this.dominio;
    }

    public HashMap getRango() {
        return this.rango;
    }

    public NodoHashMapeoM getEnlace() {
        return this.enlace;
    }

    public void setRango(HashMap nueva) {
        this.rango = nueva;
    }

    public void setEnlace(NodoHashMapeoM nuevo) {
        this.enlace = nuevo;
    }
}
