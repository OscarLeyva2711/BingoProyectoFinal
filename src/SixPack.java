import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SixPack implements PatronBingo {
    private final int inicioFila;
    private final int inicioColumna;

    // Constructor para inicializar el inicio de fila y columna del patrón
    public SixPack(int inicioFila, int inicioColumna) {
        this.inicioFila = inicioFila;
        this.inicioColumna = inicioColumna;
    }

    @Override
    public int[][] obtenerPosiciones() {
        // Inicialización del patrón 6-Pack con 6 posiciones
        int[][] patron = new int[6][2];
        int index = 0;
        for (int i = inicioFila; i < inicioFila + 2; i++) { // 2 filas
            for (int j = inicioColumna; j < inicioColumna + 3; j++) { // 3 columnas
                patron[index][0] = i;
                patron[index][1] = j;
                index++;
            }
        }
        return patron;
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        // Verifica si el patrón está completo en la carta
        for (int i = inicioFila; i < inicioFila + 2; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                int numero = carta[i][j];
                if (numero != -1 && !bolasExtraidas.contains(numero)) {
                    return false; // Si alguna celda no cumple, no hay patrón
                }
            }
        }
        return true;
    }

    // Metodo para registrar los 24 patrones válidos de 6-Pack
    public static List<PatronBingo> crearTodos() {
        List<PatronBingo> patrones = new ArrayList<>();

        // Patrones horizontales (12 combinaciones principales)
        for (int fila = 0; fila <= 3; fila++) { // Filas donde el bloque cabe (0 a 3)
            for (int columna = 0; columna <= 2; columna++) { // Columnas donde el bloque cabe (0 a 2)
                patrones.add(new SixPack(fila, columna));
            }
        }

        // Patrones verticales (12 combinaciones rotadas)
        for (int columna = 0; columna <= 3; columna++) { // Columnas donde el bloque vertical cabe (0 a 3)
            for (int fila = 0; fila <= 2; fila++) { // Filas donde el bloque vertical cabe (0 a 2)
                patrones.add(new SixPackRotado(fila, columna));
            }
        }

        return patrones; // Regresar los 24 patrones
    }

    @Override
    public String obtenerNombre() {
        return "6-Pack " + (inicioFila * 3 + inicioColumna + 1); // Nombre único para cada patrón 6-Pack
    }
}