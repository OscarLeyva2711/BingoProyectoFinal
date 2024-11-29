public class PatronesBingo {

    public static int[][] lineaVertical(int columna) {
        int[][] patron = new int[5][2];
        for (int i = 0; i < 5; i++) {
            patron[i][0] = i;
            patron[i][1] = columna;
        }
        return patron;
    }
    public static int[][] lineaHorizontal(int fila) {
        int[][] patron = new int[5][2];
        for (int i = 0; i < 5; i++) {
            patron[i][0] = fila;  // Misma fila
            patron[i][1] = i;     // Columnas 0 a 4
        }
        return patron;
    }
    public static int [][] diagonal(){
            int[][] posiciones = new int[5][2];
            for (int i = 0; i < 5; i++) {
                posiciones[i][0] = i; // Fila
                posiciones[i][1] = i; // Columna
            }
            return posiciones;
        }
        public static int [][] diagonalInvertida(){
            int[][] posiciones = new int[5][2];
            for (int i = 0; i < 5; i++) {
                posiciones[i][0] = i; // Fila
                posiciones[i][1] = 4 - i; // Columna (inversa)
            }
            return posiciones;
        }

    }


