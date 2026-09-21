
package lineales.dinamicas;

public class Pila {
     private Nodo tope;
    
    public Pila() {
        this.tope = null;
    }
    
    public boolean apilar(Object nuevoElemento) {
        //Apila un elemento nuevo.
        this.tope = new Nodo(nuevoElemento, this.tope);
        return true;
    }
    
    public boolean desapilar() {
        //Desapila el elemento tope de la pila.
        boolean exito = this.tope != null;
        if (exito) {
            this.tope = this.tope.getEnlace();
        }
        return exito;
    }
    
    public Object obtenerTope() {
        //Devuelve el elemento tope de la pila (nulo en caso de que se encuentre vacía).
        Object tope = null;
        if (this.tope != null) {
            tope = this.tope.getElemento();
        }
        return tope;
    }
    
    public boolean esVacia() {
        //Retorna true si la pila se encuentra vacía.
        return this.tope == null;
    }

    public void vaciar() {
        //Vacía la pila completa.
        while (this.tope != null) {
            this.tope = this.tope.getEnlace();
        }
    }

    public Pila clone() {
        Pila clon = new Pila();//Pila donde se guardaran todos los elementos de la pila utilizada
        if (this.tope != null) {//si la pila no esta vacia hacer
            Nodo auxiliar = this.tope;//nodo auxiliar que replica el tope de la pila original y trabajar sobre ella
            Nodo nodoClon = new Nodo(auxiliar.getElemento(), clon.tope);//nodo para asignar al tope;
            clon.tope = nodoClon;//al tope se le asigna el nodoClon para luego trabajar solo con noodoClon

            while (auxiliar.getEnlace() != null) {
                auxiliar = auxiliar.getEnlace();//se desapila un nodo
                Nodo nuevoNodo = new Nodo(auxiliar.getElemento(), null);  //nodo auxiliar para 
                nodoClon.setEnlace(nuevoNodo);//dar su enlace al enlace del tope de la pila clonada
                nodoClon = nuevoNodo;//y asignarlo como nuevo tope
            }
        }
        return clon;
    }

    @Override
    public String toString() {
        //Método que devuelve los elementos de una pila.
        String mensaje = "";
        if (this.tope == null) {
            mensaje = "La pila esta vacia";
        } else {
            Nodo auxiliar = this.tope;
            while (auxiliar != null) {
                mensaje = auxiliar.getElemento().toString() + mensaje;
                auxiliar = auxiliar.getEnlace();
                if (auxiliar != null) {
                    mensaje = "," + mensaje;
                }
            }

            mensaje = "[" + mensaje + "]";

        }
        return mensaje;
    }
}


