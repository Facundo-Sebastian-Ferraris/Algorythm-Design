package TP1.MonticuloBinomial;


public class HeapBinomial {
    private NodoBinomial raizPrincipal;

    public HeapBinomial(){
        raizPrincipal = null;
    }

    public void insertar(Object elemento){
        NodoBinomial nuevo = new NodoBinomial(elemento, null, null);
        if (this.raizPrincipal == null) {
            this.raizPrincipal = nuevo;
        } else {
            nuevo.setHermanoDerecho(this.raizPrincipal);
            this.raizPrincipal = nuevo;

            NodoBinomial cursor = nuevo;
            NodoBinomial hermano = nuevo.getHermanoDerecho();
            boolean mismoOrden = true;  //true para que pueda entrar al while

            while(mismoOrden && hermano!=null){
                mismoOrden = cursor.getOrden() == hermano.getOrden();
                if (mismoOrden) {
                    int compararArboles = comparar(cursor, hermano);
                    if (compararArboles<=0) {
                        cursor.setHermanoDerecho(hermano.getHermanoDerecho());
                        hermano.setHermanoDerecho(null);
                        cursor.agregarHijo(hermano);
                    } else {
                        cursor.setHermanoDerecho(null);
                        hermano.agregarHijo(cursor);
                        this.raizPrincipal = hermano;
                        cursor = hermano;
                    }
                    hermano = cursor.getHermanoDerecho();
                }
            }
        }
    }

    private boolean unir(NodoBinomial previo, NodoBinomial actual, NodoBinomial siguiente){
        boolean r = previo.getOrden() == actual.getOrden();
        if(r){
            if (comparar(previo, actual) < 0) {
                previo.agregarHijo(actual);
                previo.setHermanoDerecho(siguiente);
                actual.setHermanoDerecho(null);
            } else {
                actual.agregarHijo(previo);
            }
        }
        return r;
    }

    private int comparar(NodoBinomial A, NodoBinomial B) {
        return ((Comparable<Object>) A.getElemento()).compareTo(B.getElemento());
    }


    @Override
    public String toString() {
        String r = "";
        NodoBinomial cursor = this.raizPrincipal;
        int i = 1;
        while (cursor != null) {
            String arbol = "---------- Arbol "+ i +" ----------\n"+ toStringAux(cursor) + "\n--------------\n";
            cursor = cursor.getHermanoDerecho();
            r+=arbol;
            i++;
        }
        return r;
    }

    public String toStringAux(NodoBinomial nodo) {
        String mensaje = "";
        if (nodo != null) {
            mensaje += nodo.getElemento().toString() + " -> ";
            NodoBinomial hijo = nodo.getHijoIzquierdo();
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
}
