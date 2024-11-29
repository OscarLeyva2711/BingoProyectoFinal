import java.util.Set;

public class DiagonalPrincipal extends PatronesBingo implements PatronBingo {

    @Override
    public int[][] obtenerPosiciones() {
        // Llama al metodo heredado de la superclase PatronesBingo
        return diagonal();  // Usamos el metodo diagonal() de la clase base
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        for (int i = 0; i < 5; i++) {
            int numero = carta[i][i];  // Accede a la diagonal (mismo índice para fila y columna)
            if (numero != -1 && !bolasExtraidas.contains(numero)) {
                return false; // Si alguna celda no está marcada, no se cumple el patrón
            }
        }
        return true;
    }

    // Metodo estático para crear una instancia de DiagonalPrincipal
    public static PatronBingo crear() {
        return new DiagonalPrincipal();
    }

    @Override
    public String obtenerNombre() {
        return "Diagonal Principal";  // Nombre descriptivo
    }
}
