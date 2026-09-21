/*
UML:
<->raiz: nodo arbol //hecho!!!!!!
------------------------
<+>ArbolBin() //hecho!!!!!!

<+>insertar(TipoElemento, TipoElemento, char): boolean //hecho!!!!!!
<->obtenerNodo(NodoArbol, Object): NodoArbol //hecho!!!!!!

<+>esVacio(): boolean //hecho!!!!!!

<+>padre(TipoElemento): TipoElemento//hecho!!!!!! 
<->padreAux(NodoArbol, Object): NodoArbol //hecho!!!!!!

<+>altura(): int //hecho!!!!!!
<->alturaAux(NodoArbol): int //hecho!!!!!!

<+>nivel(TipoElemento): int //hecho!!!!!!
<->nivelAux((NodoArbol, Object, int) : int //hecho!!!!!!

<+>vaciar() //hecho!!!!!!

<+>clone(): ArbolBin //hecho!!!!!!
<->cloneAux(NodoArbol): ArbolBin //hecho!!!!!!

<+>toString(): String //hecho!!!!!
<->toStringAux(NodoArbol): String //hecho!!!!

<+>listarPreOrden(): Lista //hecho!!!!!!
<->listarPreordenAux(NodoArbol, Lista) //hecho!!!!!!

<+>listarPosOrden(): Lista
<->listarPosordenAux(NodoArbol, Lista)

<+>listarInOrden(): Lista
<->listarInordenAux(NodoArbol, Lista)

<+>listarPorNiveles(): Lista
<->listarPorNiveles(NodoArbol, Lista)
 */
package jerarquicas;

import lineales.dinamicas.Lista;

