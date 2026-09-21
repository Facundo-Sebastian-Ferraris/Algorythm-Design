package lineales.dinamicas;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    public Cola(){
        this.frente=null;
        this.fin=null;  
    }
    
    public boolean poner (Object newElem){
        Nodo nuevo =new Nodo (newElem,null);
        if(this.frente==null){
            this.frente=nuevo;
        }else{
            this.fin.setEnlace(nuevo);
         }
        this.fin=nuevo;
        return true;
    }
    
    public boolean sacar(){
        boolean exito=this.frente!=null;
        
        if(exito){
            this.frente = this.frente.getEnlace();
            if(this.frente==null){
                this.fin=null;
            }
                                 
    }
      return exito;
    }
        
    public Object obtenerFrente(){
        Object elem=null;
    
        if(!this.esVacia()){
        elem=this.frente.getElemento();
                }
        return elem;
    
    }
    
    public boolean esVacia(){
        boolean estaVacia=false;
        if (this.frente==null){
            estaVacia=true;
        }
        return estaVacia;
    }
    
    public void vaciar(){
        this.fin=null;
        this.frente=null;
    }
    
    public Cola clone(){
        //Módulo que copia los elementos de una cola hacia otra.
        Cola colaClon = new Cola();
        Nodo aux2;
        if (this.frente != null) {
            Nodo aux = new Nodo(this.frente.getElemento(), this.frente.getEnlace());
            colaClon.frente = aux;
            while (aux.getEnlace() != null){
                aux2 = new Nodo(aux.getEnlace().getElemento(), aux.getEnlace().getEnlace());
                aux.setEnlace(aux2);
                aux = aux2;
            }
            colaClon.fin = aux;
        }
        return colaClon;
    }
    
     public String toString() {
        //Método que devuelve los elementos de una Cola.
        String mensaje = "";
        if (this.frente== null) {
            mensaje = "La cola esta vacia";
        } else {
            Nodo auxiliar = this.frente;
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
}