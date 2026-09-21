package conjuntistas;

import lineales.dinamicas.Lista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author San Sebastian
 */
public class ArbolBB {

    NodoABB raiz;

    //constructor
    public ArbolBB() {
        this.raiz = null;
    }

    //comparadores
    public boolean pertenece(Object elemento) {
        //considerar arbol vacio y no vacio
        return perteneceAux(this.raiz, elemento);
    }

    private boolean perteneceAux(NodoABB cursor, Object elemento) {
        boolean pertenece = false;
        if (cursor != null) {
            if (cursor.getElemento().equals(elemento)) {
                pertenece = true;
            } else {
                pertenece = pertenece || perteneceAux(cursor.getHijoIzquierdo(), elemento);
                pertenece = pertenece || perteneceAux(cursor.getDerecho(), elemento);
            }
        }
        return pertenece;
    }

    //insertadores
    public boolean insertar(Comparable elemento) {
        boolean exito;
        if (this.raiz != null) {
            exito = insertarAux(this.raiz, elemento);
        } else {
            this.raiz = new NodoABB(elemento, null, null);
            exito = true;
        }
        return exito;
    }

    private boolean insertarAux(NodoABB cursor, Comparable elemento) {
        boolean exito = false; // asume que el elemento es igual
        if (cursor != null) {
            if (elemento.compareTo(cursor.getElemento()) > 0) {
                if (cursor.getDerecho() != null) {
                    exito = insertarAux(cursor.getDerecho(), elemento);
                } else {
                    cursor.setDerecho(new NodoABB(elemento, null, null));
                    exito = true;
                }
            } else if (elemento.compareTo(cursor.getElemento()) < 0) {
                if (cursor.getHijoIzquierdo() != null) {
                    exito = insertarAux(cursor.getHijoIzquierdo(), elemento);
                } else {
                    cursor.setIzquierdo(new NodoABB(elemento, null, null));
                    exito = true;
                }
            }
        }
        return exito;
    }

    //eliminadores
    public boolean eliminar(Comparable elemento) {
        return eliminarAux(this.raiz, null, elemento);
    }

