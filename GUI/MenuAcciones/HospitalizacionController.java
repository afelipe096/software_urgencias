package GUI.MenuAcciones;

import model.Paciente;
import model.TratamientoMedico;
import repository.DatosPredeterminados;
import repository.Hospitalizacion;
import GUI.InputsPersonalizados;
import model.SistemaPagos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HospitalizacionController {

    /**
     * Muestra el módulo principal de hospitalización con las opciones disponibles.
     * @param parent Ventana principal como referencia.
     * @param hospitalizacion Instancia global de hospitalización.
     */
    public static void mostrarModulo(JFrame parent, Hospitalizacion hospitalizacion) {
        JDialog dialog = new JDialog(parent, "Gestión de Hospitalización", true);
        dialog.setSize(480, 340);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Hospitalización");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        JButton btnVerHospitalizados = InputsPersonalizados.crearBoton("Ver Pacientes Hospitalizados");
        JButton btnDarAlta = InputsPersonalizados.crearBoton("Pagos y Dar de Alta");

        btnVerHospitalizados.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDarAlta.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(btnVerHospitalizados);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnDarAlta);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnCerrar);

        btnVerHospitalizados.addActionListener(_ -> mostrarHospitalizados(dialog, hospitalizacion));
        btnDarAlta.addActionListener(_ -> darDeAlta(dialog, hospitalizacion));
        btnCerrar.addActionListener(_ -> dialog.dispose());

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Muestra la lista de pacientes hospitalizados y el estado de las camas.
     * @param parent Componente padre para centrar el diálogo.
     * @param hospitalizacion Instancia global de hospitalización.
     */
    private static void mostrarHospitalizados(Component parent, Hospitalizacion hospitalizacion) {
        List<Paciente> hospitalizados = hospitalizacion.getPacientesHospitalizados();
        StringBuilder sb = new StringBuilder("<html><body style='font-family:Segoe UI Emoji;font-size:13px;'>");
        if (hospitalizados.isEmpty()) {
            sb.append("No hay pacientes hospitalizados 🛏️.");
        } else {
            for (Paciente p : hospitalizados) {
                sb.append("<b>ID:</b> ").append(p.getId())
                  .append("<br><b>Nombre:</b> ").append(p.getNombre())
                  .append("<br><b>Teléfono:</b> ").append(p.getTelefono())
                  .append("<br><b>Dirección:</b> ").append(p.getDireccion())
                  .append("<br><b>Correo:</b> ").append(p.getCorreo());
                // Mostrar nivel de triage si existe
                if (p.getNivelTriage() != 0) {
                    sb.append("<br><b>Nivel de Triage:</b> ").append(p.getNivelTriage());
                } else {
                    sb.append("<br><b>Nivel de Triage:</b> No evaluado");
                }
                // Mostrar enfermedades/síntomas si existen
                if (p.getSintomas() != null && !p.getSintomas().isEmpty()) {
                    sb.append("<br><b>Enfermedades:</b> ").append(String.join(", ", p.getSintomas()));
                } else {
                    sb.append("<br><b>Enfermedades:</b> No asignadas");
                }
                sb.append(" <span style='font-size:15px;'>🧑‍⚕️</span>")
                  .append("<hr>");
            }
        }
        sb.append("</body></html>");
        InputsPersonalizados.mostrarMensajePersonalizado(parent, sb.toString());

        // Mostrar estado de camas con emoji
        String estadoCamas = hospitalizacion.estadoCamasString() + " 🛏️";
        InputsPersonalizados.mostrarMensajePersonalizado(parent, estadoCamas);
    }

    /**
     * Permite dar de alta a un paciente hospitalizado, mostrando su recibo y eliminándolo del sistema.
     * Solo permite dar de alta si el paciente tiene triage y fue atendido por un doctor.
     * @param parent Componente padre para centrar el diálogo.
     * @param hospitalizacion Instancia global de hospitalización.
     */
    private static void darDeAlta(Component parent, Hospitalizacion hospitalizacion) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Dar de Alta", "Ingrese el ID del paciente a dar de alta:");
        if (idStr == null || idStr.trim().isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> no puede estar vacío. ⚠️");
            return;
        }
        try {
            int id = Integer.parseInt(idStr.trim());
            Paciente pacienteAlta = null;
            // Buscar paciente por ID
            for (Paciente p : DatosPredeterminados.getPacientesRegistrados()) {
                if (p.getId() == id) {
                    pacienteAlta = p;
                    break;
                }
            }
            if (pacienteAlta != null) {
                // Validar que tenga triage y que haya sido atendido por un doctor
                if (pacienteAlta.getSintomas() == null || pacienteAlta.getSintomas().isEmpty()) {
                    InputsPersonalizados.mostrarMensajePersonalizado(parent, "El paciente no ha sido evaluado.");
                    return;
                }
                if (pacienteAlta.getAtendidoPor() == null || pacienteAlta.getAtendidoPor().trim().isEmpty()) {
                    InputsPersonalizados.mostrarMensajePersonalizado(parent, "El paciente no ha sido atendido.");
                    return;
                }

                // Mostrar datos del paciente antes de dar de alta
                StringBuilder datos = new StringBuilder();
                datos.append("<html><body style='font-family:Segoe UI Emoji;font-size:13px;'>")
                     .append("<b style='font-size:15px;'>Datos del Paciente</b> 🧑‍⚕️<br><br>")
                     .append("<b>ID:</b> ").append(pacienteAlta.getId()).append("<br>")
                     .append("<b>Nombre:</b> ").append(pacienteAlta.getNombre()).append("<br>")
                     .append("<b>Teléfono:</b> ").append(pacienteAlta.getTelefono()).append("<br>")
                     .append("<b>Dirección:</b> ").append(pacienteAlta.getDireccion()).append("<br>")
                     .append("<b>Correo:</b> ").append(pacienteAlta.getCorreo()).append("<br>");
                if (pacienteAlta.getNivelTriage() != 0) {
                    datos.append("<b>Nivel de Triage:</b> ").append(pacienteAlta.getNivelTriage()).append("<br>");
                } else {
                    datos.append("<b>Nivel de Triage:</b> No evaluado<br>");
                }
                if (pacienteAlta.getSintomas() != null && !pacienteAlta.getSintomas().isEmpty()) {
                    datos.append("<b>Enfermedades:</b> ").append(String.join(", ", pacienteAlta.getSintomas())).append("<br>");
                } else {
                    datos.append("<b>Enfermedades:</b> No asignadas<br>");
                }
                datos.append("</body></html>");
                InputsPersonalizados.mostrarMensajePersonalizado(parent, datos.toString());

                // Generar el recibo del tratamiento
                TratamientoMedico tratamiento = new TratamientoMedico();
                String recibo = tratamiento.generarReciboString(pacienteAlta) + "<br>🧾";
                double monto = tratamiento.calcularTotal(pacienteAlta);

                // Selección de método de pago
                String[] metodos = {"Efectivo", "Tarjeta bancaria", "Seguro médico"};
                String metodo = InputsPersonalizados.mostrarSeleccionPersonalizada(parent, "Método de Pago", "¿Con qué método desea pagar?", metodos);
                boolean pagado = false;
                if (metodo != null) {
                    switch (metodo) {
                        case "Efectivo":
                            pagado = SistemaPagos.pagarEfectivo(parent, monto);
                            break;
                        case "Tarjeta bancaria":
                            pagado = SistemaPagos.pagarTarjeta(parent, monto);
                            break;
                        case "Seguro médico":
                            pagado = SistemaPagos.pagarSeguro(parent, monto);
                            break;
                    }
                }
                if (!pagado) return;

                // Mostrar recibo final
                InputsPersonalizados.mostrarMensajePersonalizado(parent, recibo);

                // Dar de alta: liberar la cama y eliminar todos los datos del paciente
                hospitalizacion.darDeAlta(pacienteAlta);
                DatosPredeterminados.eliminarPaciente(pacienteAlta);

                InputsPersonalizados.mostrarMensajePersonalizado(parent, "Paciente dado de alta y eliminado del sistema. ✅");
            } else {
                InputsPersonalizados.mostrarMensajePersonalizado(parent, "Paciente no encontrado. ❌");
            }
        } catch (NumberFormatException e) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> debe ser un número entero. 🔢");
        } catch (Exception e) {
            InputsPersonalizados.mostrarMensajePersonalizado(parent, "Error: " + e.getMessage() + " ⚠️");
        }
    }
}
