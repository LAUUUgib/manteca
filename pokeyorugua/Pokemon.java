public class Pokemon {
    private String nombre;
    private int vidaMaxima;
    private int vidaActual;
    private int ataque;
    private int defensa;
    private int velocidad;
    private Movimiento[] movimientos;

    public Pokemon(String nombre, int vidaMaxima, int ataque, int defensa, int velocidad) {
        this(nombre, vidaMaxima, ataque, defensa, velocidad,
                new Movimiento("Placaje", 100, 15),
                new Movimiento("Golpe fuerte", 130, 5));
    }

    public Pokemon(String nombre, int vidaMaxima, int ataque, int defensa, int velocidad, Movimiento... movimientos) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.movimientos = movimientos.clone();
    }

    public String getNombre() {
        return nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public Movimiento[] getMovimientos() {
        return movimientos.clone();
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
    }

    public boolean estaDerrotado() {
        return vidaActual <= 0;
    }

    public void mostrarEstado() {
        System.out.println(nombre + " - Vida: " + vidaActual + "/" + vidaMaxima);
    }

    public Pokemon clonar() {
        Movimiento[] movimientosClonados = new Movimiento[movimientos.length];
        for (int i = 0; i < movimientos.length; i++) {
            movimientosClonados[i] = movimientos[i].clonar();
        }
        return new Pokemon(nombre, vidaMaxima, ataque, defensa, velocidad, movimientosClonados);
    }
}