    private boolean eliminarAux(NodoABB cursor, NodoABB padreCursor, Comparable elemento) {
        boolean exito = false;
        if (cursor != null) {
            NodoABB encontrado;

            boolean esIzquierdo;
            exito = (cursor.getElemento().equals(elemento));
            if (exito) {//si el elemento se encuentra en el cursor o uno de sus hijos
                //averiguar en cual de los 3 nodos se encuentra

                esIzquierdo = padreCursor != null && padreCursor.getHijoIzquierdo().equals(cursor);
                encontrado = cursor;

                //ver cantidad de hijos
                switch (codigoHijo(encontrado)) {
                    case 0://si no tiene hijos
                        if (padreCursor == null) {//si el nodo es la raiz (unica forma que encontrado = cursor)
                            this.raiz = null;
                        } else {//si no es la raiz
                            if (esIzquierdo) {
                                padreCursor.setIzquierdo(null);
                            } else {
                                padreCursor.setDerecho(null);
                            }
                        }
                        break;
                    case 1://tiene hijo izquierdo
                        if (padreCursor == null) {//si el nodo es la raiz
                            this.raiz = this.raiz.getHijoIzquierdo();
                        } else {//si no es la raiz
                            if (esIzquierdo) {
                                padreCursor.setIzquierdo(encontrado.getHijoIzquierdo());
                            } else {
                                padreCursor.setDerecho(encontrado.getHijoIzquierdo());
                            }
                        }
                        break;
                    case 2://tiene hijo derecho
                        if (padreCursor == null) {//si el nodo es la raiz
                            this.raiz = this.raiz.getDerecho();
                        } else {//si no es la raiz
                            if (esIzquierdo) {
                                cursor.setIzquierdo(encontrado.getDerecho());
                            } else {
                                cursor.setDerecho(encontrado.getDerecho());
                            }
                        }
                        break;
                    case 3: // tiene dos hijos
                        NodoABB candidato = encontrarCandidato(encontrado.getHijoIzquierdo());
                        if (candidato.equals(encontrado.getHijoIzquierdo())) {
                            candidato.setDerecho(encontrado.getDerecho());
                            if (esIzquierdo) {
                                padreCursor.setIzquierdo(candidato);
                            } else {
                                padreCursor.setDerecho(candidato);
                            }
                        } else {
                            encontrado.setElemento(candidato.getElemento());
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            } else {
                int diferencia = cursor.getElemento().compareTo(elemento);
                if (diferencia > 0) {
                    exito = eliminarAux(cursor.getHijoIzquierdo(), cursor, elemento);
                } else {
                    exito = eliminarAux(cursor.getDerecho(), cursor, elemento);
                }
            }
        }
        return exito;
    }//correccion: agregar parametro padre (y modularizar por caso)

    private int codigoHijo(NodoABB cursor) {
        int codCantH = 0;//no tiene hijo
        if (cursor.getHijoIzquierdo() != null) {
            codCantH += 1;//tiene un hijo izquierdo
        }
        if (cursor.getDerecho() != null) {
            codCantH += 2;//tiene un hijo derecho
        }
        return codCantH;
    }

    private NodoABB encontrarCandidato(NodoABB reemplazo) {
        NodoABB padreCandidato = reemplazo;
        NodoABB candidato = reemplazo;
        if (candidato.getDerecho() != null) {
            candidato = padreCandidato.getDerecho();
            while (candidato.getDerecho() != null) {
                padreCandidato = padreCandidato.getDerecho();
                candidato = candidato.getDerecho();
                System.out.println(padreCandidato.getElemento());
            }

            padreCandidato.setDerecho(candidato.getHijoIzquierdo());

        }

        return candidato;
    }

    //observadores
    @Override
    public String toString() {
        String mensaje;
        if (this.raiz == null) {
            mensaje = "Arbol Vacio";
        } else {
            mensaje = toStringAux(this.raiz);
        }
        return mensaje;
    }

    private String toStringAux(NodoABB cursor) {
        String mensaje = "";
        if (cursor != null) {
            NodoABB izquierdo = cursor.getHijoIzquierdo(),
                    derecho = cursor.getDerecho();
            mensaje = "Nodo:" + cursor.getElemento().toString() + "\t";
            if (izquierdo != null) {
                mensaje += "HI: " + izquierdo.getElemento().toString() + "\t";
            } else {
                mensaje += "HI: " + izquierdo + "\t";
            }
            if (derecho != null) {
                mensaje += "HD: " + derecho.getElemento().toString() + "\n";
            } else {
                mensaje += "HD: " + derecho + "\n";
            }

            mensaje += toStringAux(izquierdo);
            mensaje += toStringAux(derecho);

        }
        return mensaje;
    }

    //listar
    public Lista listarInOrden() {
        Lista inOrden = new Lista();
        listarInOrdenAux(this.raiz, inOrden);
        return inOrden;
    }

    private void listarInOrdenAux(NodoABB cursor, Lista inOrden) {
        if (cursor != null) {
            listarInOrdenAux(cursor.getHijoIzquierdo(), inOrden);
            inOrden.insertar(cursor.getElemento(), inOrden.longitud() + 1);
            listarInOrdenAux(cursor.getDerecho(), inOrden);
        }
    }

    public Lista listarRango(int min, int max) {
        Lista rango = new Lista();
        listarRangoAux(min, max, this.raiz, rango);
        return rango;
    }

    private void listarRangoAux(int min, int max, NodoABB cursor, Lista rango) {
        if (cursor != null) {
            Comparable elementoCursor = cursor.getElemento();
            boolean minimo = elementoCursor.compareTo(min) >= 0;
            boolean maximo = elementoCursor.compareTo(max) <= 0;
            if (minimo) {
                listarRangoAux(min, max, cursor.getHijoIzquierdo(), rango);
            }
            if (minimo && maximo) {
                rango.insertar(elementoCursor, rango.longitud() + 1);
            }
            if (maximo) {
                listarRangoAux(min, max, cursor.getDerecho(), rango);
            }
        }
    }

    public Comparable minimo() {
        return minimoAux(this.raiz);
    }

    private Comparable minimoAux(NodoABB cursor) {
        Comparable minimo = null;
        if (cursor != null) {
            if (cursor.getHijoIzquierdo() != null) {
                minimo = minimoAux(cursor.getHijoIzquierdo());
            } else {
                minimo = cursor.getElemento();
            }
        }
        return minimo;
    }

    public Comparable maximo() {
        return maximoAux(this.raiz);
    }

    private Comparable maximoAux(NodoABB cursor) {
        Comparable maximo = null;
        if (cursor != null) {
            if (cursor.getDerecho() != null) {
                maximo = maximoAux(cursor.getDerecho());
            } else {
                maximo = cursor.getElemento();
            }
        }
        return maximo;
    }

    public boolean vacio() {
        return this.raiz == null;
    }

}
