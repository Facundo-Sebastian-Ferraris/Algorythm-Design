import TP1.MonticuloBinomial.HeapBinomial;

public class testHeapBinomial {
    public static void main(String[] args) {
        HeapBinomial prueba = new HeapBinomial();
        prueba.insertar(1);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(2);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(4);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(3);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(8);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(6);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(10);
        System.out.println("\n\n\n"+prueba.toString());
        prueba.insertar(-1);
        System.out.println("\n\n\n"+prueba.toString());
    }
}