public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        boolean exito = true;
        if (this.raiz == null) {
            //si el arbol esta vacio, pone elemNuevo en la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //si arbol no esta vacio, busca el padre
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);

            //si padre existe y lugar no esta ocupado lo pone, sino da error
            if (nPadre != null) {
                if (lugar == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if (lugar == 'D' && nPadre.getDerecho() == null) {
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false;
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        /*metodo privado que busca un elemento y devuelve el nodo
        que lo contiene. Si no se encuentra buscado devuelve null*/
        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                //si el buscado es n, lo devuelve
                resultado = n;
            } else {
                //no es el buscado: busca primero en el HI
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                //si no lo encuentra en el HI, busca en HD
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object padre(Object hijo) {
        Object padre = null;
        if (this.raiz != null && !this.raiz.getElem().equals(hijo)) {
            padre = padreAux(this.raiz, hijo).getElem();
        }
        return padre;
    }

    private NodoArbol padreAux(NodoArbol padre, Object hijo) {
        /*metodo privado que busca un elemento y devuelve el nodo
        que lo contiene. Si no se encuentra buscado devuelve null*/
        NodoArbol resultado = null;
        if (padre != null) {
            if ((padre.getIzquierdo() != null && padre.getIzquierdo().getElem().equals(hijo)) || (padre.getDerecho() != null && padre.getDerecho().getElem().equals(hijo))) {
                //si el elemento buscado en padre es hijo, lo devuelve
                resultado = padre;
            } else {
                //no es el buscado: busca primero en el HI
                resultado = padreAux(padre.getIzquierdo(), hijo);
                //si no lo encuentra en el HI, busca en HD
                if (resultado == null) {
                    resultado = padreAux(padre.getDerecho(), hijo);
                }
            }
        }
        return resultado;
    }

    public int altura() {
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoArbol nodo) {
        int altura = -1;
        if (nodo != null) {
            int alturaIzquierda = alturaAux(nodo.getIzquierdo());
            int alturaDerecha = alturaAux(nodo.getDerecho());
            if (alturaIzquierda > alturaDerecha) {
                altura = alturaIzquierda + 1;
            } else {
                altura = alturaDerecha + 1;
            }
        }
        return altura;
    }

    public int nivel(Object elem) {
        //retorna el nivel dado un nodo
        return calcularNivel(raiz, elem, 0);
    }

    private int calcularNivel(NodoArbol nodo, Object elem, int nivel) {
        int nivelEncontrado = -1; // Inicializa la variable para almacenar el nivel encontrado
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                nivelEncontrado = nivel; // Actualiza el nivel encontrado si el elemento está en el nodo actual
            } else {
                int nivelIzq = calcularNivel(nodo.getIzquierdo(), elem, nivel + 1);
                if (nivelIzq != -1) {
                    nivelEncontrado = nivelIzq; // Actualiza el nivel encontrado si el elemento está en el subárbol izquierdo
                } else {
                    nivelEncontrado = calcularNivel(nodo.getDerecho(), elem, nivel + 1); // Actualiza el nivel encontrado si el elemento está en el subárbol derecho
                }
            }
        }
        return nivelEncontrado;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public ArbolBin clone() {
        ArbolBin arbolClon = new ArbolBin();
        if (this.raiz != null) {
            arbolClon.raiz = cloneAux(this.raiz);
        }
        return arbolClon;
    }

    private NodoArbol cloneAux(NodoArbol nodo) {
        NodoArbol nodoClon = null;
        if (nodo != null) {
            nodoClon = new NodoArbol(nodo.getElem(), null, null);
            nodoClon.setIzquierdo(cloneAux(nodo.getIzquierdo()));
            nodoClon.setDerecho(cloneAux(nodo.getDerecho()));
        }
        return nodoClon;
    }

    @Override
    public String toString() {
        System.out.println("toString");
        String mensaje = toStringAux(this.raiz);
        return mensaje;
    }

    private String toStringAux(NodoArbol nodoEnPos) {
        String mensaje = "";
        NodoArbol izq, dcho;

        if (nodoEnPos != null) {
            mensaje = nodoEnPos.getElem() + "  ";
            izq = nodoEnPos.getIzquierdo();
            dcho = nodoEnPos.getDerecho();

            if (izq != null) {
                mensaje = mensaje + "HI: " + izq.getElem().toString() + " ";
            } else {
                mensaje = mensaje + "HI: " + izq + " ";
            }
            if (dcho != null) {
                mensaje = mensaje + "HD: " + dcho.getElem().toString() + " ";
            } else {
                mensaje = mensaje + "HD: " + dcho + " ";
            }
            mensaje += "\n";
            mensaje += toStringAux(izq);
            mensaje += toStringAux(dcho);
        }

        return mensaje;

    }

    //listas
    public Lista listarPreorden() {
        //retorna una lista con los elementos del arbol en PREORDEN
        Lista lis = new Lista();
        listarPreordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPreordenAux(NodoArbol nodo, Lista lis) {
        //metodo recursivo PRIVADO porque su parametro es de tipo NodoArbol
        if (nodo != null) {
            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(1)

            //recorre a sus hijos en preorden
            listarPreordenAux(nodo.getIzquierdo(), lis); //(2)
            listarPreordenAux(nodo.getDerecho(), lis); //(3)
        }
    }

    public Lista listarPosorden() {
        //retorna una lista con los elementos del arbol en PREORDEN
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPosordenAux(NodoArbol nodo, Lista lis) {
        //metodo recursivo PRIVADO porque su parametro es de tipo NodoArbol
        if (nodo != null) {
            //recorre a sus hijos en posorden
            listarPosordenAux(nodo.getIzquierdo(), lis); //(1)
            listarPosordenAux(nodo.getDerecho(), lis); //(2)

            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(3)
        }
    }

    public Lista listarInorden() {
        //retorna una lista con los elementos del arbol en PREORDEN
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoArbol nodo, Lista lis) {
        //metodo recursivo PRIVADO porque su parametro es de tipo NodoArbol
        if (nodo != null) {

            //recorre a sus hijos en inOrden
            listarInordenAux(nodo.getIzquierdo(), lis); //(1)
            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(2)
            listarInordenAux(nodo.getDerecho(), lis); //(3)   
        }
    }

    private Lista obtenerAncestros(NodoArbol n, Object elem) {
        Lista nueva = new Lista();

        if (n != null) {

            if (n.getElem().equals(elem)) {
                nueva.insertar(n.getElem(), nueva.longitud() + 1);
            } else {
                nueva = obtenerAncestros(n.getIzquierdo(), elem);
                if (nueva.longitud() == 0) {
                    nueva = obtenerAncestros(n.getDerecho(), elem);

                }

                if (nueva.longitud() > 0 && n.getElem() != (elem)) {
                    nueva.insertar(n.getElem(), nueva.longitud() + 1);
                }

            }
        }

        return nueva;
    }

    public Lista mostrarAncestros(Object elem) {
        Lista listaAnc = new Lista();
        if (this.raiz != null) {
            listaAnc = obtenerAncestros(this.raiz, elem);
        }
        return listaAnc;
    }

    public Lista obtenerAncestros(Object elem) {
        Lista lis = new Lista();
        NodoArbol actual;
        actual = ancestrosAux(this.raiz, elem, lis);
        return lis;
    }

    private NodoArbol ancestrosAux(NodoArbol cursor, Object elemento, Lista ancestros) {
        NodoArbol encontrado = null;
        if (cursor != null) {
            if (cursor.getElem().equals(elemento)) {
                encontrado = cursor;
            } else {
                encontrado = ancestrosAux(cursor.getIzquierdo(), elemento, ancestros);
                if (encontrado == null) {
                    encontrado = ancestrosAux(cursor.getDerecho(), elemento, ancestros);
                }
                if (encontrado != null) {
                    ancestros.insertar(cursor.getElem(), 1);
                }
            }
        }
        return encontrado;
    }
}
