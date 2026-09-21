package lineales.dinamicas;

public class Lista {

    private Nodo cabecera;
    private int longi;

    public Lista() {
        this.cabecera = null;
        this.longi = 0;
    }

    public boolean insertar(Object elem, int pos) {
        boolean exito = (pos >= 1 && pos <= longi + 1);
        if (exito) {
            if (pos == 1) {
                this.cabecera = new Nodo(elem, this.cabecera);
                this.longi++;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elem, aux.getEnlace());
                aux.setEnlace(nuevo);
                this.longi++;
            }
        }
        return exito;

    }

    public boolean eliminar(int pos) {
        boolean exito = pos >= 1 && pos <= this.longi;
        Nodo aux = this.cabecera;
        if (exito) {

            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();

                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());

            }
            this.longi--;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem = null;
        int i = 1;
        Nodo aux = this.cabecera;

        if (aux != null && pos <= this.longi && pos > 0) {
            while (i < pos) {

                aux = aux.getEnlace();
                i++;

            }
            elem = aux.getElemento();
        }

        return elem;
    }

    public int localizar(Object elem) {
        Nodo aux = this.cabecera;
        int ubicacion = 1;
        int longitud = this.longi;
        if (aux != null) {
            while (ubicacion <= longitud && !elem.equals(aux.getElemento())) {
                aux = aux.getEnlace();
                ubicacion++;
            }
            if (aux == null) {
                ubicacion = -1;
            }
        } else {
            ubicacion = -1;
        }

        return ubicacion;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        boolean esVacio = this.cabecera == null;
        return esVacio;
    }

    public Lista clone() {
        Lista clon = new Lista();//Pila donde se guardaran todos los elementos de la pila utilizada
        if (this.cabecera != null) {//si la pila no esta vacia hacer
            Nodo auxiliar = this.cabecera;//nodo auxiliar que replica el tope de la pila original y trabajar sobre ella
            Nodo nodoClon = new Nodo(auxiliar.getElemento(), null);//nodo para asignar al tope;
            clon.cabecera = nodoClon;//al tope se le asigna el nodoClon para luego trabajar solo con noodoClon

            while (auxiliar.getEnlace() != null) {
                auxiliar = auxiliar.getEnlace();//se desapila un nodo
                Nodo nuevoNodo = new Nodo(auxiliar.getElemento(), null);  //nodo auxiliar para 
                nodoClon.setEnlace(nuevoNodo);//dar su enlace al enlace del tope de la pila clonada
                nodoClon = nuevoNodo;//y asignarlo como nuevo tope
            }
        }
        clon.longi = this.longi;
        return clon;
    }

    @Override
    public String toString() {
        //Método que devuelve los elementos de una pila.
        String mensaje = "";
        if (this.cabecera == null) {
            mensaje = "La lista esta vacia";
        } else {
            Nodo auxiliar = this.cabecera;
            while (auxiliar != null) {
                mensaje += auxiliar.getElemento().toString();
                auxiliar = auxiliar.getEnlace();
                if (auxiliar != null) {
                    mensaje += ",";
                }
            }

            mensaje = "[" + mensaje + "]";

        }
        return mensaje;
    }

    public int longitud() {
        int contador = 0;
        Nodo aux = this.cabecera;
        if (aux != null) {
            contador++;
            while (aux.getEnlace() != null) {
                contador++;
                aux = aux.getEnlace();

            }
        }

        return contador;
    }

    public boolean verifRepeticiones() {
        //Este método retorna verdadero en caso de hallar repeticiones de elementos en una lista.
        boolean tieneRep = false;
        Nodo nActual = this.cabecera;

        while (nActual != null && !tieneRep) {
            Nodo aux = nActual.getEnlace();
            while (aux != null && !tieneRep) {
                if (nActual.getElemento().equals(aux.getElemento())) {
                    tieneRep = true;
                }
                aux = aux.getEnlace();
            }
            nActual = nActual.getEnlace();
        }
        return tieneRep;
    }
}
