import java.util.Set;

public class DiagonalInversa extends PatronesBingo implements PatronBingo {

    @Override
    public int[][] obtenerPosiciones() {
        // Llama al metodo heredado de la superclase PatronesBingo
        return diagonalInvertida();  // Usamos el metodo diagonalInvertida() de la clase base
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        for (int i = 0; i < 5; i++) {
            int numero = carta[i][4 - i];  // Accede a la diagonal invertida
            if (numero != -1 && !bolasExtraidas.contains(numero)) {
                return false; // Si alguna celda no está marcada, no se cumple el patrón
            }
        }
        return true;
    }

    // Metodo estático para crear una instancia de DiagonalInversa
    public static PatronBingo crear() {
        return new DiagonalInversa();
    }

    @Override
    public String obtenerNombre() {
        return "Diagonal Inversa";  // Nombre descriptivo
    }
}
