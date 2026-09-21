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
public class Diccionario {

    //constructores
    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    //existe
    public boolean existeClave(Comparable clave) {
        //considerar arbol vacio y no vacio
        return existeClaveAux(this.raiz, clave);
    }

    private boolean existeClaveAux(NodoAVLDicc cursor, Comparable clave) {
        boolean existe = false;
        if (cursor != null) {
            int diferencia = cursor.getClave().compareTo(clave);
            existe = diferencia == 0;
            if (diferencia > 0) {
                existe = existe || existeClaveAux(cursor.getHijoIzquierdo(), clave);
            } else if (diferencia < 0) {
                existe = existe || existeClaveAux(cursor.getHijoDerecho(), clave);
            }
        }
        return existe;
    }

    //insertadores
    public boolean insertar(Comparable clave, Object elem) {
        boolean exito;
        if (this.raiz != null) {
            exito = insertarAux(this.raiz, clave, elem);
            if (exito) {
                this.raiz = derivador(this.raiz);
                this.raiz.recalcularAltura();
            }
        } else {
            this.raiz = new NodoAVLDicc(clave, elem, null, null);
            exito = true;
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc cursor, Comparable clave, Object elem) {
        boolean exito = false; // asume que el elemento es igual
        if (clave.compareTo(cursor.getClave()) > 0) {
            if (cursor.getHijoDerecho() != null) {
                exito = insertarAux(cursor.getHijoDerecho(), clave, elem);
                if (exito) {
                    cursor.setHijoDerecho(derivador(cursor.getHijoDerecho()));
                }
            } else {
                cursor.setHijoDerecho(new NodoAVLDicc(clave, elem, null, null));
                exito = true;
            }
        } else if (clave.compareTo(cursor.getClave()) < 0) {
            if (cursor.getHijoIzquierdo() != null) {
                exito = insertarAux(cursor.getHijoIzquierdo(), clave, elem);
                if (exito) {
                    cursor.setHijoIzquierdo(derivador(cursor.getHijoIzquierdo()));
                }
            } else {
                cursor.setHijoIzquierdo(new NodoAVLDicc(clave, elem, null, null));
                exito = true;
            }
        }
        return exito;
    }

    //eliminadores
    public boolean eliminar(Comparable clave) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, null, clave, false);
            if (exito) {
                this.raiz = derivador(this.raiz);
            }
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc cursor, NodoAVLDicc cursorPadre, Comparable clave, boolean esIzquierda) {
        boolean exito = false;
        if (cursor != null) {
            int diferencia = clave.compareTo(cursor.getClave());
            exito = diferencia == 0;
            if (diferencia < 0) {
                exito = eliminarAux(cursor.getHijoIzquierdo(), cursor, clave, true);
                if (exito) {
                    cursor.setHijoIzquierdo(derivador(cursor.getHijoIzquierdo()));
                }
            } else if (diferencia > 0) {
                exito = eliminarAux(cursor.getHijoDerecho(), cursor, clave, false);
                if (exito) {
                    cursor.setHijoDerecho(derivador(cursor.getHijoDerecho()));
                }
            } else {
                if (esIzquierda) {
                    cursorPadre.setHijoIzquierdo(derivadorEliminar(cursor));
                } else {
                    if (cursorPadre != null) {
                        cursorPadre.setHijoDerecho(derivadorEliminar(cursor));
                    } else {
                        this.raiz = derivadorEliminar(this.raiz);
                    }
                }
            }
        }
        return exito;
    }

    private NodoAVLDicc derivadorEliminar(NodoAVLDicc n) {
        NodoAVLDicc r = n;
        switch (codigoHijo(r)) {
            case 0 ->
                r = null;
            case 1 ->
                r = r.getHijoIzquierdo();

            case 2 ->
                r = r.getHijoDerecho();

            case 3 -> {
                NodoAVLDicc rOriginal = r;
                r = encontrarCandidato(r);
                r.setHijoDerecho(derivador(rOriginal.getHijoDerecho()));
                r.setHijoIzquierdo(derivador(rOriginal.getHijoIzquierdo()));
            }
            default ->
                throw new AssertionError();
        }
        return r;
    }

    private int codigoHijo(NodoAVLDicc cursor) {
        int codCantH = 0;//no tiene hijo
        if (cursor.getHijoIzquierdo() != null) {
            codCantH += 1;//tiene un hijo izquierdo
        }
        if (cursor.getHijoDerecho() != null) {
            codCantH += 2;//tiene un hijo derecho
        }
        return codCantH;
    }

    private NodoAVLDicc encontrarCandidato(NodoAVLDicc reemplazo) {
        NodoAVLDicc padreCandidato = reemplazo.getHijoIzquierdo();
        NodoAVLDicc candidato = padreCandidato;
        if (candidato.getHijoDerecho() != null) {
            candidato = padreCandidato.getHijoDerecho();
            while (candidato.getHijoDerecho() != null) {
                padreCandidato = padreCandidato.getHijoDerecho();
                candidato = candidato.getHijoDerecho();
            }
            padreCandidato.setHijoDerecho(candidato.getHijoIzquierdo());
            candidato.setHijoIzquierdo(padreCandidato);
        }
        candidato.setHijoDerecho(reemplazo.getHijoDerecho());
        return candidato;
    }

    //rotaciones
    private NodoAVLDicc derivador(NodoAVLDicc n) {
        NodoAVLDicc r = n;
        int b = 0;
        if (n != null) {
            b = balance(n);
        }
        if (b >= 2) {
            int bhi = balance(n.getHijoIzquierdo());
            if (bhi < 0) {
                n.setHijoIzquierdo(rotacionIzquierda(n.getHijoIzquierdo()));
            }
            r = rotacionDerecha(n);

        } else if (b <= -2) {
            int bhd = balance(n.getHijoDerecho());
            if (bhd > 0) {
                n.setHijoDerecho(rotacionDerecha(n.getHijoDerecho()));
            }
            r = rotacionIzquierda(n);

        }
        return r;
    }

    private int balance(NodoAVLDicc n) {
        int hi = -1;
        int hd = -1;
        if (n.getHijoDerecho() != null) {
            hd = n.getHijoDerecho().getAltura();
        }
        if (n.getHijoIzquierdo() != null) {
            hi = n.getHijoIzquierdo().getAltura();
        }
        int difAlt = hi - hd;
        return difAlt;
    }

    private NodoAVLDicc rotacionDerecha(NodoAVLDicc r) {
        NodoAVLDicc h = r.getHijoIzquierdo();
        NodoAVLDicc temp = h.getHijoDerecho();
        h.setHijoDerecho(r);
        r.setHijoIzquierdo(temp);
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotacionIzquierda(NodoAVLDicc r) {
        NodoAVLDicc h = r.getHijoDerecho();
        NodoAVLDicc temp = h.getHijoIzquierdo();
        h.setHijoIzquierdo(r);
        r.setHijoDerecho(temp);
        h.recalcularAltura();
        return h;
    }

    //observadores
    public Object obtenerInformacion(Comparable clave) {
        //considerar arbol vacio y no vacio
        return obtenerInformacionAux(this.raiz, clave);

    }

    private Object obtenerInformacionAux(NodoAVLDicc cursor, Comparable clave) {
        Object informacion = null;
        if (cursor != null) {
            int diferencia = cursor.getClave().compareTo(clave);
            if (diferencia == 0) {
                informacion = cursor.getElemento();
            } else if (diferencia > 0) {
                informacion = obtenerInformacionAux(cursor.getHijoIzquierdo(), clave);
            } else if (diferencia < 0) {
                informacion = obtenerInformacionAux(cursor.getHijoDerecho(), clave);
            }
        }
        return informacion;
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista r = new Lista();
        listarRangoAux(this.raiz, r, min, max);
        return r;
    }

    private void listarRangoAux(NodoAVLDicc n, Lista r, Comparable min, Comparable max) {
        if (n != null) {
            if (n.getClave().compareTo(min) > 0) {
                listarRangoAux(n.getHijoIzquierdo(), r, min, max);
            }
            if (n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) <= 0) {
                r.insertar(n.getElemento(), r.longitud() + 1);
            }
            if (n.getClave().compareTo(max) < 0) {
                listarRangoAux(n.getHijoDerecho(), r, min, max);
            }
        }
    }

    public Lista listarClaves() {
        Lista r = new Lista();
        listarClavesAux(this.raiz, r);
        return r;
    }

    private void listarClavesAux(NodoAVLDicc n, Lista r) {
        if (n != null) {
            listarClavesAux(n.getHijoIzquierdo(), r);
            r.insertar(n.getClave(), r.longitud() + 1);
            listarClavesAux(n.getHijoDerecho(), r);
        }
    }

    public Lista listarClavePorPrefijo(Comparable prefijo) {
        Lista r = new Lista();
        listarClavePorPrefijoAux(this.raiz, prefijo, r);
        return r;
    }

    private void listarClavePorPrefijoAux(NodoAVLDicc n, Comparable prefijo, Lista r) {
        if (n != null) {

            listarClavePorPrefijoAux(n.getHijoIzquierdo(), prefijo, r);
            if (n.getClave().toString().startsWith(prefijo.toString())) {
                r.insertar(n.getClave(), r.longitud() + 1);
            }
            listarClavePorPrefijoAux(n.getHijoDerecho(), prefijo, r);

        }
    }

    public Lista listarDatos() {
        Lista r = new Lista();
        listarDatosAux(this.raiz, r);
        return r;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista r) {
        if (n != null) {
            listarClavesAux(n.getHijoIzquierdo(), r);
            r.insertar(n.getClave(), r.longitud() + 1);
            listarClavesAux(n.getHijoDerecho(), r);
        }
    }

    public boolean vacio() {
        return this.raiz == null;
    }

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

    private String toStringAux(NodoAVLDicc cursor) {
        String mensaje = "";
        if (cursor != null) {
            NodoAVLDicc izquierdo = cursor.getHijoIzquierdo(),
                    derecho = cursor.getHijoDerecho();
            mensaje = "Nodo:" + cursor.getClave().toString() + " (Altura:" + cursor.getAltura() + ")(Balance:" + balance(cursor) + ")\t";
            if (izquierdo != null) {
                mensaje += "HI: " + izquierdo.getClave().toString() + "||\t";
            } else {
                mensaje += "HI: " + izquierdo + "||\t";
            }
            if (derecho != null) {
                mensaje += "HD: " + derecho.getClave().toString() + "||\n";
            } else {
                mensaje += "HD: " + derecho + "||\n";
            }

            mensaje += toStringAux(izquierdo);
            mensaje += toStringAux(derecho);

        }
        return mensaje;
    }

}
