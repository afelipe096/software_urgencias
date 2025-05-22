package repository;
import java.util.ArrayList;
import java.util.List;

import model.Enfermero;
import model.Medico;

public class AdministracionPersonal {
    // Lista centralizada de médicos registrados
    private static List<Medico> medicos = new ArrayList<>();
    // Lista centralizada de enfermeros registrados
    private static List<Enfermero> enfermeros = new ArrayList<>();

    // Bloque static para cargar automáticamente los datos predeterminados al iniciar la aplicación
    static {
        medicos.addAll(DatosPredeterminados.cargarMedicos());
        enfermeros.addAll(DatosPredeterminados.crearEnfermeros());
    }

    // ================= MÉTODOS PARA MÉDICOS =================

    /**
     * Retorna la lista de médicos registrados.
     * @return Lista de médicos.
     */
    public static List<Medico> getMedicos() {
        return medicos;
    }

    /**
     * Registra un nuevo médico en la lista.
     * @param medico Médico a registrar.
     */
    public static void registrarMedico(Medico medico) {
        medicos.add(medico);
    }

    /**
     * Actualiza los datos de un médico existente.
     * @param medicoActualizado Médico con los datos actualizados.
     */
    public static void actualizarMedico(Medico medicoActualizado) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medicoActualizado.getId()) {
                medicos.set(i, medicoActualizado);
                break;
            }
        }
    }

    /**
     * Elimina un médico de la lista.
     * @param medico Médico a eliminar.
     */
    public static void eliminarMedico(Medico medico) {
        medicos.remove(medico);
    }

    /**
     * Busca un médico por su ID.
     * @param id ID del médico.
     * @return El médico encontrado o null si no existe.
     */
    public static Medico buscarMedicoPorId(int id) {
        for (Medico m : medicos) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    // ================= MÉTODOS PARA ENFERMEROS =================

    /**
     * Retorna la lista de enfermeros registrados.
     * @return Lista de enfermeros.
     */
    public static List<Enfermero> getEnfermeros() {
        return enfermeros;
    }

    /**
     * Registra un nuevo enfermero en la lista.
     * @param enfermero Enfermero a registrar.
     */
    public static void registrarEnfermero(Enfermero enfermero) {
        enfermeros.add(enfermero);
    }

    /**
     * Actualiza los datos de un enfermero existente.
     * @param enfermeroActualizado Enfermero con los datos actualizados.
     */
    public static void actualizarEnfermero(Enfermero enfermeroActualizado) {
        for (int i = 0; i < enfermeros.size(); i++) {
            if (enfermeros.get(i).getId() == enfermeroActualizado.getId()) {
                enfermeros.set(i, enfermeroActualizado);
                break;
            }
        }
    }

    /**
     * Elimina un enfermero de la lista.
     * @param enfermero Enfermero a eliminar.
     */
    public static void eliminarEnfermero(Enfermero enfermero) {
        enfermeros.remove(enfermero);
    }

    /**
     * Busca un enfermero por su ID.
     * @param id ID del enfermero.
     * @return El enfermero encontrado o null si no existe.
     */
    public static Enfermero buscarEnfermeroPorId(int id) {
        for (Enfermero e : enfermeros) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
