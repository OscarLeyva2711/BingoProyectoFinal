import java.util.Set;

public class PatronSmallCenterBox implements PatronBingo {
    @Override
    public int[][] obtenerPosiciones() {
        int[][] patron = new int[9][2];
        int index = 0;
        for (int i = 1; i <= 3; i++) { // Filas 1, 2, 3
            for (int j = 1; j <= 3; j++) { // Columnas 1, 2, 3
                patron[index][0] = i;
                patron[index][1] = j;
                index++;
            }
        }
        return patron;
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
        return new PatronSmallCenterBox();
    }
    @Override
    public String obtenerNombre() {
        return "Small Center Box";
    }
}