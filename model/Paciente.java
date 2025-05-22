package model;

import java.util.ArrayList;
import java.util.List;

import repository.DatosPredeterminados;
public class Paciente extends Persona implements Registrable, Actualizable, Eliminable {
    private List<String> sintomas;
    private int nivelTriage;
    private Enfermero enfermeroAsignado;
    private Integer numeroCama; // Puede ser null si el paciente no necesita cama
    private HistorialMedico historial;
    private boolean hospitalizado;
    private String atendidoPor;

    public Paciente(int id, String nombre, String telefono, String direccion, String correo, 
                    String atendidoPor, Enfermero enfermeroAsignado, Integer numeroCama) {
        super(id, nombre, telefono, direccion, correo);
        this.sintomas = new ArrayList<>();
        this.nivelTriage = 3; // Nivel más bajo por defecto
        this.atendidoPor = atendidoPor;
        this.enfermeroAsignado = enfermeroAsignado;
        this.numeroCama = numeroCama;
        this.historial = new HistorialMedico(id, this); // ✅ Se inicializa el historial
        this.hospitalizado = false; // 
    }

    public List<String> getSintomas() {
        return sintomas;
    }
    
    public void agregarSintoma(String sintoma) {
        sintomas.add(sintoma);
    }

    public int getNivelTriage() {
        return nivelTriage;
    }

    public void setNivelTriage(int nivelTriage) {
        this.nivelTriage = nivelTriage;
    }

    public Enfermero getEnfermeroAsignado() {
        return enfermeroAsignado;
    }

    public void asignarEnfermero(Enfermero enfermero) {
        this.enfermeroAsignado = enfermero;
    }

    public Integer getNumeroCama() {
        return numeroCama;
    }

    public void setNumeroCama(Integer numeroCama) {
        this.numeroCama = numeroCama;
    }

    public void asignarCama(int numeroCama) {
        this.numeroCama = numeroCama;
    }

    public void eliminarEnfermero() {
        this.enfermeroAsignado = null;
    }

    public HistorialMedico getHistorial() {
        return historial;
    }

    public boolean isHospitalizado() {
        return hospitalizado;
    }

    public void hospitalizar() {
        this.hospitalizado = true;
    }

    public void darDeAlta() {
        this.hospitalizado = false;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public void setAtendidoPor(String medico) {
        this.atendidoPor = medico;
    }

    // Método para mostrar la información del paciente
    @Override
    public void mostrarInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Teléfono: " + getTelefono());
        System.out.println("Dirección: " + getDireccion());
        System.out.println("Correo: " + getCorreo());
        System.out.println("📌 Tipo: Paciente");
        System.out.println("Síntomas: " + (sintomas.isEmpty() ? "Ninguno registrado" : String.join(", ", sintomas)));
        System.out.println("Nivel de Triage: " + nivelTriage);
        System.out.println("Atendido por: " + (atendidoPor != null ? atendidoPor : "No atendido"));
        System.out.println("Enfermero Asignado: " + (enfermeroAsignado != null ? enfermeroAsignado.getNombre() : "No asignado"));
        System.out.println("Número de Cama: " + (numeroCama != null ? numeroCama : "No requiere"));
        System.out.println("Hospitalizado: " + (hospitalizado ? "Sí" : "No"));
        System.out.println("📝 Historial Médico:");
        System.out.println(historial.obtenerHistorial());
    }

    // Método para actualizar la información del paciente
    public void actualizarPaciente() {
        System.out.println("\n--- Actualizar Paciente ---");
        System.out.print("Nombre: ");
        String nombre = Persona.SCANNER.nextLine();
        System.out.print("Teléfono: ");
        String telefono = Persona.SCANNER.nextLine();
        System.out.print("Dirección: ");
        String direccion = Persona.SCANNER.nextLine();
        System.out.print("Correo: ");
        String correo = Persona.SCANNER.nextLine();
        setNombre(nombre);
        setTelefono(telefono);
        setDireccion(direccion);
        setCorreo(correo);
        DatosPredeterminados.actualizarPaciente(this);
        System.out.println("Paciente actualizado exitosamente.");
    }

    // Método para registrar un nuevo paciente
    public static void registrarPaciente() {
        System.out.println("\n--- Registrar Paciente ---");

        // Obtener la lista de pacientes registrados a través de DatosPredeterminados.
        List<Paciente> pacientesRegistrados = DatosPredeterminados.getPacientesRegistrados();
        if (pacientesRegistrados == null) {
            System.out.println("Error: La lista de pacientes no está inicializada.");
            return;
        }

        int id;
        while (true) {
            System.out.print("ID: ");
            // Validar que la entrada sea un entero
            while (!Persona.SCANNER.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                Persona.SCANNER.next(); 
            }
            id = Persona.SCANNER.nextInt();
            Persona.SCANNER.nextLine(); 

            // Verificar si el ID ya existe en la lista de pacientes registrados
            boolean idExiste = false;
            for (Paciente paciente : pacientesRegistrados) {
                if (paciente.getId() == id) {
                    idExiste = true;
                    break;
                }
            }
            if (!idExiste) {
                break;
            } else {
                System.out.println("El ID ya existe. Por favor, ingrese otro ID.");
            }
        }

        // Solicitar los datos del paciente utilizando los métodos de Persona
        String nombre = Persona.solicitarNombre();
        String telefono = Persona.solicitarTelefono();
        String direccion = Persona.solicitarDireccion();
        String correo = Persona.solicitarCorreo();

        // Crear un nuevo paciente y agregarlo a la lista de pacientes registrados
        Paciente paciente = new Paciente(id, nombre, telefono, direccion, correo, null, null, null);
        pacientesRegistrados.add(paciente);
        System.out.println("\n✅ Paciente registrado exitosamente:");
        paciente.mostrarInfo();
    }

    public void eliminarAsignacionTriage() {
        // Limpiar la lista de síntomas
        this.sintomas.clear();
        // Reestablecer el nivel de triage a 3 o el valor por defecto
        this.nivelTriage = 3;
        // Desasignar el enfermero
        this.enfermeroAsignado = null;
        // Registrar la acción en el historial
        this.historial.agregarRegistro("Se eliminó la asignación de triage.");
    }

    @Override
    public void registrar() {
        registrarPaciente();
    }

    @Override
    public void actualizar() {
        actualizarPaciente();
    }

    @Override
    public void eliminar() {
        // Aquí podrías implementar la lógica para eliminar el paciente de la lista
        System.out.println("Paciente eliminado (implementa lógica según tu sistema).");
    }
}
