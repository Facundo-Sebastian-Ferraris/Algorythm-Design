/*
UML
<->raiz: NodoGen
------------------------------
<+>ArbolGen()
<+>insertar(Object, Object): boolean
<+>pertence(Object): boolean
<+>esVacio(): boolean
<+>padre(Object): TipoElemento
<+>altura
<+>nivel
<+>ancestros
<+>clone()
<+>clone()

 */
package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Nodo;

/**
 *
 * @author San Sebastian
 */
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object elem, Object padre) {
        NodoGen hijo = new NodoGen(elem, null, null);
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = hijo;
            exito = true;
        } else {
            NodoGen cursor = insertarAux(this.raiz, padre);
            if (cursor != null) {
                hijo.setHermanoDerecho(cursor.getHijoIzquierdo());
                cursor.setHijoIzquierdo(hijo);
                exito = true;
            }
        }
        return exito;
    }

    private NodoGen insertarAux(NodoGen nodo, Object elem) {
        NodoGen aux = null;
        if (nodo != null) {
            if (nodo.getElemento().equals(elem)) {
                aux = nodo;
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && aux == null) {
                    aux = insertarAux(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return aux;
    }

    public boolean pertenece(Object elem) {
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoGen nodo, Object elem) {
        boolean pertenece = false;
        if (nodo != null) {
            if (nodo.getElemento().equals(elem)) {
                // System.out.println(elem.toString() +" == "+ nodo.getElemento().toString()); //comentario de depuracion
                pertenece = true;
            } else {
                //System.out.println(elem.toString() +" != "+ nodo.getElemento().toString()); //comentario de depuracion
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && pertenece == false) {
                    pertenece = perteneceAux(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return pertenece;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object padre(Object hijo) {
        Object padre = null;
        if (this.raiz != null) {
            if (!hijo.equals(this.raiz.getElemento())) {
                padre = padreAux(this.raiz, hijo);
            }
        }
        return padre;
    }

    private Object padreAux(NodoGen nodoPadre, Object elem) {
        Object padre = null;
        if (nodoPadre != null) {
            NodoGen nodoHijo = nodoPadre.getHijoIzquierdo();
            if (nodoHijo != null && nodoHijo.getElemento().equals(elem)) {
                padre = nodoPadre.getElemento();
            } else {
                while (nodoHijo != null && padre == null) {
                    padre = padreAux(nodoHijo, elem);
                    nodoHijo = nodoHijo.getHermanoDerecho();
                    if (nodoHijo != null && nodoHijo.getElemento().equals(elem)) {
                        padre = nodoPadre.getElemento();
                    }
                }
            }
        }
        return padre;
    }

    public int altura() {
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoGen cursor) {
        //metodo de resolucion: utilizar dos enteros que compare un primer recorrido, con el recorrido final.
        int alturaSubArbol = 0;
        if (cursor != null) {
            NodoGen hijo = cursor.getHijoIzquierdo();
            while (hijo != null) {
                int alturaHijo = 1 + alturaAux(hijo);
                if (alturaSubArbol < alturaHijo) {
                    alturaSubArbol = alturaHijo;
                }
                hijo = hijo.getHermanoDerecho();
            }
        }
        return alturaSubArbol;
    }

    public int nivel(Object elemento) {
        return nivelAux(this.raiz, elemento, 0);
    }

    private int nivelAux(NodoGen cursor, Object elemento, int nivelActual) {
        int nivelEncontrado = -1;
        if (cursor != null) {
            if (cursor.getElemento().equals(elemento)) {
                //System.out.println(cursor.getElemento().toString() + " == " + elemento.toString());
                nivelEncontrado = nivelActual;
            } else {
                //System.out.println(cursor.getElemento().toString() + " != " + elemento.toString());
                NodoGen hijo = cursor.getHijoIzquierdo();
                nivelActual++;
                while (hijo != null && nivelEncontrado == -1) {
                    nivelEncontrado = nivelAux(hijo, elemento, nivelActual);
                    hijo = hijo.getHermanoDerecho();
                }

            }
        }
        return nivelEncontrado;
    }

    public Lista ancestros(Object elemento) {
        Lista ancestros = new Lista();
        ancestrosAux(this.raiz, elemento, ancestros);
        return ancestros;
    }

    private boolean ancestrosAux(NodoGen cursor, Object elemento, Lista ancestros) {
        boolean encontrado = false;
        if (cursor != null) {
            if (cursor.getElemento().equals(elemento)) {
                encontrado = true;
            } else {
                NodoGen hijo = cursor.getHijoIzquierdo();
                while (hijo != null && !encontrado) {
                    encontrado = ancestrosAux(hijo, elemento, ancestros);
                    if (encontrado) {
                        ancestros.insertar(cursor.getElemento(), 1);
                    }
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return encontrado;
    }

    @Override
    public String toString() {

        return toStringAux(this.raiz);
    }

    public String toStringAux(NodoGen nodo) {
        String mensaje = "";
        if (nodo != null) {
            mensaje += nodo.getElemento().toString() + " -> ";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                mensaje += hijo.getElemento().toString() + ", ";
                hijo = hijo.getHermanoDerecho();
            }
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                mensaje += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return mensaje;
    }

    public Lista listarInorden() {
        Lista salida = new Lista();
        listarInOrdenAux(this.raiz, salida);
        return salida;
    }

    private void listarInOrdenAux(NodoGen n, Lista ls) {
        if (n != null) {
            if (n.getHijoIzquierdo() != null) {
                listarInOrdenAux(n.getHijoIzquierdo(), ls);
            }
            //visita el nodo n
            ls.insertar(n.getElemento(), ls.longitud() + 1);
            //llamados recrusivos con los otros hijos de n 
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInOrdenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPorNiveles() {
        Lista lis = new Lista();
        Cola colaAux = new Cola();
        NodoGen cursor;
        if (this.raiz != null) {
            colaAux.poner(this.raiz);
            lis.insertar(this.raiz.getElemento(), lis.longitud() + 1);
            while (!colaAux.esVacia()) {
                cursor = (NodoGen) colaAux.obtenerFrente();
                colaAux.sacar();
                NodoGen hijo = cursor.getHijoIzquierdo();

                //System.out.println("cursor: " + cursor.getElemento().toString());
                while (hijo != null) {
                    colaAux.poner(hijo);
                    lis.insertar(hijo.getElemento(), lis.longitud() + 1);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lis;
    }

    public void vaciar() {
        this.raiz = null;
    }

    private boolean equalsAux(NodoGen actual, NodoGen otroArbol) {
        boolean exito = true;

        if (actual != null && otroArbol != null) {
            if (actual.getElemento().equals(otroArbol.getElemento())) {
                NodoGen hijo1 = actual.getHijoIzquierdo();
                NodoGen hijo2 = otroArbol.getHijoIzquierdo();
                while (hijo1 != null && hijo2 != null && exito) {
                    exito = equalsAux(hijo1, hijo2);
                    hijo1 = hijo1.getHermanoDerecho();
                    hijo2 = hijo2.getHermanoDerecho();
                }
                exito = hijo1 == null && hijo2 == null;
            } else {
                exito = false;
            }
        } else {
            exito = actual == null && otroArbol == null;
        }
        return exito;
    }

    public boolean equals(ArbolGen otro) {
        boolean sonIguales;
        sonIguales = equalsAux(this.raiz, otro.raiz);
        return sonIguales;

    }

    private boolean sonFronteraRecursivo(NodoGen actual, Lista lista) {
        boolean sonFrontera = true;

        if (actual != null && sonFrontera) {
            if (actual.getHijoIzquierdo() == null && lista.localizar(actual.getElemento()) == -1) {
                sonFrontera = false;
            } else {
                NodoGen hijo = actual.getHijoIzquierdo();
                while (hijo != null && sonFrontera) {
                    sonFrontera = sonFronteraRecursivo(hijo, lista);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return sonFrontera;
    }

    public boolean sonFrontera(Lista lista) {
        boolean listaValida = (!lista.esVacia()) && (!lista.verifRepeticiones());
        boolean sonFrontera = true;
        if (listaValida) {
            sonFrontera = sonFronteraRecursivo(this.raiz, lista);
        } else {
            sonFrontera = false;
        }
        return sonFrontera;
    }

    public ArbolGen clone() {
        ArbolGen copia = new ArbolGen();
        copia.raiz = cloneAux(this.raiz);
        return copia;
    }

    private NodoGen cloneAux(NodoGen cursor) {
        NodoGen nodoClon = null;
        NodoGen hijoClon = null;

        if (cursor != null) {
            nodoClon = new NodoGen(cursor.getElemento(), null, null);
            hijoClon = cursor.getHijoIzquierdo();
            nodoClon.setHijoIzquierdo(cloneAux(hijoClon));
            NodoGen hermanoCopia = nodoClon.getHijoIzquierdo();
            while (hijoClon != null) {
                hermanoCopia.setHermanoDerecho(cloneAux(hijoClon.getHermanoDerecho()));
                hijoClon = hijoClon.getHermanoDerecho();
                hermanoCopia = hermanoCopia.getHermanoDerecho();
            }
        }
        return nodoClon;
    }

    public Lista listarPosorden() {
        Lista salida = new Lista();
        if (this.raiz != null) {
            listarPosOrdenAux(this.raiz, salida);
        }
        return salida;
    }

    private void listarPosOrdenAux(NodoGen cursor, Lista ls) {
        if (cursor != null) {
            NodoGen hijo = cursor.getHijoIzquierdo();
            if (hijo == null) {
                ls.insertar(cursor.getElemento(), ls.longitud() + 1);
            } else {
                while (hijo != null) {
                    listarPosOrdenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
                ls.insertar(cursor.getElemento(), ls.longitud() + 1);
            }
        }
    }

    public Lista listarPreorden() {
        Lista preOrden = new Lista();
        if (this.raiz != null) {
            listarPreordenAux(this.raiz, preOrden);
        }
        return preOrden;
    }

    private void listarPreordenAux(NodoGen cursor, Lista ls) {
        if (cursor != null) {
            ls.insertar(cursor.getElemento(), ls.longitud() + 1);
            NodoGen hijo = cursor.getHijoIzquierdo();
            while (hijo != null) {
                listarPreordenAux(hijo, ls);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    private int grado(NodoGen n) {
        int gradosTot = 0;

        if (n != null) {
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                int gradoActual = grado(hijo);
                hijo = hijo.getHermanoDerecho();
                if (gradoActual > gradosTot) {
                    gradosTot = gradoActual;
                }

            }
            gradosTot++;

        } else {
            gradosTot = -1;
        }
        return gradosTot;
    }

    public int grado() {
        int grados = grado(this.raiz);
        return grados;
    }

    private int gradoSubarbolAux(Object elem, NodoGen n) {
        NodoGen actual = obtenerNodo(n, elem);
        int grados = 0;

        if (actual != null) {
            NodoGen hijo = actual.getHijoIzquierdo();
            while (hijo != null) {
                grados++;
                hijo = hijo.getHermanoDerecho();
            }
        } else {
            grados = -1;
        }
        return grados;
    }

    public int gradoSubarbol(Object elem) {
        int grados = gradoSubarbolAux(elem, this.raiz);
        return grados;

    }

    private NodoGen obtenerNodo(NodoGen n, Object elem) {
        //Método que busca un nodo.
        NodoGen hijo, encontrado;
        encontrado = null;
        if (n != null) {
            if (n.getElemento().equals(elem)) {
                encontrado = n;
            } else {
                hijo = n.getHijoIzquierdo();
                while (hijo != null && encontrado == null) {
                    encontrado = obtenerNodo(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return encontrado;
    }

    public boolean verificarCamino(Lista unaLista) {
        Lista copia = unaLista.clone();
        return verificarCaminoAux(this.raiz, copia);
    }

    private boolean verificarCaminoAux(NodoGen cursor, Lista unaLista) {
        boolean esCamino = unaLista.esVacia();
        if (cursor != null && !esCamino) {
            boolean coincide = cursor.getElemento().equals(unaLista.recuperar(1));
            if (coincide) {
                unaLista.eliminar(1);
                esCamino = verificarCaminoAux(cursor.getHijoIzquierdo(), unaLista);
            } else {

                while (cursor != null && !esCamino) {
                    cursor = cursor.getHermanoDerecho();
                    esCamino = verificarCaminoAux(cursor, unaLista);
                }

            }
        }
        return esCamino;
    }

    public Lista caminoMasLargo() {
        Lista mejor = new Lista();
        mejor = caminoMasLargoAux(this.raiz, mejor, new Lista(), 1);
        return mejor;
    }

    private Lista caminoMasLargoAux(NodoGen cursor, Lista mejor, Lista aux, int i) {

        if (cursor != null) {
            NodoGen hijo = cursor.getHijoIzquierdo();
            while (hijo != null) {
                aux.insertar(cursor.getElemento(), aux.longitud() + 1);
                mejor = caminoMasLargoAux(hijo, mejor, aux, i + 1);
                if (mejor.longitud() < aux.longitud()) {
                    mejor = aux.clone();
                }
                aux.eliminar(aux.longitud());
                hijo = hijo.getHermanoDerecho();
            }
            aux.insertar(cursor.getElemento(), aux.longitud() + 1);
            if (mejor.longitud() < aux.longitud()) {
                mejor = aux.clone();
            }
            aux.eliminar(aux.longitud());

        }
        return mejor;
    }

    private Lista caminoMasLargoAux3(NodoGen n, Lista masLarga, Lista actual) {
        if (n != null) {
            actual.insertar(n.getElemento(), actual.longitud() + 1);
            NodoGen hijo = n.getHijoIzquierdo();
            if (hijo == null && actual.longitud() > masLarga.longitud()) {
                masLarga = actual.clone();
            } else {
                while (hijo != null) {
                    masLarga = caminoMasLargoAux3(hijo, masLarga, actual);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return masLarga;
    }

}
