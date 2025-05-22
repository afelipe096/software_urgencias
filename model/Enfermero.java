package model;

import repository.AdministracionPersonal;

public class Enfermero extends Persona<Enfermero> implements Crud {
    private String especialidad;
    private String turno;

    public Enfermero(int id, String nombre, String telefono, String direccion, String correo, String especialidad, String turno) {
        super(id, nombre, telefono, direccion, correo);
        this.especialidad = especialidad;
        this.turno = turno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    // Método para mostrar la información del enfermero
    @Override
    public void mostrarInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Teléfono: " + getTelefono());
        System.out.println("Dirección: " + getDireccion());
        System.out.println("Correo: " + getCorreo());
        System.out.println("Tipo: Enfermero");
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Turno: " + turno);
    }

    // Método para asignar triage a un paciente
    public void asignarTriage(Paciente paciente) {
        // Asigna al enfermero al paciente
        paciente.asignarEnfermero(this);
        
        Triage triage = new Triage();
        triage.mostrarCatalogo();
    
        System.out.println("\nEnfermero " + getNombre() + " asignando triage al paciente...");
    
        while (true) {
            System.out.print("Ingrese el número de la enfermedad (0 para finalizar): ");
            int num = Persona.SCANNER.nextInt();
            Persona.SCANNER.nextLine(); // Consumir salto de línea
            if (num == 0) break;
            
            // Agregar la enfermedad al triage
            triage.agregarEnfermedad(num);
            // Agregar el síntoma al paciente con el formato esperado, por ejemplo:
            String sintoma = triage.getEnfermedad(num);
            if (sintoma != null) {
                // Formatear el síntoma para incluir el código, por ejemplo:
                paciente.agregarSintoma(sintoma + " (Código: " + num + ")");
            }
        }
        
        // Evaluar el paciente según el triage (actualiza nivel y muestra mensajes)
        triage.evaluarPaciente(paciente);
        
        // Actualiza el nivel de triage del paciente
        paciente.setNivelTriage(triage.getNivelUrgencia());
        
        // Agrega registro al historial
        paciente.getHistorial().agregarRegistro("Triage asignado por " + getNombre() + " (" + especialidad + "), Nivel: " + triage.getNivelUrgencia());
    }

    // Método para asignar triage a un paciente usando Swing
    public void asignarTriage(Paciente paciente, java.awt.Component parent) {
        paciente.asignarEnfermero(this);

        Triage triage = new Triage();
        java.util.List<Integer> claves = new java.util.ArrayList<>(triage.getCatalogoEnfermedades().keySet());
        java.util.Collections.sort(claves);

        java.util.List<Integer> seleccionadas = new java.util.ArrayList<>();
        boolean seguir = true;
        while (seguir) {
            String[] opciones = claves.stream()
                .filter(num -> !seleccionadas.contains(num))
                .map(num -> num + ". " + triage.getCatalogoEnfermedades().get(num))
                .toArray(String[]::new);

            if (opciones.length == 0) break;

            // Usa el input personalizado
            String seleccion = GUI.InputsPersonalizados.mostrarSeleccionListaPersonalizada(
                parent,
                "Catálogo de Enfermedades",
                "Seleccione una enfermedad (o Cancelar para terminar):",
                opciones
            );
            if (seleccion == null) {
                seguir = false;
            } else {
                int num = Integer.parseInt(seleccion.split("\\.")[0].trim());
                if (!seleccionadas.contains(num)) {
                    seleccionadas.add(num);
                    triage.agregarEnfermedad(num);
                    String sintoma = triage.getEnfermedad(num);
                    if (sintoma != null) {
                        paciente.agregarSintoma(sintoma + " (Código: " + num + ")");
                    }
                }
            }
        }

        if (seleccionadas.isEmpty()) {
            GUI.InputsPersonalizados.mostrarMensajePersonalizado(parent, "No se seleccionó ninguna enfermedad.");
            return;
        }

        triage.evaluarPaciente(paciente);
        paciente.setNivelTriage(triage.getNivelUrgencia());
        if (paciente.getHistorial() != null) {
            paciente.getHistorial().agregarRegistro("Triage asignado por " + getNombre() + " (" + especialidad + "), Nivel: " + triage.getNivelUrgencia());
        }
        GUI.InputsPersonalizados.mostrarMensajePersonalizado(parent, "Triage asignado exitosamente. Nivel: " + triage.getNivelUrgencia());
    }

    // Método para actualizar la información del enfermero
    public void actualizarEnfermero() {
        System.out.println("\n--- Actualizar Enfermero ---");
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
        System.out.print("Turno: ");
        String turno = Persona.SCANNER.nextLine();

        setNombre(nombre);
        setTelefono(telefono);
        setDireccion(direccion);
        setCorreo(correo);
        setEspecialidad(especialidad);
        setTurno(turno);
        AdministracionPersonal.actualizarEnfermero(this);
        System.out.println("Enfermero actualizado exitosamente.");
    }

    // Método para registrar un nuevo enfermero
    public static void registrarEnfermero() {
        System.out.println("\n--- Registrar Enfermero ---");
        int id;
        while (true) {
            System.out.print("ID: ");
            while (!Persona.SCANNER.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                Persona.SCANNER.next(); // Descarta la entrada inválida
            }
            id = Persona.SCANNER.nextInt();
            Persona.SCANNER.nextLine(); // Consumir salto de línea
            if (buscarEnfermeroPorId(id) == null) {
                break;
            } else {
                System.out.println("El ID ya existe. Por favor, ingrese otro ID.");
            }
        }
        // Usamos los métodos de la clase Persona para solicitar datos
        String nombre = Persona.solicitarNombre();
        String telefono = Persona.solicitarTelefono();
        String direccion = Persona.solicitarDireccion();
        String correo = Persona.solicitarCorreo();
        System.out.print("Especialidad: ");
        String especialidad = Persona.SCANNER.nextLine();
        System.out.print("Turno: ");
        String turno = Persona.SCANNER.nextLine();

        Enfermero enfermero = new Enfermero(id, nombre, telefono, direccion, correo, especialidad, turno);
        AdministracionPersonal.registrarEnfermero(enfermero);
        System.out.println("Enfermero registrado exitosamente.");
    }

    // Método para eliminar un enfermero existente
    public static void eliminarEnfermero() {
        System.out.println("\n--- Eliminar Enfermero ---");
        System.out.print("ID del Enfermero: ");
        int id = Persona.SCANNER.nextInt();
        Persona.SCANNER.nextLine();
        Enfermero enfermero = buscarEnfermeroPorId(id);
        if (enfermero != null) {
            AdministracionPersonal.eliminarEnfermero(enfermero);
            System.out.println("Enfermero eliminado exitosamente.");
        } else {
            System.out.println("Enfermero no encontrado.");
        }
    }

    // Método para buscar un enfermero por su ID
    public static Enfermero buscarEnfermeroPorId(int id) {
        for (Enfermero enfermero : AdministracionPersonal.getEnfermeros()) { // Cambiado para coincidir con el menú
            if (enfermero.getId() == id) {
                return enfermero;
            }
        }
        return null;
    }

    @Override
    public void registrar() {
        registrarEnfermero();
    }

    @Override
    public void actualizar() {
        actualizarEnfermero();
    }

    @Override
    public void eliminar() {
        eliminarEnfermero();
    }
}
