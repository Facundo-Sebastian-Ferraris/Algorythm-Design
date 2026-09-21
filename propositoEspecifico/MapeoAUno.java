/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package propositoEspecifico;

import lineales.dinamicas.Lista;

/**
 *
 * @author Lord Efarrsir
 */
public class MapeoAUno {

    private static final int TAMANIO = 10;
    private NodoHashMapeo[] tabla;
    private int cant;

    public MapeoAUno() {
        this.tabla = new NodoHashMapeo[TAMANIO];
        this.cant = 0;
    }

    public boolean asociar(Object dominio, Object rango) {
        boolean exito = true;
        int direccion = funcionHash(dominio) % TAMANIO;
        NodoHashMapeo aux = this.tabla[direccion];
        while (aux != null && exito) {
            exito = !aux.getDominio().equals(dominio);
            aux = aux.getEnlace();
        }
        if (exito) {
            this.tabla[direccion] = new NodoHashMapeo(dominio, rango, this.tabla[direccion]);
            this.cant++;
        }
        return exito;
    }

    public boolean desasociar(Object dominio) {
        boolean exito = false;
        if (this.cant != 0) {
            int direccion = funcionHash(dominio) % TAMANIO;
            NodoHashMapeo aux = this.tabla[direccion];
            if (aux.getDominio().equals(dominio)) {
                this.tabla[direccion] = aux.getEnlace();
            } else {
                while (aux.getEnlace() != null && !exito) {
                    exito = aux.getEnlace().getDominio().equals(dominio);
                    aux = aux.getEnlace();
                }
                if (exito) {
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    this.cant--;
                }
            }
        }
        return exito;
    }

    public Object obtenerValor(Object dominio) {
        Object r = null;
        if (this.cant != 0) {
            NodoHashMapeo aux = this.tabla[funcionHash(dominio) % TAMANIO];
            boolean encontrado = aux.getDominio().equals(dominio);
            while (aux != null && !encontrado) {
                aux = aux.getEnlace();
                encontrado = aux != null && aux.getDominio().equals(dominio);
            }
            if (encontrado) {
                r = aux.getRango();
            }
        }
        return r;
    }

    public Lista obtenerConjuntoDominio() {
        Lista r = new Lista();
        int direccion = TAMANIO - 1;
        int referencia = this.cant;
        while (referencia > 0) {
            NodoHashMapeo aux = this.tabla[direccion];
            while (aux != null) {
                r.insertar(aux.getDominio(), 1);
                referencia--;
                aux = aux.getEnlace();
            }
            direccion--;
        }
        return r;
    }

    public Lista obtenerConjuntoRango() {
        Lista r = new Lista();
        int direccion = TAMANIO - 1;
        int referencia = this.cant;
        while (referencia > 0) {
            NodoHashMapeo aux = this.tabla[direccion];
            while (aux != null) {
                r.insertar(aux.getRango(), 1);
                referencia--;
                aux = aux.getEnlace();
            }
            direccion--;
        }
        return r;
    }

    @Override
    public String toString() {
        String r = "Mapa Vacio";
        if (this.cant != 0) {
            r = "";
            int referencia = cant;
            int direccion = TAMANIO - 1;
            while (referencia > 0) {
                NodoHashMapeo aux = this.tabla[direccion];
                while (aux != null) {
                    r += "\nDominio: " + aux.getDominio().toString() + "\nRango: " + aux.getRango().toString() + "\n------";
                    referencia--;
                    aux = aux.getEnlace();
                }
                direccion--;
            }

        }
        return r;
    }

    private int funcionHash(Object dominio) {
        return Math.abs(dominio.hashCode());
    }

}
