import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FiveInARowHorizontal extends PatronesBingo implements PatronBingo {
    private final int fila;  // Aquí especificamos la fila donde se desea el patrón

    public FiveInARowHorizontal(int fila) {
        this.fila = fila;  // Especifica la fila en la que se busca el patrón
    }

    @Override
    public int[][] obtenerPosiciones() {
        // Llama al método heredado de la superclase PatronesBingo
        return lineaHorizontal(fila);  // Usamos el método lineaHorizontal() de la clase base
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        for (int i = 0; i < 5; i++) {
            int numero = carta[fila][i];  // Accede a la fila fija, pero columnas varían
            if (numero != -1 && !bolasExtraidas.contains(numero)) {
                return false; // Si alguna celda no cumple, no hay patrón
            }
        }
        return true;
    }

    // Método para registrar todos los patrones de este tipo
    public static List<PatronBingo> crearTodos() {
        List<PatronBingo> patrones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            patrones.add(new FiveInARowHorizontal(i)); // Crear instancias de FiveInARowHorizontal
        }
        return patrones;
    }

    @Override
    public String obtenerNombre() {
        return "Línea Horizontal " + (fila + 1); // Nombre descriptivo
    }
}
