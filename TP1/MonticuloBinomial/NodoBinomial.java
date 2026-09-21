package TP1.MonticuloBinomial;

/**
 *
 * @author San Sebastian
 */
public class NodoBinomial {

    private int orden;
    private Object elem;
    private NodoBinomial hijoIzquierdo;
    private NodoBinomial hermanoDerecho;

    public NodoBinomial(Object elemento, NodoBinomial HI, NodoBinomial HerD) {
        this.orden = 0;
        this.elem = elemento;
        this.hijoIzquierdo = HI;
        this.hermanoDerecho = HerD;
    }

    public int getOrden(){
        return  orden;
    }

    public Object getElemento() {
        return this.elem;
    }

    public NodoBinomial getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public NodoBinomial getHermanoDerecho() {
        return this.hermanoDerecho;
    }

    public void setElemento(Object elem) {
        this.elem = elem;
    }

    private void setHijoIzquierdo(NodoBinomial HI) {
        this.hijoIzquierdo = HI;
    }

    public void agregarHijo(NodoBinomial HI){
        HI.setHermanoDerecho(this.hijoIzquierdo);
        this.setHijoIzquierdo(HI);
        orden++;
    }

    public void setHermanoDerecho(NodoBinomial HerD) {
        this.hermanoDerecho = HerD;
    }


}
