package propositoEspecifico;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lineales.dinamicas.Lista;

/**
 *
 * @author Ferraris Facundo
 *
 */
public class MapeoAMuchos {

    private static final int TAMANIO = 50;
    private final NodoHashMapeoM[] tabla;
    private int cant;

    public MapeoAMuchos() {
        this.tabla = new NodoHashMapeoM[TAMANIO];
        this.cant = 0;
    }

    //asociador
    public boolean asociar(Object dominio, Object valorRango) {
        boolean existe,
                exito;
        NodoHashMapeoM dominioAux;
        HashMap rangoAux;
        int claveDominio = funcionHash(dominio),
                keyRango = funcionHash(valorRango),
                direccion = claveDominio % TAMANIO;

        //buscar dominio
        dominioAux = buscarNodo(dominio);
        existe = dominioAux != null;
        //crear dominio si no existe
        if (!existe) {
            this.tabla[direccion] = new NodoHashMapeoM(dominio, new HashMap(), this.tabla[direccion]);
            dominioAux = this.tabla[direccion];
            this.cant++;
        }

        //asegurar valorRango inexistente
        rangoAux = dominioAux.getRango();
        exito = !rangoAux.containsKey(keyRango);
        if (exito) {
            //ingresar en el valor del rango al hashmap
            rangoAux.put(keyRango, valorRango);
        }
        return exito;
    }

    private NodoHashMapeoM buscarNodo(Object dominio) {
        NodoHashMapeoM aux = this.tabla[funcionHash(dominio) % TAMANIO];
        if (aux != null && !aux.getDominio().equals(dominio)) {
            while (aux.getEnlace() != null && !aux.getEnlace().getDominio().equals(dominio)) {
                aux = aux.getEnlace();
            }
            aux = aux.getEnlace();
        }
        return aux;
    }

    //desasociador
    public boolean desasociar(Object dominio, Object valorRango) {
        boolean exito = false;
        int direccion = funcionHash(dominio) % TAMANIO,
                keyRango = funcionHash(valorRango);
        NodoHashMapeoM anteriorAux = this.tabla[direccion],
                aux = anteriorAux;
        HashMap rango;

        //buscar dominio 
        if (!anteriorAux.getDominio().equals(dominio)) {
            aux = aux.getEnlace();
            while (aux != null && !aux.getDominio().equals(dominio)) {
                anteriorAux = anteriorAux.getEnlace();
                aux = aux.getEnlace();
            }
        }

        //ver rango en dominio
        if (aux != null) {
            rango = aux.getRango();
            exito = rango.containsKey(keyRango);
            if (exito) {
                //eliminar valor del rango
                rango.remove(keyRango);
            }
            //eliminar dominio sin rango
            if (rango.isEmpty()) {
                if (anteriorAux == aux) {
                    this.tabla[direccion] = this.tabla[direccion].getEnlace();
                } else {
                    anteriorAux.setEnlace(aux.getEnlace());
                }
                this.cant--;
            }
        }
        return exito;
    }

    public boolean desasociarDominio(Object dominio) {
        boolean exito = false;
        int direccion = funcionHash(dominio) % TAMANIO;
        NodoHashMapeoM anteriorAux = this.tabla[direccion],
                aux = anteriorAux;

        //buscar dominio 
        if (!anteriorAux.getDominio().equals(dominio)) {
            aux = aux.getEnlace();
            while (aux != null && !aux.getDominio().equals(dominio)) {
                anteriorAux = anteriorAux.getEnlace();
                aux = aux.getEnlace();
            }
        }
        if (aux != null) {
            if (anteriorAux == aux) {
                this.tabla[direccion] = this.tabla[direccion].getEnlace();
            } else {
                anteriorAux.setEnlace(aux.getEnlace());
            }
            exito = true;
            this.cant--;
        }
        return exito;
    }

    public boolean desasociarRango(Object valorRango) {
        int direccion = 0;
        int keyRango = funcionHash(valorRango);
        NodoHashMapeoM anteriorAux = this.tabla[direccion], aux = anteriorAux;
        HashMap rango = aux.getRango();
        boolean existe = rango.containsKey(keyRango);

        //buscar dominio 
        if (!existe) {
            aux = aux.getEnlace();
            while (aux != null && !existe) {
                rango = aux.getRango();
                existe = rango.containsKey(keyRango);
                if (!existe) {
                    anteriorAux = anteriorAux.getEnlace();
                    aux = aux.getEnlace();
                }
            }
        }

        //ver rango en dominio
        if (existe) {
            //eliminar dominio sin rango
            if (rango.isEmpty()) {
                if (anteriorAux == aux) {
                    this.tabla[direccion] = this.tabla[direccion].getEnlace();
                } else {
                    anteriorAux.setEnlace(aux.getEnlace());
                }
                this.cant--;
            }
        }
        return existe;
    }

