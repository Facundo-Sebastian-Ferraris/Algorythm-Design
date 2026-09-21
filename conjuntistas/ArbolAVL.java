package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author Ferraris Facundo Sebastian
 */
public class ArbolAVL {

    //constructores
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    //pertenece
    public boolean pertenece(Comparable elemento) {
        //considerar arbol vacio y no vacio
        return perteneceAux(this.raiz, elemento);
    }

    private boolean perteneceAux(NodoAVL cursor, Object elemento) {
        boolean pertenece = false;
        if (cursor != null) {
            int diferencia = cursor.getElem().compareTo(elemento);
            pertenece = diferencia == 0;
            if (diferencia > 0) {
                pertenece = pertenece || perteneceAux(cursor.getHijoIzquierdo(), elemento);
            } else if (diferencia < 0) {
                pertenece = pertenece || perteneceAux(cursor.getHijoDerecho(), elemento);
            }
        }
        return pertenece;
    }

    //insertadores
    public boolean insertar(Comparable elemento) {
        boolean exito;
        if (this.raiz != null) {
            exito = insertarAux(this.raiz, elemento);
            if (exito) {
                this.raiz = derivador(this.raiz);
            }
        } else {
            this.raiz = new NodoAVL(elemento, null, null);
            //hubo recalc altura
            exito = true;
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL cursor, Comparable elemento) {
        boolean exito = false; // asume que el elemento es igual
        if (elemento.compareTo(cursor.getElem()) > 0) {
            if (cursor.getHijoDerecho() != null) {
                exito = insertarAux(cursor.getHijoDerecho(), elemento);
                if (exito) {
                    cursor.setHijoDerecho(derivador(cursor.getHijoDerecho()));
                }
            } else {
                cursor.setHijoDerecho(new NodoAVL(elemento, null, null));
                exito = true;
            }
        } else if (elemento.compareTo(cursor.getElem()) < 0) {
            if (cursor.getHijoIzquierdo() != null) {
                exito = insertarAux(cursor.getHijoIzquierdo(), elemento);
                if (exito) {
                    cursor.setHijoIzquierdo(derivador(cursor.getHijoIzquierdo()));
                }
            } else {
                cursor.setHijoIzquierdo(new NodoAVL(elemento, null, null));
                exito = true;
            }
        }
        return exito;
    }

    //eliminadores
    public boolean eliminar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, null, elemento, false);
            if (exito) {
                this.raiz = derivador(this.raiz);
                //hubo recalc altura
            }
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVL cursor, NodoAVL cursorPadre, Comparable elemento, boolean esIzquierda) {
        boolean exito = false;
        if (cursor != null) {
            int diferencia = elemento.compareTo(cursor.getElem());

            exito = diferencia == 0;
            if (diferencia < 0) {
                exito = eliminarAux(cursor.getHijoIzquierdo(), cursor, elemento, true);
                if (exito) {
                    cursor.setHijoIzquierdo(derivador(cursor.getHijoIzquierdo()));
                }
            } else if (diferencia > 0) {
                exito = eliminarAux(cursor.getHijoDerecho(), cursor, elemento, false);
                if (exito) {
                    cursor.setHijoDerecho(derivador(cursor.getHijoDerecho()));
                }
            } else {
                if (esIzquierda) {
                    cursorPadre.setHijoIzquierdo(derivador(derivadorEliminar(cursor)));
                } else {
                    if (cursorPadre != null) {
                        cursorPadre.setHijoDerecho(derivadorEliminar(cursor));
                    } else {
                        this.raiz = derivadorEliminar(this.raiz);
                        //hubo recalcular altura
                    }
                }
            }
        }
        return exito;
    }

    private NodoAVL derivadorEliminar(NodoAVL n) {
        NodoAVL r = n;
        switch (codigoHijo(r)) {
            case 0 ->
                r = null;
            case 1 ->
                r = r.getHijoIzquierdo();

            case 2 ->
                r = r.getHijoDerecho();

            case 3 -> {
                r.setElem(encontrarCandidato(r).getElem());
                r.setHijoIzquierdo(derivador(r.getHijoIzquierdo()));
            }
            default ->
                throw new AssertionError();
        }
        return r;
    }

    private int codigoHijo(NodoAVL cursor) {
        int codCantH = 0;//no tiene hijo
        if (cursor.getHijoIzquierdo() != null) {
            codCantH += 1;//tiene un hijo izquierdo
        }
        if (cursor.getHijoDerecho() != null) {
            codCantH += 2;//tiene un hijo derecho
        }
        return codCantH;
    }

    private NodoAVL encontrarCandidato(NodoAVL reemplazo) {
        NodoAVL candidato = reemplazo.getHijoIzquierdo();
        if (candidato.getHijoDerecho() != null) {
            NodoAVL padreCandidato = candidato;
            candidato = candidato.getHijoDerecho();
            candidato = encontrarCandidatoAux(candidato, padreCandidato);
        }
        return candidato;
    }

    private NodoAVL encontrarCandidatoAux(NodoAVL candidato, NodoAVL padreCandidato) {
        if (candidato != null) {
            if (candidato.getHijoDerecho() == null) {
                padreCandidato.setHijoDerecho(derivadorEliminar(candidato));
            } else {
                candidato = encontrarCandidatoAux(candidato.getHijoDerecho(), padreCandidato.getHijoDerecho());
                candidato.setHijoDerecho(derivador(candidato.getHijoDerecho()));
            }
        }
        return candidato;
    }

    //rotaciones
    private NodoAVL derivador(NodoAVL n) {
        NodoAVL r = n;
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

    private int balance(NodoAVL n) {
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

    private NodoAVL rotacionDerecha(NodoAVL r) {
        NodoAVL h = r.getHijoIzquierdo();
        NodoAVL temp = h.getHijoDerecho();
        r.setHijoIzquierdo(temp);
        h.setHijoDerecho(r);
        return h;
    }

    private NodoAVL rotacionIzquierda(NodoAVL r) {
        NodoAVL h = r.getHijoDerecho();
        NodoAVL temp = h.getHijoIzquierdo();
        r.setHijoDerecho(temp);
        h.setHijoIzquierdo(r);
        return h;
    }

    //observadores
    public Lista listar() {
        Lista r = new Lista();
        listarAux(this.raiz, r);
        return r;
    }

    private void listarAux(NodoAVL n, Lista r) {
        if (n != null) {
            listarAux(n.getHijoIzquierdo(), r);
            r.insertar(n.getElem(), r.longitud() + 1);
            listarAux(n.getHijoDerecho(), r);
        }
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista r = new Lista();
        listarRangoAux(this.raiz, r, min, max);
        return r;
    }

    private void listarRangoAux(NodoAVL n, Lista r, Comparable min, Comparable max) {
        if (n != null) {
            if (n.getElem().compareTo(min) >= 0) {
                listarRangoAux(n.getHijoIzquierdo(), r, min, max);
            }
            if (n.getElem().compareTo(min) >= 0 && n.getElem().compareTo(max) <= 0) {
                r.insertar(n.getElem(), r.longitud() + 1);
            }   
            if (n.getElem().compareTo(max) <= 0) {
                listarRangoAux(n.getHijoDerecho(), r, min, max);
            }
        }
    }

    public Comparable minimoElem() {
        Comparable r = null;
        if (this.raiz != null) {
            r = minimoElemAux(this.raiz);
        }
        return r;
    }

    private Comparable minimoElemAux(NodoAVL n) {
        Comparable r = n.getElem();
        if (n.getHijoIzquierdo() != null) {
            r = minimoElemAux(n.getHijoIzquierdo());
        }
        return r;
    }

    public Comparable maximoElem() {
        Comparable r = null;
        if (this.raiz != null) {
            r = maximoElemAux(this.raiz);
        }
        return r;
    }

    private Comparable maximoElemAux(NodoAVL n) {
        Comparable r = n.getElem();
        if (n.getHijoDerecho() != null) {
            r = maximoElemAux(n.getHijoDerecho());
        }
        return r;
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

    private String toStringAux(NodoAVL cursor) {
        String mensaje = "";
        if (cursor != null) {
            NodoAVL izquierdo = cursor.getHijoIzquierdo(),
                    derecho = cursor.getHijoDerecho();
            mensaje = "Nodo:" + cursor.getElem().toString() + " (Altura:" + cursor.getAltura() + ")(Balance:" + balance(cursor) + ")\t";
            if (izquierdo != null) {
                mensaje += "HI: " + izquierdo.getElem().toString() + "||\t";
            } else {
                mensaje += "HI: " + izquierdo + "||\t";
            }
            if (derecho != null) {
                mensaje += "HD: " + derecho.getElem().toString() + "||\n";
            } else {
                mensaje += "HD: " + derecho + "||\n";
            }

            mensaje += toStringAux(izquierdo);
            mensaje += toStringAux(derecho);

        }
        return mensaje;
    }

}
