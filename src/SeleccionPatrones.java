import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.event.ActionListener;

public class SeleccionPatrones extends JFrame {
    private final List<PatronBingo> patronesDisponibles;
    private final ActionListener listener;

    public SeleccionPatrones(List<PatronBingo> patronesDisponibles, ActionListener listener) {
        this.patronesDisponibles = patronesDisponibles;
        this.listener = listener;

        setTitle("Selecciona un patrón de Bingo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel donde estarán los botones
        JPanel panelPatrones = new JPanel();
        int filas = (int) Math.ceil(patronesDisponibles.size() / 5.0); // Calcula las filas necesarias
        panelPatrones.setLayout(new GridLayout(filas, 5, 10, 10)); // Configuración de rejilla dinámica

        // Cargar los botones con imágenes representativas de los patrones
        for (int i = 0; i < patronesDisponibles.size(); i++) {
            int index = i; // Necesario para usar en lambda
            PatronBingo patron = patronesDisponibles.get(i);

            // Ruta de la imagen asociada al patrón
            String imagePath = "/Resources/patron" + (i + 1) + ".png"; // Asegúrate de que las imágenes sigan este orden
            ImageIcon icon = cargarIcono(imagePath);

            // Crear botón para cada patrón con su imagen representativa
            JButton botonPatron = new JButton(icon);
            botonPatron.setToolTipText(patron.obtenerNombre()); // Muestra el nombre del patrón al pasar el mouse
            botonPatron.addActionListener(e -> seleccionarPatron(index));
            panelPatrones.add(botonPatron);
        }

        // Añadir los componentes al JFrame
        add(new JLabel("Selecciona un patrón para jugar:", JLabel.CENTER), BorderLayout.NORTH);
        add(panelPatrones, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // Centrar la ventana
        setVisible(true);
    }

    // Metodo para cargar imágenes
    private ImageIcon cargarIcono(String ruta) {
        try {
            return new ImageIcon(getClass().getResource(ruta)); // Carga la imagen desde recursos
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen: " + ruta);
            return null; // Devuelve null si la imagen no se encuentra
        }
    }

    // Metodo para manejar la selección del patrón
    private void seleccionarPatron(int index) {
        PatronBingo patronSeleccionado = patronesDisponibles.get(index);
        // Dispara el evento al listener para que se integre en BINGOBINGO
        listener.actionPerformed(new ActionEvent(patronSeleccionado, ActionEvent.ACTION_PERFORMED, "PatronSeleccionado"));
        dispose(); // Cierra la ventana después de seleccionar
    }
}