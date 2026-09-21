/*
GRAFO: conjunto de vertices o nodos unidos por enlaces llamados aristas o arcos 
que representa la relacion
Es un par ordenado de conjunto de Nodos/Vertices y de conjunto de arcos/aristas
Existen dos tipos de Grafos:
    No Dirigido:    donde la relacion entre dos nodos es simetrica.
    Dirigido:       donde la relacion entre dos nodos NO es simetrica, donde el
                    el primero es el nodo inicial y el ultimo es final.
Tambien se clasifican en etiquetados ya que los arcos pueden contener informacion
relacionado a los Nodos.

El orden de un grafo, es la cantidad de Nodos que hay en total.
El grado de un vertice es el numero de arcos que tiene como extremo.
Adyacentes: 2 vertices unidos por una arista, y en un digrafo el nodo destino es
            adyacente al nodo origen.


 */
package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import java.util.HashMap;

/**
 *
 * @author Facundo Sebastian Ferraris
 */
public class Grafo {

    private NodoVert inicio;

    //CONSTRUCTOR
    public Grafo() {
        inicio = null;
    }

    //INSERTADORES
    public boolean insertarVertice(Object nuevoVertice) {
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        boolean exito = aux == null;
        if (exito) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, Object tag) {
        //verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }
        boolean exito = auxO != null && auxD != null;
        if (exito) {
            NodoAdy arc = auxO.getPrimerAdy();
            while (arc != null
                    && !(arc.getVertice().getElem().equals(destino) && ((arc.getEtiqueta() != null && arc.getEtiqueta().equals(tag)) || (arc.getEtiqueta() == null && tag == null)))) {

                arc = arc.getSigAdyacente();
            }
            exito = arc == null;
            if (exito) {
                auxO.setPrimerAdy(new NodoAdy(tag, auxD, auxO.getPrimerAdy()));
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino) {
        return insertarArco(origen, destino, null);
    }

    //ELIMINADORES
    public boolean eliminarVertice(Object e) {
        boolean r = false;
        if (ubicarVertice(e) != null) {
            if (this.inicio.equals(e)) {
                this.inicio = this.inicio.getSigVertice();
                r = true;
            } else {
                this.inicio.setPrimerAdy(eliminarTodosArcosDirigidos(this.inicio.getPrimerAdy(), e));
            }
            NodoVert aux = this.inicio;
            while (aux != null && aux.getSigVertice() != null) {
                if (!r && aux.getSigVertice().getElem().equals(e)) {
                    aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    r = true;
                }
                aux.setPrimerAdy(eliminarTodosArcosDirigidos(aux.getPrimerAdy(), e));
                aux = aux.getSigVertice();
            }

        }

        //eliminar los arcos y luego el nodo
        return r;
    }

    private NodoAdy eliminarTodosArcosDirigidos(NodoAdy a, Object e) {
        if (a != null) {
            while (a != null && a.getVertice().getElem().equals(e)) {
                a = a.getSigAdyacente();
            }
            NodoAdy aux = a;
            if (a != null) {
                while (aux.getSigAdyacente() != null) {
                    if (aux.getSigAdyacente().getVertice().getElem().equals(e)) {
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    } else {
                        aux = aux.getSigAdyacente();
                    }
                }
            }

        }

        return a;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        //verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            //si ambos vertices existen, busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    //BUSCADORES
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    //OBSERVADORES
    public boolean esVacio() {
        return inicio == null;
    }

    //Camino mas corto y largo
    public NodoVert[] ubicarOrigenYDestino(Object origen, Object destino) {// [0] = NodoOrigen, [1] = NodoDestino
        NodoVert[] r = new NodoVert[2];
        NodoVert aux = this.inicio;
        while ((r[0] == null || r[1] == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                r[0] = aux;
            }
            if (aux.getElem().equals(destino)) {
                r[1] = aux;
            }
            aux = aux.getSigVertice();
        }
        return r;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista r = new Lista();
        NodoVert[] nodos = ubicarOrigenYDestino(origen, destino);
        if (nodos[0] != null && nodos[1] != null) {
            r = caminoMasLargo(nodos[0], nodos[1].getElem(), new Lista(), new Lista());
        }
        return r;
    }

    private Lista caminoMasLargo(NodoVert vertice, Object destino, Lista caminoMasLargo, Lista caminoAuxiliar) {
        caminoAuxiliar.insertar(vertice.getElem(), caminoAuxiliar.longitud() + 1);
        if (vertice.getElem().equals(destino)) {
            if (caminoAuxiliar.longitud() > caminoMasLargo.longitud()) {
                caminoMasLargo = caminoAuxiliar.clone();
            }
        } else {
            NodoAdy adyacente = vertice.getPrimerAdy();
            while (adyacente != null) {
                if (caminoAuxiliar.localizar(adyacente.getVertice().getElem()) < 0) {
                    caminoMasLargo = caminoMasLargo(adyacente.getVertice(), destino, caminoMasLargo, caminoAuxiliar);
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        caminoAuxiliar.eliminar(caminoAuxiliar.longitud());
        return caminoMasLargo;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista r = new Lista();
        NodoVert[] nodos = ubicarOrigenYDestino(origen, destino);
        if (nodos[0] != null && nodos[1] != null) {
            r = caminoMasCortoAux(nodos[0], nodos[1].getElem(), new Lista(), new Lista());
        }
        return r;
    }

    private Lista caminoMasCortoAux(NodoVert vertice, Object destino, Lista caminoMasCorto, Lista caminoAuxiliar) {
        caminoAuxiliar.insertar(vertice.getElem(), caminoAuxiliar.longitud() + 1);
        if (vertice.getElem().equals(destino)) {
            if (caminoMasCorto.esVacia() || caminoAuxiliar.longitud() < caminoMasCorto.longitud()) {
                caminoMasCorto = caminoAuxiliar.clone();
            }
        } else {
            NodoAdy adyacente = vertice.getPrimerAdy();
            while (adyacente != null) {
                if (caminoAuxiliar.localizar(adyacente.getVertice().getElem()) < 0) {
                    caminoMasCorto = caminoMasCortoAux(adyacente.getVertice(), destino, caminoMasCorto, caminoAuxiliar);
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        caminoAuxiliar.eliminar(caminoAuxiliar.longitud());
        return caminoMasCorto;
    }

    //Camino mas costoso y corto
    //Camino mas costoso y corto
    public Lista caminoMenosCostoso(Object origen, Object destino) {
        Lista r = new Lista();
        double[] kilometros = new double[2];
        kilometros[0] = 0;
        kilometros[1] = 0;
        NodoVert[] nodos = ubicarOrigenYDestino(origen, destino);
        if (nodos[0] != null && nodos[1] != null) {
            r = caminoMenosCostoso(nodos[0], nodos[1].getElem(), new Lista(), new Lista(), kilometros);
        }
        return r;
    }

    private Lista caminoMenosCostoso(NodoVert vertice, Object destino, Lista caminoMenosCostoso, Lista caminoAuxiliar, double[] kilometros) {
        caminoAuxiliar.insertar(vertice.getElem(), caminoAuxiliar.longitud() + 1);
        NodoAdy adyacente = vertice.getPrimerAdy();
        if (adyacente != null) {
            kilometros[1] += (double) adyacente.getEtiqueta();
        }
        if (vertice.getElem().equals(destino)) {
            if (kilometros[0] == 0 || kilometros[1] < kilometros[0]) {
                caminoMenosCostoso = caminoAuxiliar.clone();
                kilometros[0] = kilometros[1];
            }
        } else {
            while (adyacente != null) {
                if (caminoAuxiliar.localizar(adyacente.getVertice().getElem()) < 0) {
                    caminoMenosCostoso = caminoMenosCostoso(adyacente.getVertice(), destino, caminoMenosCostoso, caminoAuxiliar, kilometros);
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        caminoAuxiliar.eliminar(caminoAuxiliar.longitud());
        return caminoMenosCostoso;
    }

//Listar en profundidad
    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        //define un nuevo vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                //si el vertice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            //marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                //visita en profundidad los adyacenes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }

    }

    //Listar en Anchura
    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                //si el vertice no fue visitado aun, avanza en anchura
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();

        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert u, Lista visitados) {
        Cola q = new Cola();
        visitados.insertar(u.getElem(), visitados.longitud() + 1);
        q.poner(u);
        while (!q.esVacia()) {
            u = (NodoVert) q.obtenerFrente();
            NodoAdy v = u.getPrimerAdy();
            q.sacar();

            while (v != null) {
                if (visitados.localizar(v.getVertice().getElem()) < 0) {
                    visitados.insertar(v.getVertice().getElem(), visitados.longitud() + 1);
                    q.poner(v.getVertice());
                }
                v = v.getSigAdyacente();
            }
        }
    }

    //Existe camino
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        //verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            //si ambos vertices existen, busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    public boolean existeVertice(Object e) {
        return ubicarVertice(e) != null;
    }

    public boolean existeArco(Object origen, Object destino) {
        boolean existe = false;
        NodoVert aux = ubicarVertice(origen);
        if (aux != null) {
            NodoAdy arc = aux.getPrimerAdy();
            while (arc != null && !arc.getVertice().getElem().equals(destino)) {
                arc = arc.getSigAdyacente();
            }
            existe = arc != null;
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            //si vertice n es el destino: Hay Camino!
            exito = n.getElem().equals(dest);
            if (!exito) {
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    //toString

    @Override
    public String toString() {
        String r = "Grafo Vacio";
        if (inicio != null) {
            r = "------------------";
            NodoVert vertAux = inicio;
            while (vertAux != null) {
                r += "\nNodo: " + vertAux.getElem().toString();
                NodoAdy adyAux = vertAux.getPrimerAdy();
                r += "\nArcos: ";
                if (adyAux == null) {
                    r += "No tiene xd";
                } else {
                    while (adyAux != null) {
                        r += "   " + adyAux.getVertice().getElem().toString() + "(";
                        if (adyAux.getEtiqueta() != null) {
                            r += adyAux.getEtiqueta().toString();
                        }
                        r += ")";
                        adyAux = adyAux.getSigAdyacente();
                    }
                }
                r += "\n------------------";
                vertAux = vertAux.getSigVertice();
            }
        }
        return r;
    }

    //clone
    public Grafo clone() {
        Grafo clon = new Grafo();
        if (this.inicio != null) {
            //Guarda las estructuras que se van creando;
            HashMap creados = new HashMap();

            //Crear auxiliares
            NodoVert verticeAuxiliar = this.inicio;
            NodoAdy arcoAuxiliar = verticeAuxiliar.getPrimerAdy();
            //Crear nodosClones
            NodoVert verticeClon = new NodoVert(verticeAuxiliar.getElem(), null, null);
            NodoAdy arcoClon;
            creados.put(verticeClon.getElem().hashCode(), verticeClon);

            //colocando primer Nodo con sus arcos
            clon.inicio = verticeClon;

            if (arcoAuxiliar != null) {
                int hashCode = arcoAuxiliar.getVertice().getElem().hashCode();
                if (!creados.containsKey(hashCode)) {
                    creados.put(hashCode, new NodoVert(arcoAuxiliar.getVertice().getElem(), null, null));
                }
                arcoClon = new NodoAdy(arcoAuxiliar.getEtiqueta(), (NodoVert) creados.get(hashCode), null);
                verticeClon.setPrimerAdy(arcoClon);
                //colocar restos de arcos del primer nodo
                while (arcoAuxiliar.getSigAdyacente() != null) {
                    arcoAuxiliar = arcoAuxiliar.getSigAdyacente();
                    hashCode = arcoAuxiliar.getVertice().getElem().hashCode();
                    if (!creados.containsKey(hashCode)) {
                        creados.put(hashCode, new NodoVert(arcoAuxiliar.getVertice().getElem(), null, null));
                    }
                    NodoAdy arcoNuevo = new NodoAdy(arcoAuxiliar.getEtiqueta(), (NodoVert) creados.get(hashCode), null);
                    arcoClon.setSigAdyacente(arcoNuevo);
                    arcoClon = arcoNuevo;
                }
            }
            //Colocar resto de vertices del grafo
            while (verticeAuxiliar.getSigVertice() != null) {
                verticeAuxiliar = verticeAuxiliar.getSigVertice();
                int hashCode = verticeAuxiliar.getElem().hashCode();
                if (!creados.containsKey(hashCode)) {
                    creados.put(hashCode, new NodoVert(verticeAuxiliar.getElem(), null, null));
                }
                verticeClon.setSigVertice((NodoVert) creados.get(hashCode));
                verticeClon = verticeClon.getSigVertice();
                //asignar arco del nodo acutal
                arcoAuxiliar = verticeAuxiliar.getPrimerAdy();
                if (arcoAuxiliar != null) {
                    hashCode = arcoAuxiliar.getVertice().getElem().hashCode();
                    if (!creados.containsKey(hashCode)) {
                        creados.put(hashCode, new NodoVert(arcoAuxiliar.getVertice().getElem(), null, null));
                    }
                    arcoClon = new NodoAdy(arcoAuxiliar.getEtiqueta(), (NodoVert) creados.get(hashCode), null);
                    verticeClon.setPrimerAdy(arcoClon);
                    //colocar restos de arcos del nodo actual
                    while (arcoAuxiliar.getSigAdyacente() != null) {
                        arcoAuxiliar = arcoAuxiliar.getSigAdyacente();
                        hashCode = arcoAuxiliar.getVertice().getElem().hashCode();
                        if (!creados.containsKey(hashCode)) {
                            creados.put(hashCode, new NodoVert(arcoAuxiliar.getVertice().getElem(), null, null));
                        }
                        NodoAdy arcoNuevo = new NodoAdy(arcoAuxiliar.getEtiqueta(), (NodoVert) creados.get(hashCode), null);
                        arcoClon.setSigAdyacente(arcoNuevo);
                        arcoClon = arcoNuevo;
                    }
                }
            }
        }
        return clon;
    }

}
