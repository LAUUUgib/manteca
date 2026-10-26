import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    private final Pokemon[] pokemones = {
        new Pokemon("Carpini", 95, 75, 90, 45,
                new Movimiento("Chorro de agua", 100, 15),
                new Movimiento("Ola yorugua", 140, 5)),
        new Pokemon("Matechu", 70, 80, 55, 110,
                new Movimiento("Chispa", 105, 15),
                new Movimiento("Descarga", 145, 5)),
        new Pokemon("Horneri", 85, 70, 75, 65,
                new Movimiento("Picotazo", 95, 18),
                new Movimiento("Vuelo rasante", 135, 6)),
        new Pokemon("Ñandunero", 80, 90, 60, 105,
                new Movimiento("Picotazo veloz", 110, 16),
                new Movimiento("Picada", 130, 7)),
        new Pokemon("Lobinon", 75, 70, 65, 95,
                new Movimiento("Mordisco", 100, 15),
                new Movimiento("Aullido feroz", 150, 4)),
        new Pokemon("Muliton", 90, 85, 100, 35,
                new Movimiento("Pisotón", 90, 18),
                new Movimiento("Embestida", 130, 7)),
        new Pokemon("Teronero", 70, 65, 55, 115,
                new Movimiento("Golpe de arena", 105, 16),
                new Movimiento("Terremoto", 145, 4)),
        new Pokemon("Yacared", 85, 85, 80, 40,
                new Movimiento("Cola pesada", 95, 18),
                new Movimiento("Mordida de pantano", 150, 4))
    };

    private final Random random = new Random();

    private JList<String> listaPokemones;
    private DefaultListModel<String> modeloLista;
    private JTextArea log;
    private JPanel panelAtaques;
    private JLabel lblJugador;
    private JLabel lblRival;

    private Pokemon jugadorActual;
    private Pokemon rivalActual;
    private boolean jugadorDefendiendo;

    public Main() {
        setTitle("PokeYorugua");
        setSize(920, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(12, 12));

        JPanel fondo = new JPanel(new BorderLayout(12, 12));
        fondo.setBorder(new EmptyBorder(16, 16, 16, 16));
        fondo.setBackground(new Color(16, 24, 38));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(24, 39, 58));
        panelIzquierdo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(119, 184, 255), 2),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titulo = new JLabel("POKEYORUGUA");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(new Color(120, 220, 255));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subTitulo = new JLabel("Seleccioná tu Pokémon");
        subTitulo.setForeground(Color.WHITE);
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        modeloLista = new DefaultListModel<>();
        for (Pokemon p : pokemones) {
            modeloLista.addElement(p.getNombre());
        }

        listaPokemones = new JList<>(modeloLista);
        listaPokemones.setFixedCellHeight(28);
        listaPokemones.setBackground(new Color(15, 24, 38));
        listaPokemones.setForeground(Color.WHITE);
        listaPokemones.setSelectionBackground(new Color(87, 140, 255));
        listaPokemones.setSelectionForeground(Color.WHITE);
        listaPokemones.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JButton btnJugar = new JButton("Jugar");
        JButton btnVer = new JButton("Ver Pokémon");
        JButton btnSalir = new JButton("Salir");

        estiloBoton(btnJugar, new Color(52, 152, 219));
        estiloBoton(btnVer, new Color(46, 204, 113));
        estiloBoton(btnSalir, new Color(231, 76, 60));

        btnJugar.addActionListener(e -> iniciarBatalla());
        btnVer.addActionListener(e -> mostrarEquipo());
        btnSalir.addActionListener(e -> System.exit(0));

        panelIzquierdo.add(titulo);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(subTitulo);
        panelIzquierdo.add(Box.createVerticalStrut(8));
        panelIzquierdo.add(new JScrollPane(listaPokemones));
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(btnJugar);
        panelIzquierdo.add(Box.createVerticalStrut(8));
        panelIzquierdo.add(btnVer);
        panelIzquierdo.add(Box.createVerticalStrut(8));
        panelIzquierdo.add(btnSalir);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBackground(new Color(17, 27, 41));
        panelDerecho.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(119, 184, 255), 2),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel combateTitulo = new JLabel("COMBATE");
        combateTitulo.setForeground(new Color(255, 214, 102));
        combateTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel estado = new JPanel(new GridLayout(1, 2, 12, 0));
        estado.setOpaque(false);

        lblJugador = new JLabel("Jugador: -");
        lblRival = new JLabel("Rival: -");
        lblJugador.setForeground(new Color(120, 220, 255));
        lblRival.setForeground(new Color(255, 128, 128));
        lblJugador.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRival.setFont(new Font("Segoe UI", Font.BOLD, 16));

        estado.add(lblJugador);
        estado.add(lblRival);

        log = new JTextArea();
        log.setEditable(false);
        log.setBackground(new Color(18, 30, 48));
        log.setForeground(new Color(230, 240, 255));
        log.setFont(new Font("Consolas", Font.PLAIN, 14));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);

        panelAtaques = new JPanel(new GridLayout(0, 3, 10, 10));
        panelAtaques.setOpaque(false);

        panelDerecho.add(combateTitulo, BorderLayout.NORTH);
        panelDerecho.add(estado, BorderLayout.BEFORE_FIRST_LINE);
        panelDerecho.add(new JScrollPane(log), BorderLayout.CENTER);
        panelDerecho.add(panelAtaques, BorderLayout.SOUTH);

        fondo.add(panelIzquierdo, BorderLayout.WEST);
        fondo.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(fondo);

        actualizarLog("Elige tu Pokémon y empieza el combate.");
    }

    private void estiloBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    private void iniciarBatalla() {
        int seleccion = listaPokemones.getSelectedIndex();
        if (seleccion < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná un Pokémon antes de jugar.");
            return;
        }

        jugadorActual = pokemones[seleccion].clonar();
        jugadorDefendiendo = false;

        int rivalIndex;
        do {
            rivalIndex = random.nextInt(pokemones.length);
        } while (rivalIndex == seleccion);

        rivalActual = pokemones[rivalIndex].clonar();

        lblJugador.setText("Jugador: " + jugadorActual.getNombre() + " | Vida: " + jugadorActual.getVidaActual());
        lblRival.setText("Rival: " + rivalActual.getNombre() + " | Vida: " + rivalActual.getVidaActual());

        log.setText("");
        actualizarLog("¡" + jugadorActual.getNombre() + " contra " + rivalActual.getNombre() + "!");
        actualizarLog("Elegí tu ataque:");

        actualizarBotonesAtaque();
    }

    private void actualizarBotonesAtaque() {
        panelAtaques.removeAll();
        Movimiento[] movimientos = jugadorActual.getMovimientos();
        for (int i = 0; i < movimientos.length; i++) {
            Movimiento movimiento = movimientos[i];
            int indice = i;
            JButton boton = new JButton(movimiento.getNombre() + " (PP: " + movimiento.getUsosActuales() + ")");
            estiloBoton(boton, new Color(52, 152, 219));
            boton.setEnabled(movimiento.getUsosActuales() > 0);
            boton.addActionListener(e -> turnoJugador(indice));
            panelAtaques.add(boton);
        }

        JButton btnDefender = new JButton("Defender");
        estiloBoton(btnDefender, new Color(46, 204, 113));
        btnDefender.addActionListener(e -> defenderJugador());
        panelAtaques.add(btnDefender);
        panelAtaques.revalidate();
        panelAtaques.repaint();
    }

    private void turnoJugador(int indiceMovimiento) {
        if (jugadorActual == null || rivalActual == null) {
            return;
        }

        if (jugadorActual.estaDerrotado() || rivalActual.estaDerrotado()) {
            return;
        }

        Movimiento[] movimientos = jugadorActual.getMovimientos();
        if (indiceMovimiento < 0 || indiceMovimiento >= movimientos.length) {
            return;
        }
        Movimiento movimiento = movimientos[indiceMovimiento];
        if (!movimiento.usar()) {
            actualizarLog("¡" + movimiento.getNombre() + " ya no tiene usos!");
            actualizarBotonesAtaque();
            return;
        }

        jugadorDefendiendo = false;
        if (movimiento.acierta(random)) {
            int danio = movimiento.calcularDanio(jugadorActual.getAtaque(), rivalActual.getDefensa());
            actualizarLog("¡" + jugadorActual.getNombre() + " usa " + movimiento.getNombre()
                + "! Daño: " + danio + " | Usos restantes: " + movimiento.getUsosActuales());
            rivalActual.recibirDanio(danio);
        } else {
            actualizarLog("¡" + jugadorActual.getNombre() + " usa " + movimiento.getNombre()
                + ", pero falla! Usos restantes: " + movimiento.getUsosActuales());
        }
        lblRival.setText("Rival: " + rivalActual.getNombre() + " | Vida: " + rivalActual.getVidaActual());

        if (rivalActual.estaDerrotado()) {
            finCombate(true);
            return;
        }

        turnoIA();

        lblJugador.setText("Jugador: " + jugadorActual.getNombre() + " | Vida: " + jugadorActual.getVidaActual());
        lblRival.setText("Rival: " + rivalActual.getNombre() + " | Vida: " + rivalActual.getVidaActual());
        if (!jugadorActual.estaDerrotado()) {
            actualizarBotonesAtaque();
        }
    }

    private void defenderJugador() {
        if (jugadorActual == null || rivalActual == null
                || jugadorActual.estaDerrotado() || rivalActual.estaDerrotado()) {
            return;
        }
        jugadorDefendiendo = true;
        actualizarLog("¡" + jugadorActual.getNombre() + " se defiende!");
        turnoIA();
        lblJugador.setText("Jugador: " + jugadorActual.getNombre() + " | Vida: " + jugadorActual.getVidaActual());
        lblRival.setText("Rival: " + rivalActual.getNombre() + " | Vida: " + rivalActual.getVidaActual());
        if (!jugadorActual.estaDerrotado() && !rivalActual.estaDerrotado()) {
            actualizarBotonesAtaque();
        }
    }

    private void turnoIA() {
        Movimiento[] movimientos = rivalActual.getMovimientos();
        java.util.List<Movimiento> disponibles = new java.util.ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getUsosActuales() > 0) {
                disponibles.add(movimiento);
            }
        }

        if (disponibles.isEmpty() || random.nextInt(3) == 2) {
            actualizarLog("¡" + rivalActual.getNombre() + " se defiende!");
        } else {
            Movimiento movimiento = disponibles.get(random.nextInt(disponibles.size()));
            movimiento.usar();
            if (movimiento.acierta(random)) {
                int danioIA = movimiento.calcularDanio(rivalActual.getAtaque(), jugadorActual.getDefensa());
                if (jugadorDefendiendo) {
                    danioIA /= 2;
                    actualizarLog("¡Tu defensa redujo el daño!");
                }
                actualizarLog("¡" + rivalActual.getNombre() + " usa " + movimiento.getNombre()
                        + "! Daño: " + danioIA);
                jugadorActual.recibirDanio(danioIA);
            } else {
                actualizarLog("¡" + rivalActual.getNombre() + " usa " + movimiento.getNombre()
                        + ", pero falla!");
            }
        }

        jugadorDefendiendo = false;

        if (jugadorActual.estaDerrotado()) {
            finCombate(false);
        }
    }

    private void finCombate(boolean jugadorGana) {
        if (jugadorGana) {
            actualizarLog("¡" + jugadorActual.getNombre() + " gana el combate!");
        } else {
            actualizarLog("¡" + rivalActual.getNombre() + " gana el combate!");
        }

        panelAtaques.removeAll();

        JButton btnMenu = new JButton("Volver al menú");
        estiloBoton(btnMenu, new Color(155, 89, 182));

        btnMenu.addActionListener(e -> {
            jugadorActual = null;
            rivalActual = null;
            jugadorDefendiendo = false;
            lblJugador.setText("Jugador: -");
            lblRival.setText("Rival: -");
            log.setText("Seleccioná un Pokémon para otra batalla.");
            panelAtaques.removeAll();
            panelAtaques.revalidate();
            panelAtaques.repaint();
        });

        panelAtaques.add(btnMenu);
        panelAtaques.revalidate();
        panelAtaques.repaint();
    }

    private void mostrarEquipo() {
        StringBuilder texto = new StringBuilder();
        for (Pokemon p : pokemones) {
            texto.append(p.getNombre())
                 .append(" | Vida: ")
                 .append(p.getVidaActual())
                 .append(" | Ataque: ")
                 .append(p.getAtaque())
                 .append(" | Defensa: ")
                 .append(p.getDefensa())
                 .append("\n");
        }
        log.setText(texto.toString());
    }

    private void actualizarLog(String mensaje) {
        log.append(mensaje + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}