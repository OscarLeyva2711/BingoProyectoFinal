import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SixPackRotado implements PatronBingo {
    private final int inicioFila;
    private final int inicioColumna;

    // Constructor para inicializar el inicio de fila y columna del patrón rotado
    public SixPackRotado(int inicioFila, int inicioColumna) {
        this.inicioFila = inicioFila;
        this.inicioColumna = inicioColumna;
    }

    @Override
    public int[][] obtenerPosiciones() {
        // Inicialización del patrón 6-Pack girado (3 filas x 2 columnas)
        int[][] patron = new int[6][2];
        int index = 0;
        for (int i = inicioFila; i < inicioFila + 3; i++) { // 3 filas
            for (int j = inicioColumna; j < inicioColumna + 2; j++) { // 2 columnas
                patron[index][0] = i;
                patron[index][1] = j;
                index++;
            }
        }
        return patron;
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        // Verifica si el patrón rotado está completo en la carta
        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioColumna; j < inicioColumna + 2; j++) {
                int numero = carta[i][j];
                if (numero != -1 && !bolasExtraidas.contains(numero)) {
                    return false; // Si alguna celda no cumple, no hay patrón
                }
            }
        }
        return true;
    }

    // Metodo para registrar los 12 patrones válidos de 6-Pack rotado
    public static List<PatronBingo> crearTodos() {
        List<PatronBingo> patrones = new ArrayList<>();

        // Generar 6-Pack rotados válidos dentro de un tablero 5x5
        for (int fila = 0; fila <= 2; fila++) { // Filas donde el bloque vertical cabe (0 a 2)
            for (int columna = 0; columna <= 3; columna++) { // Columnas donde el bloque vertical cabe (0 a 3)
                patrones.add(new SixPackRotado(fila, columna));
            }
        }

        return patrones; // Regresar solo los patrones válidos
    }

    @Override
    public String obtenerNombre() {
        return "6-Pack Rotado " + (inicioFila * 4 + inicioColumna + 1); // Nombre único para cada patrón 6-Pack rotado
    }
}