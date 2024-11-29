import java.util.Set;

public class PatronDiamond implements PatronBingo {
    @Override
    public int[][] obtenerPosiciones() {
        return new int[][]{
                {2, 0}, {1, 1}, {3, 1}, {0, 2}, {4, 2}, {1, 3}, {3, 3}, {2, 4}
        }; // Coordenadas de un diamante
    }

    @Override
    public boolean verificar(int[][] carta, Set<Integer> bolasExtraidas) {
        for (int[] pos : obtenerPosiciones()) {
            int numero = carta[pos[0]][pos[1]];
            if (numero != -1 && !bolasExtraidas.contains(numero)) {
                return false;
            }
        }
        return true;
    }

    public static PatronBingo crear() {
        return new PatronDiamond();
    }
    @Override
    public String obtenerNombre() {
        return "Diamond";
    }
}