package model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Esta clase representa el historial médico de un paciente.
 */
public class HistorialMedico {

    private int pacienteId;
    private Paciente paciente;
    private List<String> registros;
    private Date fechaActualizacion;

    // Formateador para la fecha de actualización.
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // Constructor de la clase HistorialMedico
    public HistorialMedico(int pacienteId, Paciente paciente) {
        this.pacienteId = pacienteId;
        this.paciente = paciente;
        this.registros = new ArrayList<>();
        this.fechaActualizacion = new Date();
    }

    // Método para agregar un registro al historial médico
    public void agregarRegistro(String registro) {
        registros.add(registro);
        actualizarFecha();
    }

    // Método para actualizar la fecha de la última actualización del historial
    private void actualizarFecha() {
        this.fechaActualizacion = new Date();
    }

    // Método para obtener el historial médico en formato de texto
    public String obtenerHistorial() {
        if (registros.isEmpty()) {
            return "No hay registros en el historial médico.";
        }
        StringBuilder historial = new StringBuilder();
        for (String registro : registros) {
            historial.append(registro).append("\n");
        }
        historial.append("Última actualización: ").append(dateFormat.format(fechaActualizacion));
        return historial.toString();
    }

    // Métodos getter para los atributos de la clase
    public int getPacienteId() {
        return pacienteId;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }
}
