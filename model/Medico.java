package model;
import repository.AdministracionPersonal;
public class Medico extends Persona implements Registrable, Actualizable, Eliminable {
    private String especialidad;

    public Medico(int id, String nombre, String telefono, String direccion, String correo, String especialidad) {
        super(id, nombre, telefono, direccion, correo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Método para mostrar la información del médico
    @Override
    public void mostrarInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Teléfono: " + getTelefono());
        System.out.println("Dirección: " + getDireccion());
        System.out.println("Correo: " + getCorreo());
        System.out.println("Tipo: Médico");
        System.out.println("Especialidad: " + especialidad);
    }

    // Método para atender a un paciente
    public void atenderPaciente(Paciente paciente) {
        System.out.println("\n🩺 " + getNombre() + " está atendiendo a " + paciente.getNombre());
        paciente.getHistorial().agregarRegistro("Atendido por " + getNombre() + " (" + especialidad + ")");
        System.out.println("\nHistorial Clínico Actualizado:");
        System.out.println(paciente.getHistorial().obtenerHistorial());
    }
    

    // Método para continuar la consulta de un paciente utilizando el sistema de triage
    public void continuarConsulta(Paciente paciente) {
        Triage triage = new Triage();
        triage.mostrarCatalogo();

        System.out.println("\n🩺 " + getNombre() + " asignando triage al paciente...");
        while (true) {
            System.out.print("Ingrese el número de la enfermedad (0 para finalizar): ");
       
            int num = Persona.SCANNER.nextInt();
            Persona.SCANNER.nextLine(); 
            if (num == 0) break;
            triage.agregarEnfermedad(num);
        }
        triage.evaluarPaciente(paciente);
        paciente.getHistorial().agregarRegistro("Diagnóstico realizado por " + getNombre() + " (" + especialidad + ")");
        System.out.println("\nHistorial Clínico Actualizado:");
        System.out.println(paciente.getHistorial().obtenerHistorial());
    }

    // Método para actualizar la información del médico
    public void actualizarMedico() {
        System.out.println("\n--- Actualizar Médico ---");
        System.out.print("Nombre: ");
        String nombre = Persona.SCANNER.nextLine();
        System.out.print("Teléfono: ");
        String telefono = Persona.SCANNER.nextLine();
        System.out.print("Dirección: ");
        String direccion = Persona.SCANNER.nextLine();
        System.out.print("Correo: ");
        String correo = Persona.SCANNER.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = Persona.SCANNER.nextLine();

        setNombre(nombre);
        setTelefono(telefono);
        setDireccion(direccion);
        setCorreo(correo);
        setEspecialidad(especialidad);

        AdministracionPersonal.actualizarMedico(this);
        System.out.println("Médico actualizado exitosamente.");
    }

    // Método para registrar un nuevo médico, solicitando todos los datos
    public static void registrarMedico() {
        System.out.println("\n--- Registrar Médico ---");
        int id;
        while (true) {
            System.out.print("ID: ");
            while (!Persona.SCANNER.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                Persona.SCANNER.next();
            }
            id = Persona.SCANNER.nextInt();
            Persona.SCANNER.nextLine(); 
            if (AdministracionPersonal.buscarMedicoPorId(id) == null) {
                break;
            } else {
                System.out.println("El ID ya existe. Por favor, ingrese otro ID.");
            }
        }
        System.out.print("Nombre: ");
        String nombre = Persona.SCANNER.nextLine();
        System.out.print("Teléfono: ");
        String telefono = Persona.SCANNER.nextLine();
        System.out.print("Dirección: ");
        String direccion = Persona.SCANNER.nextLine();
        System.out.print("Correo: ");
        String correo = Persona.SCANNER.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = Persona.SCANNER.nextLine();

        Medico medico = new Medico(id, nombre, telefono, direccion, correo, especialidad);
        AdministracionPersonal.registrarMedico(medico);
        System.out.println("Médico registrado exitosamente.");
    }

  
    public static void eliminarMedico() {
        System.out.println("\n--- Eliminar Médico ---");
        System.out.print("ID del Médico: ");
        int id = Persona.SCANNER.nextInt();
        Persona.SCANNER.nextLine(); // Consumir el salto de línea pendiente
        Medico medico = AdministracionPersonal.buscarMedicoPorId(id);
        if (medico != null) {
            AdministracionPersonal.eliminarMedico(medico);
            System.out.println("Médico eliminado exitosamente.");
        } else {
            System.out.println("Médico no encontrado.");
        }
    }

    @Override
    public void registrar() {
        registrarMedico();
    }

    @Override
    public void actualizar() {
        actualizarMedico();
    }

    @Override
    public void eliminar() {
        eliminarMedico();
    }
}
