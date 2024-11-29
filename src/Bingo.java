import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Bingo extends GestionJuego{
    private int[][] cartaUsuario; // Representación de la carta del usuario
    private Set<Integer> bolasExtraidas; // Números extraídos
    private PatronBingo patronActual; // Patrón seleccionado por el usuario
    private final List<PatronBingo> patrones; // Lista de todos los patrones disponibles
    private JFrame frame;
    private JTable tablaCarta;
    private JLabel ultimaBolaLabel;
    private JTextArea historialBolas;
    private int turnosMaximos; // Número máximo de turnos que el usuario desea jugar
    private int turnosRestantes; // Número de turnos restantes

    public Bingo() {
        this.patrones = cargarPatrones();
        this.bolasExtraidas = new HashSet<>();
        this.cartaUsuario = generarCarta(); // Carta del usuario generada
    }

    private int[][] generarCarta() {
        Random random = new Random();
        int[][] nuevaCarta = new int[5][5];

        for (int col = 0; col < 5; col++) {
            for (int fila = 0; fila < 5; fila++) {
                int valor;
                do {
                    valor = random.nextInt(15) + 1 + (col * 15);
                } while (yaExisteEnColumna(nuevaCarta, fila, col, valor));
                nuevaCarta[fila][col] = valor;
            }
        }

        nuevaCarta[2][2] = -1; // Espacio FREE automáticamente marcado
        return nuevaCarta;
    }

    private boolean yaExisteEnColumna(int[][] carta, int fila, int col, int valor) {
        for (int i = 0; i < fila; i++) {
            if (carta[i][col] == valor) {
                return true;
            }
        }
        return false;
    }
    @Override
    // Cargar los patrones de todos los tipos disponibles
    protected List<PatronBingo> cargarPatrones() {
        List<PatronBingo> patrones = new ArrayList<>();
        patrones.addAll(FiveInARowVertical.crearTodos());
        patrones.add(DiagonalPrincipal.crear());
        patrones.addAll(FiveInARowHorizontal.crearTodos());
        patrones.add(DiagonalInversa.crear());
        patrones.addAll(SixPack.crearTodos());
        patrones.add(PatronDiamond.crear());
        patrones.add(PatronSmallCenterBox.crear());
        return patrones;
    }

    // Metodo para lanzar el GUI de selección de patrón
    public void iniciarSeleccionDePatron() {
        new SeleccionPatrones(patrones, this::manejarSeleccionDePatron);
    }
@Override
    // Manejar la selección del patrón desde el GUI
    protected void manejarSeleccionDePatron(ActionEvent e) {
        if (e.getSource() instanceof PatronBingo) {
            this.patronActual = (PatronBingo) e.getSource();
            System.out.println("Patrón seleccionado: " + patronActual.obtenerPosiciones());

            // Preguntar por el número de turnos
            String input = JOptionPane.showInputDialog(frame, "¿Cuántos turnos quieres jugar? (Entre 8 y 75)", "Número de turnos", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                try {
                    turnosMaximos = Integer.parseInt(input);
                    if (turnosMaximos < 8 || turnosMaximos > 75) {
                        JOptionPane.showMessageDialog(frame, "Por favor, ingresa un número entre 8 y 75.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    turnosRestantes = turnosMaximos;
                    iniciarJuego();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
@Override
    // Metodo para iniciar el juego
public void iniciarJuego() {
        // Crear ventana con la carta y otras interfaces
        frame = new JFrame("Bingo Interactivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel principal para la carta del bingo
        JPanel panelCarta = new JPanel(new BorderLayout());
        tablaCarta = crearTablaCarta();
        panelCarta.add(new JLabel("Tu Carta de Bingo", JLabel.CENTER), BorderLayout.NORTH);
        panelCarta.add(new JScrollPane(tablaCarta), BorderLayout.CENTER);

        // Panel para mostrar la última bola extraída y el historial
        JPanel panelInferior = new JPanel(new BorderLayout());
        ultimaBolaLabel = new JLabel("Última Bola: Ninguna", JLabel.CENTER);
        historialBolas = new JTextArea();
        historialBolas.setEditable(false);
        historialBolas.setFont(new Font("Arial", Font.PLAIN, 14));
        historialBolas.setLineWrap(true);

        panelInferior.add(ultimaBolaLabel, BorderLayout.NORTH);
        panelInferior.add(new JScrollPane(historialBolas), BorderLayout.CENTER);

        // Botón para girar la tómbola
        JButton btnGirar = new JButton("Girar Tómbola");
        btnGirar.addActionListener(this::girarTombola);
        panelInferior.add(btnGirar, BorderLayout.SOUTH);

        // Añadir los paneles a la ventana principal
        frame.add(panelCarta, BorderLayout.CENTER);
        frame.add(panelInferior, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Metodo para girar la tómbola (extraer bola)
    private void girarTombola(ActionEvent e) {
        if (turnosRestantes > 0) {
            int bola = extraerBola();
            System.out.println("Bola extraída: " + bola);
            bolasExtraidas.add(bola);
            ultimaBolaLabel.setText("Última Bola: " + bola);
            historialBolas.append(bola + " ");
            historialBolas.setCaretPosition(historialBolas.getDocument().getLength());
            turnosRestantes--;

            // Actualizar la carta para resaltar los números extraídos
            tablaCarta.repaint();

            if (verificarVictoria()) {
                JOptionPane.showMessageDialog(frame, "¡Felicidades, ganaste con el patrón seleccionado!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Cierra la ventana al ganar
            } else if (turnosRestantes == 0) {
                JOptionPane.showMessageDialog(frame, "Se acabaron los turnos. ¡Perdiste!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Cierra la ventana al perder
            }
        }
    }


@Override
    // Metodo para extraer una bola aleatoria única
    public int extraerBola() {
        Random random = new Random();
        int bola;

        // Generar una bola única
        do {
            bola = random.nextInt(75) + 1; // Genera un número entre 1 y 75
        } while (bolasExtraidas.contains(bola)); // Repetir si ya ha sido extraída

        bolasExtraidas.add(bola); // Registrar la bola como extraída
        return bola; // Retornar la bola única generada
    }
    @Override
    // Verificar si el patrón se cumple con las bolas extraídas
    protected boolean verificarVictoria() {
        if (patronActual == null) return false;
        return patronActual.verificar(cartaUsuario, bolasExtraidas);
    }

    private JTable crearTablaCarta() {
        String[] encabezados = {"B", "I", "N", "G", "O"};
        Object[][] datosTabla = new Object[5][5];

        for (int fila = 0; fila < 5; fila++) {
            for (int col = 0; col < 5; col++) {
                if (fila == 2 && col == 2) {
                    datosTabla[fila][col] = "FREE";
                } else {
                    datosTabla[fila][col] = cartaUsuario[fila][col];
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(datosTabla, encabezados) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición de las celdas
            }
        };

        JTable tabla = new JTable(model);
        tabla.setRowHeight(60);
        tabla.setFont(new Font("Arial", Font.PLAIN, 20));

        // Deshabilitar cualquier interacción del usuario con clics
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // No hacer nada cuando se hace clic
                e.consume();
            }
        });

        // Renderizador personalizado para colorear las celdas según las bolas extraídas y el patrón ganador
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);

                if (value instanceof Integer && bolasExtraidas.contains(value)) {
                    cell.setForeground(Color.RED); // Números extraídos en rojo
                }

                if (patronActual != null && verificarVictoria()) {
                    // Cambiar a azul si la celda es parte del patrón ganador
                    for (int[] posicion : patronActual.obtenerPosiciones()) {
                        if (posicion[0] == row && posicion[1] == column) {
                            cell.setForeground(Color.BLUE); // Números del patrón ganador en azul
                        }
                    }
                }

                if (row == 2 && column == 2) {
                    cell.setBackground(new Color(173, 216, 230)); // FREE en color azul claro
                }
                return cell;
            }
        });
        return tabla;
    }

    public static void main(String[] args) {
        Bingo juego = new Bingo();
        juego.iniciarSeleccionDePatron();
    }
}