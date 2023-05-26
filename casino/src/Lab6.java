import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Lab6 extends JFrame implements ActionListener {

    private JButton btnDados;
    private JButton btnTragamonedas;
    private JButton btnSalir;
    private Timer timerDados;
    private Timer timerTragamonedas;
    private ImageIcon[] simbolosTragamonedas;
    private ImageIcon[] simbolosDados;
    private JLabel lblCarrete1;
    private JLabel lblCarrete2;
    private JLabel lblCarrete3;
    private boolean tragamonedasJugado;

    public Lab6() {
    
        setTitle("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

   
        btnDados = new JButton("Juego de Dados");
        btnTragamonedas = new JButton("Juego de Tragamonedas");
        btnSalir = new JButton("Salir");

     
        btnDados.addActionListener(this);
        btnTragamonedas.addActionListener(this);
        btnSalir.addActionListener(this);

   
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnDados);
        panelBotones.add(btnTragamonedas);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.NORTH);

        // Cargar las imágenes de los símbolos para el juego de tragamonedas
        simbolosTragamonedas = new ImageIcon[]{
                new ImageIcon("Cereza.png"),
                new ImageIcon("naranja.png"),
                new ImageIcon("uva.png"),
                new ImageIcon("Campana.png"),
                new ImageIcon("7.png")
        };

        // Cargar las imágenes de los símbolos para el juego de dados
        simbolosDados = new ImageIcon[]{
                new ImageIcon("1.png"),
                new ImageIcon("2.png"),
                new ImageIcon("3.png"),
                new ImageIcon("4.png"),
                new ImageIcon("5.png"),
                new ImageIcon("6.png")
        };

        // Inicializar los labels de los carretes
        lblCarrete1 = new JLabel();
        lblCarrete2 = new JLabel();
        lblCarrete3 = new JLabel();

        // Agregar los labels al panel central
        JPanel panelCarretes = new JPanel();
        panelCarretes.add(lblCarrete1);
        panelCarretes.add(lblCarrete2);
        panelCarretes.add(lblCarrete3);
        add(panelCarretes, BorderLayout.CENTER);

        // Configurar el temporizador para el juego de tragamonedas
        timerTragamonedas = new Timer(100, new ActionListener() {
            private int tiempoTranscurrido = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoTranscurrido += 100;

                if (tiempoTranscurrido >= 5000) {
                    timerTragamonedas.stop();
                    detenerJuegoTragamonedas();
                } else {
                    cambiarImagenesCarretes(simbolosTragamonedas, timerTragamonedas);
                }
            }
        });

        // Configurar el temporizador para el juego de dados
        timerDados = new Timer(100, new ActionListener() {
            private int tiempoTranscurrido = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoTranscurrido += 100;

                if (tiempoTranscurrido >= 5000) {
                    timerDados.stop();
                    mostrarResultadoDados();
                } else {
                    cambiarImagenesCarretes(simbolosDados, timerDados);
                }
            }
        });

        tragamonedasJugado = false;
    }

    // Método que se ejecuta cuando se hace clic en un botón
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDados) {
            if (!timerDados.isRunning()) {
                iniciarJuegoDados();
            }
        } else if (e.getSource() == btnTragamonedas) {
            if (!timerTragamonedas.isRunning()) {
                iniciarJuegoTragamonedas();
            }
        } else if (e.getSource() == btnSalir) {
            // Salir de la aplicación
            dispose();
        }
    }

    // Método que cambia las imágenes en los carretes
    private void cambiarImagenesCarretes(ImageIcon[] simbolos, Timer timer) {
        Random random = new Random();

        // Generar índices aleatorios para las imágenes de los carretes
        int indice1 = random.nextInt(simbolos.length);
        int indice2 = random.nextInt(simbolos.length);
        int indice3 = random.nextInt(simbolos.length);

        // Establecer las imágenes en los labels de los carretes
        lblCarrete1.setIcon(simbolos[indice1]);
        lblCarrete2.setIcon(simbolos[indice2]);
        lblCarrete3.setIcon(simbolos[indice3]);

        // Detener el temporizador si es proporcionado y se ha alcanzado el tiempo límite
        if (timer != null && timer.isRunning() && timer.getDelay() >= 5000) {
            timer.stop();
        }
    }

    // Método para iniciar el juego de dados
    private void iniciarJuegoDados() {
        reiniciarCarretes();
        timerDados.start();
    }

    // Método para mostrar el resultado del juego de dados
    private void mostrarResultadoDados() {
        int resultado1 = obtenerResultadoDados(lblCarrete1.getIcon());
        int resultado2 = obtenerResultadoDados(lblCarrete2.getIcon());
        int resultado3 = obtenerResultadoDados(lblCarrete3.getIcon());

        int suma = resultado1 + resultado2 + resultado3;

        JOptionPane.showMessageDialog(this, "Resultado de los dados: " + suma);

        reiniciarCarretes();
    }

    // Método para obtener el resultado del dado basado en la imagen
    private int obtenerResultadoDados(Icon imagen) {
        for (int i = 0; i < simbolosDados.length; i++) {
            if (imagen == simbolosDados[i]) {
                return i + 1;
            }
        }
        return 0; // Valor por defecto en caso de que la imagen no coincida con ninguna
    }

    // Método para iniciar el juego de tragamonedas
    private void iniciarJuegoTragamonedas() {
        reiniciarCarretes();
        timerTragamonedas.start();
    }

    // Método para detener el juego de tragamonedas y determinar si el jugador ganó o no
    private void detenerJuegoTragamonedas() {
        timerTragamonedas.stop();

        ImageIcon imagen1 = (ImageIcon) lblCarrete1.getIcon();
        ImageIcon imagen2 = (ImageIcon) lblCarrete2.getIcon();
        ImageIcon imagen3 = (ImageIcon) lblCarrete3.getIcon();

        if (imagen1 == imagen2 && imagen2 == imagen3) {
            JOptionPane.showMessageDialog(this, "¡Felicidades! ¡Has ganado!");
        } else {
            JOptionPane.showMessageDialog(this, "Lo siento, no has ganado. Intenta de nuevo.");
        }

        reiniciarCarretes();
    }

    // Método para reiniciar los labels de los carretes
    private void reiniciarCarretes() {
        lblCarrete1.setIcon(null);
        lblCarrete2.setIcon(null);
        lblCarrete3.setIcon(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Lab6 juego = new Lab6();
                juego.setSize(400, 300);
                juego.setVisible(true);
            }
        });
    }
}
