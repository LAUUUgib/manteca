import java.util.Random;

/**
 * Movimiento
 */
public class Movimiento {
    private static final int PROBABILIDAD_FALLO = 8;

    private String nombre;
    private int potencia;
    private int usosMaximos;
    private int usosActuales;

    public Movimiento(String nombre, int potencia, int usosMaximos) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.usosMaximos = usosMaximos;
        this.usosActuales = usosMaximos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPotencia() {
        return potencia;
    }

    public int getUsosActuales() {
        return usosActuales;
    }

    public Movimiento clonar() {
        Movimiento clon = new Movimiento(nombre, potencia, usosMaximos);
        clon.usosActuales = usosActuales;
        return clon;
    }

    public int calcularDanio(int ataque, int defensa) {
        int danioBase = ataque * potencia / 100 - defensa / 2;
        return Math.max(8, danioBase / 4);
    }

    public boolean acierta(Random random) {
        return random.nextInt(100) >= PROBABILIDAD_FALLO;
    }

    public boolean usar() {
        if (usosActuales > 0) {
            usosActuales--;
            return true;
        }
        return false;
    }
}