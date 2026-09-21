package utils;
public class ANSI {

    // Reiniciar estilos
    public static final String RESET = "\u001B[0m";

    // Colores del Arcoíris (Texto / Foreground)
    public static final String RED     = "\u001B[31m";
    public static final String ORANGE  = "\u001B[38;2;255;127;0m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String GREEN   = "\u001B[32m";
    public static final String CYAN    = "\u001B[36m";
    public static final String BLUE    = "\u001B[34m";
    public static final String PURPLE  = "\u001B[35m";

    // Colores del Arcoíris (Fondo / Background)
    public static final String BG_RED    = "\u001B[41m";
    public static final String BG_ORANGE = "\u001B[48;2;255;127;0m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_GREEN  = "\u001B[42m";
    public static final String BG_CYAN   = "\u001B[46m";
    public static final String BG_BLUE   = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";

    // Método para generar color de texto RGB personalizado (TrueColor 24-bit)
    public static String rgb(int r, int g, int b) {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }

    // Método para generar color de fondo RGB personalizado
    public static String bgRgb(int r, int g, int b) {
        return String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
    }

    // Método especial que va alternando las letras entre los colores del arcoíris
    public static String rainbow(String text) {
        String[] colors = { RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE };
        StringBuilder result = new StringBuilder();
        int colorIndex = 0;

        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                result.append(ch);
            } else {
                result.append(colors[colorIndex % colors.length]).append(ch);
                colorIndex++;
            }
        }
        result.append(RESET);
        return result.toString();
    }
}