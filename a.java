import static utils.ANSI.*;

public class a {
    public static void main(String[] args) {

        // Paleta de colores en fondo (Background)
        String __ = RESET + "  ";                          // Fondo transparente / vacío
        String KK = bgRgb(0, 0, 0) + "  ";                 // Negro (Borde / Ojos)
        String RR = BG_RED + "  ";                         // Rojo (Sombrero)
        String WW = bgRgb(255, 255, 255) + "  ";           // Blanco (Puntos)
        String SS = bgRgb(255, 220, 170) + "  ";           // Piel (Cara)

        // Dibujo Pixel Art línea por línea
        System.out.println("\n" + rainbow("=== PIXEL ART DE PRUEBA ===") + "\n");

        System.out.println(__ + __ + __ + KK + KK + KK + KK + KK + KK + __ + __ + __);
        System.out.println(__ + __ + KK + RR + RR + RR + RR + RR + RR + KK + __ + __);
        System.out.println(__ + KK + RR + WW + WW + RR + RR + WW + WW + RR + KK + __);
        System.out.println(__ + KK + RR + WW + WW + RR + RR + WW + WW + RR + KK + __);
        System.out.println(KK + RR + RR + RR + RR + WW + WW + RR + RR + RR + RR + KK + __);
        System.out.println(KK + RR + RR + RR + RR + WW + WW + RR + RR + RR + RR + KK + __);
        System.out.println(KK + KK + KK + SS + SS + SS + SS + SS + SS + KK + KK + KK + __);
        System.out.println(__ + __ + KK + SS + KK + SS + SS + KK + SS + KK + __ + __);
        System.out.println(__ + __ + KK + SS + KK + SS + SS + KK + SS + KK + __ + __);
        System.out.println(__ + __ + KK + SS + SS + SS + SS + SS + SS + KK + __ + __);
        System.out.println(__ + __ + __ + KK + KK + KK + KK + KK + KK + __ + __ + __);

        System.out.println(RESET + "\n¡Pixel Art renderizado correctamente!");
    }
}