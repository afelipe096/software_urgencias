package model;
import java.util.Scanner;
public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String telefono;
    protected String direccion;
    protected String correo;
    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor base para inicializar los datos de la persona.
     */
    public Persona(int id, String nombre, String telefono, String direccion, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    // Métodos getter y setter para cada atributo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Método abstracto que obliga a las subclases a mostrar la información de la persona.
     */
    public abstract void mostrarInfo();

    // Métodos estáticos para solicitar datos al usuario antes de crear el objeto

    /**
     * Solicita el ID por consola y valida que sea un número entero.
     */
    public static int solicitarId() {
        System.out.print("Ingrese el ID: ");
        while (!SCANNER.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            SCANNER.next(); // Descartar entrada inválida.
        }
        int id = SCANNER.nextInt();
        SCANNER.nextLine(); // Consumir el salto de línea pendiente.
        return id;
    }

    /**
     * Solicita el nombre por consola.
     */
    public static String solicitarNombre() {
        System.out.print("Ingrese el nombre: ");
        return SCANNER.nextLine();
    }

    /**
     * Solicita el teléfono por consola.
     */
    public static String solicitarTelefono() {
        System.out.print("Ingrese el teléfono: ");
        return SCANNER.nextLine();
    }

    /**
     * Solicita la dirección por consola.
     */
    public static String solicitarDireccion() {
        System.out.print("Ingrese la dirección: ");
        return SCANNER.nextLine();
    }

    /**
     * Solicita el correo electrónico por consola.
     */
    public static String solicitarCorreo() {
        System.out.print("Ingrese el correo: ");
        return SCANNER.nextLine();
    }
}
