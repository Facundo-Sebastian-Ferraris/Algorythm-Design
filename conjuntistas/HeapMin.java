/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas;


/**
 *
 * @author San Sebastian
 */
public class HeapMin {

    private final int TAMANIO = 11;
    private Comparable[] heap;
    private int ultimo;

    public HeapMin() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 1; //debe empezar en 1 para el agregado de elementos
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = this.ultimo < TAMANIO;
        if (exito) {

            this.heap[this.ultimo] = elemento;
            hacerSubir(this.ultimo);
            this.ultimo++;

        }
        return exito;
    }

    private void hacerSubir(int cursor) {
        int cursorComparador = cursor / 2;
        boolean fijado = false;
        while (!fijado && cursorComparador > 0) {
            fijado = true;
            if (this.heap[cursor].compareTo(this.heap[cursorComparador]) < 0) {
                Comparable temp = this.heap[cursorComparador];
                this.heap[cursorComparador] = this.heap[cursor];
                this.heap[cursor] = temp;
                fijado = false;
            }
            cursor = cursorComparador;
            cursorComparador /= 2;
        }
    }

    public boolean eliminarCima() {
        boolean exito = this.ultimo > 1;
        if (exito) {
            this.heap[1] = this.heap[this.ultimo-1];
            this.ultimo--;
            hacerBajar(1);
        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;
        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                if (posH < this.ultimo) {
                    if (this.heap[posH + 1].compareTo(this.heap[posH]) < 0) {
                        posH++;
                    }
                }
                if (this.heap[posH].compareTo(temp) < 0) {
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }

        }
    }

    public Comparable recuperarCima(){
    return this.heap[1];
    }
    
    @Override
    public String toString() {
        String mensaje = "[";
        for (int direccion = 1; direccion < this.ultimo; direccion++) {
            mensaje += this.heap[direccion].toString();
            if (direccion < this.ultimo - 1) {
                mensaje += " , ";
            }
        }
        mensaje += "]";
        return mensaje;
    }
}
