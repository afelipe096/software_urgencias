package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Paciente;

public class Hospitalizacion {
    // Lista de pacientes actualmente hospitalizados
    private List<Paciente> pacientesHospitalizados = new ArrayList<>();
    // Mapa de número de cama a disponibilidad (true = libre, false = ocupada)
    private Map<Integer, Boolean> camasDisponibles = new HashMap<>();

    /**
     * Constructor: inicializa las camas como libres.
     * @param totalCamas Número total de camas en el hospital.
     */
    public Hospitalizacion(int totalCamas) {
        for (int i = 1; i <= totalCamas; i++) {
            camasDisponibles.put(i, true); // Todas las camas empiezan libres
        }
    }

    /**
     * Asigna una cama disponible al paciente.
     * @param paciente Paciente que se hospitaliza.
     * @return true si se asignó una cama, false si no hay camas disponibles.
     */
    public boolean asignarCama(Paciente paciente) {
        if (paciente.getNumeroCama() != null) {
            System.out.println("El paciente " + paciente.getNombre() + " ya tiene una cama asignada.");
            return false;
        }
        for (Map.Entry<Integer, Boolean> entrada : camasDisponibles.entrySet()) {
            if (entrada.getValue()) { // Si la cama está disponible
                paciente.setNumeroCama(entrada.getKey());
                if (!pacientesHospitalizados.contains(paciente)) {
                    pacientesHospitalizados.add(paciente);
                }
                camasDisponibles.put(entrada.getKey(), false); // Ocupa la cama
                System.out.println("Cama " + entrada.getKey() + " asignada a " + paciente.getNombre());
                return true;
            }
        }
        System.out.println("No hay camas disponibles.");
        return false;
    }

    /**
     * Da de alta al paciente liberando la cama asignada.
     * @param paciente Paciente que se da de alta.
     */
    public void darDeAlta(Paciente paciente) {
        Integer cama = paciente.getNumeroCama();
        if (cama != null) {
            camasDisponibles.put(cama, true); // Libera la cama
            pacientesHospitalizados.remove(paciente);
            paciente.setNumeroCama(null);
            System.out.println("Paciente " + paciente.getNombre() + " dado de alta. Cama " + cama + " ahora está libre.");
        } else {
            System.out.println("El paciente no tenía una cama asignada.");
        }
    }

    /**
     * Devuelve el estado actual de las camas (libres/ocupadas) como String HTML para mostrar en GUI.
     * Incluye el nombre del paciente si la cama está ocupada.
     */
    public String estadoCamasString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family:Segoe UI Emoji;font-size:15px;'>");
        sb.append("<b>Estado de camas:</b><br>");
        for (Map.Entry<Integer, Boolean> entrada : camasDisponibles.entrySet()) {
            Integer numCama = entrada.getKey();
            boolean libre = entrada.getValue();
            if (libre) {
                sb.append("Cama ").append(numCama).append(": 🛏️ <span style='color:green;'>Libre</span><br>");
            } else {
                // Busca el paciente que tiene esta cama
                Paciente ocupante = null;
                for (Paciente p : pacientesHospitalizados) {
                    if (p.getNumeroCama() != null && p.getNumeroCama().equals(numCama)) {
                        ocupante = p;
                        break;
                    }
                }
                sb.append("Cama ").append(numCama).append(": 🛏️ <span style='color:red;'>Ocupada</span>");
                if (ocupante != null) {
                    sb.append(" por <b>").append(ocupante.getNombre()).append("</b>");
                }
                sb.append("<br>");
            }
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    /**
     * Muestra la lista de pacientes que actualmente se encuentran hospitalizados en consola.
     */
    public void verPacientesHospitalizados() {
        System.out.println("\n--- Pacientes Hospitalizados ---");
        if (pacientesHospitalizados.isEmpty()) {
            System.out.println("No hay pacientes hospitalizados en este momento.");
        } else {
            for (Paciente paciente : pacientesHospitalizados) {
                paciente.mostrarInfo();
                System.out.println("---------------------");
            }
        }
    }

    /**
     * Devuelve una copia de la lista de pacientes hospitalizados (para uso en GUI).
     * @return Lista de pacientes hospitalizados.
     */
    public List<Paciente> getPacientesHospitalizados() {
        return new ArrayList<>(pacientesHospitalizados);
    }
}