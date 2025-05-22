package GUI.MenuAcciones;

import model.Enfermero;
import model.Paciente;
import repository.AdministracionPersonal;
import repository.DatosPredeterminados;
import repository.Hospitalizacion;
import GUI.InputsPersonalizados;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;

/**
 * Controlador principal para el módulo de enfermería.
 */
public class EnfermeriaController {
    /**
     * Muestra el módulo principal de enfermería, permitiendo seleccionar un enfermero.
     * @param parent Ventana principal como referencia.
     * @param hospitalizacion Instancia global de hospitalización.
     */
    public static void mostrarModulo(JFrame parent, Hospitalizacion hospitalizacion) {
        mostrarSeleccionEnfermero(parent, hospitalizacion);
    }

    /**
     * Muestra un diálogo para seleccionar un enfermero de la lista.
     */
    private static void mostrarSeleccionEnfermero(JFrame parent, Hospitalizacion hospitalizacion) {
        List<Enfermero> enfermeros = AdministracionPersonal.getEnfermeros();
        if (enfermeros.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "No hay enfermeros registrados.");
            return;
        }
        String[] nombres = enfermeros.stream().map(e -> e.getId() + " - " + e.getNombre()).toArray(String[]::new);
        String seleccion = InputsPersonalizados.mostrarSeleccionPersonalizada(parent, "Enfermeros", "Seleccione un enfermero:", nombres);
        if (seleccion == null) return;
        int idSeleccionado = Integer.parseInt(seleccion.split(" - ")[0]);
        Enfermero enfermero = AdministracionPersonal.buscarEnfermeroPorId(idSeleccionado);
        if (enfermero == null) {
            InputsPersonalizados.mostrarError(parent, "Debe seleccionar un enfermero de la lista.");
            return;
        }
        mostrarMenuEnfermero(parent, enfermero, hospitalizacion);
    }

    /**
     * Muestra el menú principal del enfermero con las acciones disponibles.
     */
    private static void mostrarMenuEnfermero(JFrame parent, Enfermero enfermero, Hospitalizacion hospitalizacion) {
        JDialog dialog = new JDialog(parent, "Menú Enfermero: " + enfermero.getNombre(), true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Gestión de Pacientes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        JButton btnListar = InputsPersonalizados.crearBoton("Ver pacientes disponibles");
        JButton btnAgregar = InputsPersonalizados.crearBoton("Agregar Paciente");
        JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar Paciente");
        JButton btnEliminarTriage = InputsPersonalizados.crearBoton("Eliminar asignación de triage");
        JButton btnAsignarTriage = InputsPersonalizados.crearBoton("Asignar Triage");

        btnListar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnActualizar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEliminarTriage.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAsignarTriage.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(btnListar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnAgregar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnActualizar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnEliminarTriage);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnAsignarTriage);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnCerrar);

        btnListar.addActionListener(_ -> listarPacientes(dialog));
        btnAgregar.addActionListener(_ -> agregarPaciente(dialog));
        btnActualizar.addActionListener(_ -> actualizarPaciente(dialog));
        btnEliminarTriage.addActionListener(_ -> eliminarAsignacionTriage(dialog));
        btnAsignarTriage.addActionListener(_ -> asignarTriage(dialog, enfermero, hospitalizacion));
        btnCerrar.addActionListener(_ -> dialog.dispose());

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Muestra la lista de pacientes registrados en un diálogo con scroll personalizado.
     */
    private static void listarPacientes(Component parent) {
        List<Paciente> pacientes = DatosPredeterminados.getPacientesRegistrados();
        StringBuilder sb = new StringBuilder("<html><body style='font-family:Segoe UI;font-size:12px;'>");
        if (pacientes.isEmpty()) {
            sb.append("No hay pacientes registrados.");
        } else {
            for (Paciente p : pacientes) {
                sb.append("<b>ID:</b> ").append(p.getId())
                  .append("<br><b>Nombre:</b> ").append(p.getNombre())
                  .append("<br><b>Teléfono:</b> ").append(p.getTelefono())
                  .append("<br><b>Dirección:</b> ").append(p.getDireccion())
                  .append("<br><b>Correo:</b> ").append(p.getCorreo())
                  .append("<hr>");
            }
        }
        sb.append("</body></html>");
        JEditorPane area = new JEditorPane("text/html", sb.toString());
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(460, 260));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // Scroll personalizado
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(55, 103, 229);
                this.trackColor = new Color(230, 230, 230);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(55, 103, 229);
                this.trackColor = new Color(230, 230, 230);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        // Muestra en un JDialog para mantener la estética
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            "Lista de Pacientes",
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        btnCerrar.addActionListener(_ -> dialog.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        bottomPanel.add(btnCerrar);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setSize(520, 420);
        dialog.setVisible(true);
    }

    /**
     * Muestra un formulario para agregar un nuevo paciente.
     * Incluye validaciones de campos.
     */
    private static void agregarPaciente(Component parent) {
        JDialog dialog = new JDialog(
            SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null,
            "Agregar Paciente", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Agregar Paciente");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        JTextField idField = InputsPersonalizados.crearCampo(panel, "ID:");
        JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:");
        JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:");
        JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:");
        JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:");

        panel.add(Box.createVerticalStrut(20));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setBackground(Color.WHITE);
        JButton btnAgregar = InputsPersonalizados.crearBoton("Agregar");
        JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");
        btnPanel.add(btnAgregar);
        btnPanel.add(btnCancelar);

        panel.add(btnPanel);

        btnAgregar.addActionListener(_ -> {
            if (idField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>ID</b> no puede estar vacío.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idField.getText().trim());
            } catch (NumberFormatException ex) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>ID</b> debe ser un número entero.");
                return;
            }
            if (nombreField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Nombre</b> no puede estar vacío.");
                return;
            }
            if (!utils.Validadores.esNombreValido(nombreField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                return;
            }
            if (telefonoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                return;
            }
            if (!utils.Validadores.esTelefonoValido(telefonoField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El teléfono debe tener exactamente 10 dígitos numéricos.");
                return;
            }
            if (direccionField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Dirección</b> no puede estar vacío.");
                return;
            }
            if (correoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Correo</b> no puede estar vacío.");
                return;
            }
            if (!utils.Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                return;
            }
            for (Paciente p : DatosPredeterminados.getPacientesRegistrados()) {
                if (p.getId() == id) {
                    InputsPersonalizados.mostrarError(dialog, "Ya existe un paciente con ese ID.");
                    return;
                }
            }
            try {
                Paciente paciente = new Paciente(id, nombreField.getText(), telefonoField.getText(),
                        direccionField.getText(), correoField.getText(), null, null, null);
                DatosPredeterminados.getPacientesRegistrados().add(paciente);
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Paciente agregado exitosamente.");
                dialog.dispose();
            } catch (Exception ex) {
                InputsPersonalizados.mostrarError(dialog, "Error al agregar paciente: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(_ -> dialog.dispose());

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Permite actualizar los datos de un paciente existente.
     * Incluye validaciones de campos.
     */
    private static void actualizarPaciente(Component parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Actualizar Paciente", "Ingrese el ID del paciente a actualizar:");
        if (idStr == null || idStr.trim().isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "Por favor, ingrese un <b>ID válido</b>.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El <b>ID</b> debe ser un número entero.");
            return;
        }
        try {
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

            JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent) instanceof Frame
                    ? (Frame) SwingUtilities.getWindowAncestor(parent)
                    : null,
                "Actualizar Paciente", true);
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);

            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JLabel title = new JLabel("Actualizar Paciente");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setForeground(new Color(33, 43, 54));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(title);
            panel.add(Box.createVerticalStrut(15));

            JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:", paciente.getNombre());
            JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:", paciente.getTelefono());
            JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:", paciente.getDireccion());
            JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:", paciente.getCorreo());

            panel.add(Box.createVerticalStrut(20));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            btnPanel.setBackground(Color.WHITE);
            JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar");
            JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");
            btnPanel.add(btnActualizar);
            btnPanel.add(btnCancelar);

            panel.add(btnPanel);

            // Usar variable final para el paciente en la lambda
            final Paciente pacienteFinal = paciente;
            btnActualizar.addActionListener(_ -> {
                if (nombreField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Nombre</b> no puede estar vacío.");
                    return;
                }
                if (!utils.Validadores.esNombreValido(nombreField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                    return;
                }
                if (telefonoField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                    return;
                }
                if (!utils.Validadores.esTelefonoValido(telefonoField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El teléfono debe tener exactamente 10 dígitos numéricos.");
                    return;
                }
                if (direccionField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Dirección</b> no puede estar vacío.");
                    return;
                }
                if (correoField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Correo</b> no puede estar vacío.");
                    return;
                }
                if (!utils.Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                    return;
                }
                pacienteFinal.setNombre(nombreField.getText());
                pacienteFinal.setTelefono(telefonoField.getText());
                pacienteFinal.setDireccion(direccionField.getText());
                pacienteFinal.setCorreo(correoField.getText());
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Paciente actualizado exitosamente.");
                dialog.dispose();
            });

            btnCancelar.addActionListener(_ -> dialog.dispose());

            dialog.setContentPane(panel);
            dialog.setVisible(true);

        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Error: " + e.getMessage());
        }
    }

    /**
     * Permite eliminar la asignación de triage de un paciente por ID.
     */
    private static void eliminarAsignacionTriage(Component parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Eliminar Triage", "Ingrese el ID del paciente para eliminar asignación de triage:");
        if (idStr == null || idStr.trim().isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "Por favor, ingrese un <b>ID válido</b>.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El <b>ID</b> debe ser un número entero.");
            return;
        }
        try {
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
            // Verifica si el paciente tiene triage asignado (tiene síntomas)
            if (paciente.getSintomas() == null || paciente.getSintomas().isEmpty()) {
                InputsPersonalizados.mostrarMensajePersonalizado(parent, "El paciente no tiene triage asignado.");
                return;
            }
            paciente.eliminarAsignacionTriage();
            InputsPersonalizados.mostrarMensajePersonalizado(parent, "Asignación de triage eliminada para el paciente " + paciente.getNombre() + ".");
        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Error: " + e.getMessage());
        }
    }

    /**
     * Permite asignar triage a un paciente y, si corresponde, asignar cama.
     * Solo permite asignar triage si el paciente no tiene uno ya asignado.
     */
    private static void asignarTriage(Component parent, Enfermero enfermero, Hospitalizacion hospitalizacion) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Asignar Triage", "Ingrese el ID del paciente para asignar Triage:");
        if (idStr == null || idStr.trim().isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "Por favor, ingrese un <b>ID válido</b> para el paciente.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El <b>ID</b> debe ser un número entero.");
            return;
        }
        Paciente paciente = null;
        for (Paciente p : DatosPredeterminados.getPacientesRegistrados()) {
            if (p.getId() == id) {
                paciente = p;
                break;
            }
        }
        if (paciente == null) {
            InputsPersonalizados.mostrarError(parent, "No se encontró un paciente con ese <b>ID</b>.");
            return;
        }

        // Nuevo: No permitir asignar más de un triage
        if (paciente.getSintomas() != null && !paciente.getSintomas().isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El paciente ya tiene un triage asignado.");
            return;
        }

        // Asignar triage usando la lógica del enfermero
        enfermero.asignarTriage(paciente, parent);

        // Si el paciente tiene triage asignado, revisa el nivel
        if (paciente.getSintomas() != null && !paciente.getSintomas().isEmpty()) {
            if (paciente.getNivelTriage() == 1) {
                InputsPersonalizados.mostrarMensajePersonalizado(parent, "Nivel de Prioridad 1, Se le asignará una cama.");
                hospitalizacion.asignarCama(paciente);
            }
            InputsPersonalizados.mostrarMensajePersonalizado(parent, "Triage asignado exitosamente.");
        }
    }
}
