package repository;
import java.util.ArrayList;
import java.util.List;

import model.Enfermero;
import model.Medico;
import model.Paciente;

public class DatosPredeterminados {
    // Lista centralizada de pacientes registrados
    private static List<Paciente> pacientes = new ArrayList<>();

    // Bloque static para cargar automáticamente los pacientes predeterminados al iniciar la aplicación
    static {
        crearPacientes();
    }

    /**
     * Carga y retorna una lista de médicos predeterminados.
     * @return Lista de médicos.
     */
    public static List<Medico> cargarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        medicos.add(new Medico(1, "Dr. Juan Pérez", "3185125840", "Cra 50 # n-40", "juan.perez@gmail.com", "Cardiología"));
        medicos.add(new Medico(2, "Dra. Ana Gómez", "3007451982", "Cl 116 # p-19", "ana.gomez@gmail.com", "Neurología"));
        medicos.add(new Medico(3, "Dr. Luis Martínez", "3046210375", "Cra 24 # q-27", "luis.martinez@gmail.com", "Pediatría"));
        medicos.add(new Medico(4, "Dra. Sofía Torres", "3209548137", "Cl 60 # r-11", "sofia.torres@gmail.com", "Dermatología"));
        medicos.add(new Medico(5, "Dr. Miguel Ángel", "3152047681", "Cra 20 # s-03", "miguel.angel@gmail.com", "Gastroenterología"));
        medicos.add(new Medico(6, "Dra. Elena Ruiz", "3016389274", "Cl 44 # t-09", "elena.ruiz@gmail.com", "Endocrinología"));
        // Agregar más médicos según sea necesario
        return medicos;
    }

    /**
     * Carga y retorna una lista de enfermeros predeterminados.
     * @return Lista de enfermeros.
     */
    public static List<Enfermero> crearEnfermeros() {
        List<Enfermero> enfermeros = new ArrayList<>();
        enfermeros.add(new Enfermero(1, "Enf. Carlos López", "3114527093", "Cra 11 # b-29", "carlos.lopez@gmail.com", "Urgencias", "Mañana"));
        enfermeros.add(new Enfermero(2, "Enf. María Rodríguez", "3129846150", "Cl 72 # a-15", "maria.rodriguez@gmail.com", "Pediatría", "Noche"));
        enfermeros.add(new Enfermero(3, "Enf. José Hernández", "3160734521", "Cra 15 # f-34", "jose.hernandez@gmail.com", "Traumatología", "Tarde"));
        enfermeros.add(new Enfermero(4, "Enf. Laura García", "3175298460", "Cl 108 # c-21", "laura.garcia@gmail.com", "Oncología", "Mañana"));
        enfermeros.add(new Enfermero(5, "Enf. Luis Fernández", "3146859037", "Cra 7 # d-45", "luis.fernandez@gmail.com", "Cardiología", "Noche"));
        enfermeros.add(new Enfermero(6, "Enf. Ana Torres", "3102984516", "Cl 26 # e-17", "ana.torres@gmail.com", "Neurología", "Tarde"));
        // Agregar más enfermeros según sea necesario
        return enfermeros;
    }

    /**
     * Crea y carga la lista de pacientes predeterminados.
     * Limpia la lista antes de agregar para evitar duplicados.
     * @return Lista de pacientes predeterminados.
     */
    public static List<Paciente> crearPacientes() {
        pacientes.clear(); // Limpia la lista antes de agregar
        pacientes.add(new Paciente(1, "Pedro Sánchez", "3198426701", "Cra 30 # g-12  ", "pedro.sanchez@gmail.com", null, null, null));
        pacientes.add(new Paciente(2, "Laura Martínez", "3035712986", "Cl 13 # h-48", "laura.martinez@gmail.com", null, null, null));
        pacientes.add(new Paciente(3, "Carlos Gómez", "3056487219", "Cra 9 # j-36", "carlos.gomez@gmail.com", null, null, null));
        pacientes.add(new Paciente(4, "Ana López", "3221903847", "Cl 100 # k-22", "ana.lopez@gmail.com", null, null, null));
        pacientes.add(new Paciente(5, "Marta Díaz", "3138765092", "Cra 68 # l-51", "marta.diaz@gmail.com", null, null, null));
        pacientes.add(new Paciente(6, "Jorge Ramírez", "3231478960", "Cl 85 # m-33", "jorge.ramirez@gmail.com", null, null, null));
        // Agregar más pacientes según sea necesario
        return pacientes;
    }

    /**
     * Elimina un paciente existente de la lista.
     * @param paciente Paciente a eliminar.
     */
    public static void eliminarPaciente(Paciente paciente) {
        pacientes.remove(paciente);
    }

    /**
     * Actualiza los datos de un paciente existente.
     * @param pacienteActualizado Paciente con los datos actualizados.
     */
    public static void actualizarPaciente(Paciente pacienteActualizado) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == pacienteActualizado.getId()) {
                pacientes.set(i, pacienteActualizado);
                break;
            }
        }
    }

    /**
     * Retorna la lista de pacientes registrados.
     * @return Lista de pacientes.
     */
    public static List<Paciente> getPacientesRegistrados() {
        return pacientes;
    }
}
