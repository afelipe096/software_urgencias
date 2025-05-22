package GUI.MenuAcciones;

import model.Medico;
import model.Paciente;
import model.TratamientoMedico;
import repository.AdministracionPersonal;
import repository.DatosPredeterminados;
import GUI.InputsPersonalizados; // <-- Importa la clase centralizada

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Controlador para las acciones relacionadas con los médicos.
 */
public class MedicoController {

    /**
     * Muestra el módulo principal de médicos, permitiendo seleccionar un médico.
     * @param parent Ventana principal como referencia.
     */
    public static void mostrarModulo(JFrame parent) {
        mostrarSeleccionMedico(parent);
    }

    /**
     * Muestra un diálogo para seleccionar un médico de la lista.
     */
    private static void mostrarSeleccionMedico(JFrame parent) {
        List<Medico> medicos = AdministracionPersonal.getMedicos();
        if (medicos.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "No hay médicos registrados.");
            return;
        }
        String[] nombres = medicos.stream().map(m -> m.getId() + " - " + m.getNombre()).toArray(String[]::new);
        String seleccion = InputsPersonalizados.mostrarComboPersonalizado(
                parent,
                "Médicos",
                "Seleccione un médico:",
                nombres
        );
        if (seleccion == null) return;
        int idSeleccionado;
        try {
            idSeleccionado = Integer.parseInt(seleccion.split(" - ")[0].trim());
        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Selección inválida.");
            return;
        }
        Medico medico = AdministracionPersonal.buscarMedicoPorId(idSeleccionado);
        if (medico == null) {
            InputsPersonalizados.mostrarError(parent, "No se encontró el médico seleccionado.");
            return;
        }

        mostrarMenuMedico(parent, medico);
    }

    /**
     * Muestra el menú principal del médico con las acciones disponibles.
     */
    private static void mostrarMenuMedico(JFrame parent, Medico medico) {
        JDialog dialog = new JDialog(parent, "Menú Médico: " + medico.getNombre(), true);
        dialog.setSize(420, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Evaluación de Triage");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        // Usa los botones personalizados centralizados
        JButton btnEvaluar = InputsPersonalizados.crearBoton("Evaluar Triage (Consultar y tratar paciente)");
        btnEvaluar.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(btnEvaluar);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnCerrar);

        btnEvaluar.addActionListener(_ -> evaluarTriage(dialog, medico));
        btnCerrar.addActionListener(_ -> dialog.dispose());

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Evalúa el triage y genera el informe de tratamiento para el paciente seleccionado.
     * El médico queda registrado como "atendido por" en el paciente.
     * @param parent Componente padre para centrar el diálogo.
     * @param medico Médico que atiende al paciente.
     */
    private static void evaluarTriage(Component parent, Medico medico) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Evaluar Triage", "Ingrese el ID del paciente a evaluar:");
        if (idStr == null) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            Paciente paciente = null;
            for (Paciente p : DatosPredeterminados.getPacientesRegistrados()) {
                if (p.getId() == id) {
                    paciente = p;
                    break;
                }
            }
            if (paciente == null) {
                InputsPersonalizados.mostrarError(parent, "No se encontró un paciente con ese ID.");
                return;
            }
            if (paciente.getSintomas() == null || paciente.getSintomas().isEmpty()) {
                InputsPersonalizados.mostrarError(parent, "El paciente no tiene un triage asignado.");
                return;
            }
            // El médico atiende al paciente usando el triage ya asignado
            medico.atenderPaciente(paciente);
            paciente.setAtendidoPor(medico.getNombre());

            // Generar y mostrar el informe de tratamiento
            TratamientoMedico tratamiento = new TratamientoMedico();
            String informe = tratamiento.generarInformeTratamientoString(paciente);
            InputsPersonalizados.mostrarMensajePersonalizado(parent, informe);
        } catch (NumberFormatException e) {
            InputsPersonalizados.mostrarError(parent, "El ID debe ser un número entero.");
        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Error: " + e.getMessage());
        }
    }
}