    //Obtenedores
    public HashMap obtenerRango(Object dominio) {
        HashMap rango = new HashMap();
        NodoHashMapeoM aux;
        if (this.cant != 0) {
            aux = buscarNodo(dominio);
            if (aux != null) {
                rango.putAll(aux.getRango());
            }
        }
        return rango;
    }

    public Object obtenerElemento(Object dominio, Object claveRango) {
        NodoHashMapeoM aux = buscarNodo(dominio);
        HashMap rango;
        Object elemento = null;
        if (aux != null) {
            rango = aux.getRango();
            elemento = rango.get(claveRango);
        }
        return elemento;
    }

    public Lista obtenerConjuntoDominio() {
        Lista dominio = new Lista();
        int pos = 0;
        if (this.cant > 0) {
            while (dominio.longitud() < this.cant) {
                listarDominiosAdyacentes(this.tabla[pos], dominio);
                pos++;
            }
        }
        return dominio;
    }

    private void listarDominiosAdyacentes(NodoHashMapeoM n, Lista lis) {
        while (n != null) {
            lis.insertar(n.getDominio(), 1);
            n = n.getEnlace();
        }
    }

    public HashMap obtenerConjuntoRango() {
        /*Retorna todos los valores de tipo rango almacenados en la estructura.*/
        HashMap rangos = new HashMap();
        NodoHashMapeoM aux;
        int pos;
        if (this.cant > 0) {
            pos = 0;
            while (pos < TAMANIO) {
                aux = this.tabla[pos];
                while (aux != null) {
                    putAllSinColision(rangos, aux.getRango());
                    aux = aux.getEnlace();
                }
                pos++;
            }
        }
        return rangos;
    }

    private void putAllSinColision(HashMap receptor, HashMap emisor) {
        emisor.forEach((key, value) -> {
            receptor.put(key, value);
        });
    }

    public boolean esVacio() {
        // Retorna si la estructura esta vacia o no.
        return (this.cant == 0);
    }

    public void vaciar() {
        // Vacia la estructura
        int pos = 0;
        NodoHashMapeoM aux;
        if (this.cant > 0) {
            while (pos < MapeoAMuchos.TAMANIO) {
                aux = this.tabla[pos];
                if (aux != null) {
                    this.tabla[pos] = null;
                }
                pos++;
            }
            this.cant = 0;
        }
    }

    public MapeoAMuchos clone() {
        //metodo que crea un clon de la estructura original
        MapeoAMuchos clon = new MapeoAMuchos();
        NodoHashMapeoM aux, nodoClon = null;
        int direccion = 0;
        if (this.cant > 0) {
            while (clon.cant < this.cant) {
                aux = this.tabla[direccion];
                while (aux != null) {
                    if (clon.tabla[direccion] == null) {
                        clon.tabla[direccion] = new NodoHashMapeoM(aux.getDominio(), aux.getRango(), null);
                        nodoClon = clon.tabla[direccion];
                        clon.cant++;
                    } else {
                        nodoClon.setEnlace(new NodoHashMapeoM(aux.getDominio(), aux.getRango(), null));
                        nodoClon = nodoClon.getEnlace();
                        clon.cant++;
                    }
                    aux = aux.getEnlace();
                }
                direccion++;
            }
        }
        return clon;
    }

    //Observadores
    @Override
    public String toString() {
        String txt = "Mapa Vacio";
        int direccion = 0;
        int cont = 0;
        NodoHashMapeoM aux;
        if (this.cant > 0) {
            txt = "";
            while (cont < this.cant) {
                aux = this.tabla[direccion];
                if (aux != null) {
                    txt += "\nTabla[" + direccion + "]:";
                }
                while (aux != null) {
                    txt += "\nDominio: " + aux.getDominio().toString() + "\nRango: ";
                    txt += hashMapToString(aux.getRango());
                    cont++;
                    aux = aux.getEnlace();
                    if (aux == null) {
                        txt += "\n------------";
                    }
                }
                direccion++;
            }
        }
        return txt;
    }

    private static String hashMapToString(HashMap<?, ?> map) {
        String txt = "[Rango Vacio]";
        if (!map.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for (HashMap.Entry<?, ?> entry : map.entrySet()) {
                sb.append(entry.getValue().toString());
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2); // Elimina la última coma y el espacio
            sb.append(" ]");
            txt = sb.toString();
        }
        return txt;
    }

    public boolean contieneDominio(Object dominio) {
        NodoHashMapeoM buscador = this.tabla[funcionHash(dominio) % TAMANIO];
        while (buscador != null && !buscador.getDominio().equals(dominio)) {
            buscador = buscador.getEnlace();
        }
        return buscador != null;
    }

    //auxiliares
    private int funcionHash(Object dominio) {
        // Tomamos el dominio como un tipo de dato primitivo para poder aplicar la función.
        return Math.abs(dominio.toString().hashCode());
    }
}
