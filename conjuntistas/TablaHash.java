/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;

import lineales.dinamicas.Nodo;
import libreria.funciones;

/**
 *
 * @author San Sebastian
 */
public class TablaHash {

    private final int TAMANIO = 10;
    private Nodo[] tabla;
    private int cant;

    public TablaHash() {
        this.tabla = new Nodo[TAMANIO];
        this.cant = 0;
    }

    public boolean insertar(Object elem) {
        int posicion = hashCode(elem);
        Nodo aux = this.tabla[posicion];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getElemento().equals(elem);
            aux = aux.getEnlace();
        }
        if (!encontrado) {
            this.tabla[posicion] = new Nodo(elem, this.tabla[posicion]);
            this.cant++;
        }
        return !encontrado;
    }

    public boolean eliminar(Object elem) {
        Nodo aux = this.tabla[hashCode(elem)];
        boolean encontrado = aux.getElemento().equals(elem);
        if (encontrado) {
            aux.setEnlace(aux.getEnlace());
        } else {
            encontrado = aux.getEnlace().getElemento().equals(elem);
            while (!encontrado && aux.getEnlace() != null) {
                aux = aux.getEnlace();
                encontrado = aux.getEnlace().getElemento().equals(elem);
            }
            if (encontrado) {
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return encontrado;
    }

    public boolean pertenece(Object elem) {
        boolean encontrado = false;
        Nodo aux = this.tabla[hashCode(elem)];
        encontrado = aux != null && aux.getElemento().equals(elem);
        if (!encontrado) {
            encontrado = aux != null && aux.getEnlace().getElemento().equals(elem);
            while (!encontrado && aux.getEnlace() != null) {
                aux = aux.getEnlace();
                encontrado = aux.getEnlace().getElemento().equals(elem);
            }
        }
        return encontrado;
    }

    //HASHERS
    private int hashCode(Object elem) {
        return funciones.primo(elem.toString(), TAMANIO);
    }

    private int rehashingDoble(int clave, int intento, Object elem) {
        int hashAux = funciones.doblamiento(elem.toString(), TAMANIO);
        int rehash = (clave + (intento - 1) * hashAux) % TAMANIO;
        return rehash;
    }

    //OBSERVADORES
    public boolean esVacio() {
        boolean vacio = this.tabla[0] == null;
        if (vacio) {
            int direccion = 1;
            vacio = this.tabla[direccion] == null;
            while (vacio) {
                direccion++;
                vacio = this.tabla[direccion] == null;
            }
        }
        return vacio;
    }

    @Override
    public String toString() {
        String mensaje = "";
        int direccion = 0;
        Nodo aux = this.tabla[direccion];
        while (direccion < TAMANIO - 1) {
            mensaje += "[" + direccion + "]: ";
            while (aux != null) {
                mensaje += aux.getElemento().toString() + " , ";
                aux = aux.getEnlace();
            }
            mensaje += "null\n";
            direccion++;
            aux = this.tabla[direccion];
        }
        return mensaje;
    }

}
