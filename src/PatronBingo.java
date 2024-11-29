import java.util.Set;

public interface PatronBingo {
    // Devuelve las posiciones del patrón
    int[][] obtenerPosiciones();

    // Verifica si el patrón está cumplido en la carta
    boolean verificar(int[][] carta, Set<Integer> bolasExtraidas);

    String obtenerNombre();
}