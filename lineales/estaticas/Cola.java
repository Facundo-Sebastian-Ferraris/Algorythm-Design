package lineales.estaticas;

public class Cola {

    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    public Cola() {
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean esVacia() {
        return frente == fin;
    }

    public boolean sacar() {
        boolean exito = true;
        if (this.frente == this.fin) {
            exito = false;
        } else {
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % this.TAMANIO;
        }
        return exito;
    }

    public boolean poner(Object newElem) {
        boolean exito = false;
// siempre que quede una posiciÃ³n libre. 
        if ((this.fin + 1 )%this.TAMANIO!= this.frente) {
            this.arreglo[this.fin] = newElem;
            this.fin = (this.fin + 1) % this.TAMANIO;
            exito = true;
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object elem = null;
        if (this.fin != this.frente) {
            elem = this.arreglo[this.frente];
        }
        return elem;
    }

    public void vaciar() {
       this.frente=this.fin;
    }

    public boolean estaLlena() {
        return this.fin + 1 == this.frente;
    }

    public Cola clone() {
        Cola copia = new Cola();
        for (int actual = 0; actual < this.TAMANIO; actual++) {
            copia.arreglo[actual] = this.arreglo[actual];
        }
        copia.frente=this.frente;
        copia.fin=this.fin;
        return copia;
    }

    @Override
    public String toString() {

        String arregloElem = "";
        int auxFrente=this.frente;
        while(auxFrente!=this.fin){
            arregloElem = arregloElem + this.arreglo[auxFrente] + ",";
            auxFrente=(auxFrente+1)%this.TAMANIO;
        }
        if(arregloElem.length()>1){
            arregloElem=arregloElem.substring(0, arregloElem.length()-1);
        }
       
        return ("[" + arregloElem +"]");
    }

}