import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class GestionJuego {
    protected Set<Integer> bolasExtraidas; // Números extraídos
    protected PatronBingo patronActual; // Patrón seleccionado por el usuario
    protected List<PatronBingo> patrones; // Lista de todos los patrones disponibles
    protected int[][] cartaUsuario; // Representación de la carta del usuario
    protected int turnosMaximos; // Número máximo de turnos
    protected int turnosRestantes; // Número de turnos restantes

    // Metodo abstracto para cargar los patrones disponibles (cada clase hija puede implementarlo de manera diferente)
    protected abstract List<PatronBingo> cargarPatrones();

    // Metodo abstracto para verificar si el patrón seleccionado se cumple
    protected abstract boolean verificarVictoria();

    // Metodo abstracto para manejar la selección del patrón
    protected abstract void manejarSeleccionDePatron(ActionEvent e);

    // Metodo común para la verificación de la victoria usando el patrón actual
    public boolean verificarPatron() {
        if (patronActual == null) return false;
        return patronActual.verificar(cartaUsuario, bolasExtraidas);
    }

    // Metodo común para extraer una bola única
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

    // Metodo para iniciar el juego (común a todas las clases hijas)
    public void iniciarJuego() {
        // Lógica común para iniciar el juego, como crear la interfaz de usuario (UI)

    }

    // Metodo para gestionar la selección del patrón desde el GUI
    public void iniciarSeleccionDePatron() {
        new SeleccionPatrones(patrones, this::manejarSeleccionDePatron);
    }
}
