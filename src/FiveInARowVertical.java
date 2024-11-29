import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FiveInARowVertical extends PatronesBingo implements PatronBingo {
    private final int columna;

    public FiveInARowVertical(int columna) {
        this.columna = columna; // Especifica la columna en la que se busca el patrón
    }

    @Override
    public int[][] obtenerPosiciones() {
        // Llama al método heredado de la superclase PatronesBingo
        return lineaVertical(columna);  // Usamos el método lineaVertical() de la clase base
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        for (int i = 0; i < 5; i++) {
            int numero = carta[i][columna];
            if (numero != -1 && !bolasExtraidas.contains(numero)) {
                return false; // Si alguna celda no cumple, no hay patrón
            }
        }
        return true;
    }

    // Metodo para registrar todos los patrones de este tipo
    public static List<PatronBingo> crearTodos() {
        List<PatronBingo> patrones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            patrones.add(new FiveInARowVertical(i)); // Crear instancias de FiveInARowVertical
        }
        return patrones;
    }

    @Override
    public String obtenerNombre() {
        return "Línea Vertical " + (columna + 1); // Nombre descriptivo
    }
}