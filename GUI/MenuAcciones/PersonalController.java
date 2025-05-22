package GUI.MenuAcciones;

import GUI.InputsPersonalizados;
import model.Enfermero;
import model.Medico;
import repository.AdministracionPersonal;

import utils.Validadores;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;

/**
 * Controlador para la gestión de personal médico y enfermero.
 * Proporciona métodos para mostrar menús y realizar operaciones CRUD.
 */
public class PersonalController {

    /**
     * Muestra el módulo principal de gestión de personal.
     * Permite elegir entre gestionar médicos o enfermeros.
     */
    public static void mostrarModulo(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Jefe de Personal", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        // Panel de botones a la izquierda
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Gestión de Personal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        JButton btnMedicos = InputsPersonalizados.crearBoton("Gestionar Médicos");
        JButton btnEnfermeros = InputsPersonalizados.crearBoton("Gestionar Enfermeros");

        btnMedicos.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEnfermeros.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(btnMedicos);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnEnfermeros);

        // Botón cerrar en la esquina inferior derecha
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnCerrar);

        btnMedicos.addActionListener(_ -> {
            dialog.dispose();
            gestionarMedicos(parent);
        });
        btnEnfermeros.addActionListener(_ -> {
            dialog.dispose();
            gestionarEnfermeros(parent);
        });
        btnCerrar.addActionListener(_ -> dialog.dispose());

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // ================= MÉDICOS =================

    /**
     * Muestra el menú de gestión de médicos.
     * Permite registrar, actualizar, eliminar y listar médicos.
     */
    private static void gestionarMedicos(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Gestión de Médicos", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Gestión de Médicos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        JButton btnRegistrar = InputsPersonalizados.crearBoton("Registrar Médico");
        JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar Médico");
        JButton btnEliminar = InputsPersonalizados.crearBoton("Eliminar Médico");
        JButton btnListar = InputsPersonalizados.crearBoton("Listar Médicos");

        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnActualizar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEliminar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnListar.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(btnRegistrar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnActualizar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnEliminar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnListar);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnVolver = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnVolver);

        btnRegistrar.addActionListener(_ -> {
            dialog.dispose();
            registrarMedico(parent);
        });
        btnActualizar.addActionListener(_ -> {
            dialog.dispose();
            actualizarMedico(parent);
        });
        btnEliminar.addActionListener(_ -> {
            dialog.dispose();
            eliminarMedico(parent);
        });
        btnListar.addActionListener(_ -> {
            dialog.dispose();
            listarMedicos(parent);
        });
        btnVolver.addActionListener(_ -> {
            dialog.dispose();
            mostrarModulo(parent);
        });

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Muestra el formulario para registrar un nuevo médico.
     * Incluye validaciones de campos.
     */
    private static void registrarMedico(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Registrar Médico", true);
        dialog.setSize(400, 420);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Registrar Médico");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(33, 43, 54));
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        JTextField idField = InputsPersonalizados.crearCampo(panel, "ID:");
        JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:");
        JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:");
        JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:");
        JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:");
        JTextField especialidadField = InputsPersonalizados.crearCampo(panel, "Especialidad:");

        panel.add(Box.createVerticalStrut(20));

        JButton btnRegistrar = InputsPersonalizados.crearBoton("Registrar Médico");
        JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.add(btnRegistrar);
        btnPanel.add(btnCancelar);

        panel.add(btnPanel);

        btnRegistrar.addActionListener(_ -> {
            // Validaciones amigables
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
            if (!Validadores.esNombreValido(nombreField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                return;
            }
            if (telefonoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                return;
            }
            if (!Validadores.esTelefonoValido(telefonoField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El teléfono debe tener exactamente 10 dígitos numéricos.");
                return;
            }
            if (correoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Correo</b> no puede estar vacío.");
                return;
            }
            if (!Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                return;
            }
            if (especialidadField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Especialidad</b> no puede estar vacío.");
                return;
            }
            if (AdministracionPersonal.buscarMedicoPorId(id) != null) {
                InputsPersonalizados.mostrarError(dialog, "Ya existe un médico con ese ID.");
                return;
            }
            try {
                Medico medico = new Medico(
                        id,
                        nombreField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        correoField.getText(),
                        especialidadField.getText()
                );
                AdministracionPersonal.registrarMedico(medico);
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Médico registrado exitosamente.");
                dialog.dispose();
                gestionarMedicos(parent);
            } catch (Exception ex) {
                InputsPersonalizados.mostrarError(dialog, "Ocurrió un error inesperado al registrar el médico.");
            }
        });

        btnCancelar.addActionListener(_ -> {
            dialog.dispose();
            gestionarMedicos(parent);
        });

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Permite actualizar los datos de un médico existente.
     * Incluye validaciones de campos.
     */
    private static void actualizarMedico(JFrame parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Actualizar Médico", "Ingrese el ID del médico a actualizar:");
        if (idStr == null) {
            gestionarMedicos(parent);
            return;
        }
        idStr = idStr.trim();
        if (idStr.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> no puede estar vacío.");
            gestionarMedicos(parent);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> debe ser un número entero.");
            gestionarMedicos(parent);
            return;
        }
        try {
            Medico medico = AdministracionPersonal.buscarMedicoPorId(id);
            if (medico == null) {
                InputsPersonalizados.mostrarError(parent, "No se encontró un médico con ese ID.");
                gestionarMedicos(parent);
                return;
            }
            JDialog dialog = new JDialog(parent, "Actualizar Médico", true);
            dialog.setSize(400, 420);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);

            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JLabel title = new JLabel("Actualizar Médico");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setForeground(new Color(33, 43, 54));
            panel.add(title);
            panel.add(Box.createVerticalStrut(15));

            JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:", medico.getNombre());
            JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:", medico.getTelefono());
            JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:", medico.getDireccion());
            JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:", medico.getCorreo());
            JTextField especialidadField = InputsPersonalizados.crearCampo(panel, "Especialidad:", medico.getEspecialidad());

            panel.add(Box.createVerticalStrut(20));

            JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar");
            JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");

            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(Color.WHITE);
            btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
            btnPanel.add(btnActualizar);
            btnPanel.add(btnCancelar);

            panel.add(btnPanel);

            btnActualizar.addActionListener(_ -> {
                // Validaciones amigables
                if (nombreField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Nombre</b> no puede estar vacío.");
                    return;
                }
                if (!Validadores.esNombreValido(nombreField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                    return;
                }
                if (telefonoField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                    return;
                }
                if (!Validadores.esTelefonoValido(telefonoField.getText().trim())) {
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
                if (!Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                    return;
                }
                if (especialidadField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Especialidad</b> no puede estar vacío.");
                    return;
                }
                medico.setNombre(nombreField.getText());
                medico.setTelefono(telefonoField.getText());
                medico.setDireccion(direccionField.getText());
                medico.setCorreo(correoField.getText());
                medico.setEspecialidad(especialidadField.getText());
                AdministracionPersonal.actualizarMedico(medico);
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Médico actualizado exitosamente.");
                dialog.dispose();
                gestionarMedicos(parent);
            });

            btnCancelar.addActionListener(_ -> {
                dialog.dispose();
                gestionarMedicos(parent);
            });

            dialog.setContentPane(panel);
            dialog.setVisible(true);

        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Ocurrió un error inesperado al actualizar el médico.");
            gestionarMedicos(parent);
        }
    }

    /**
     * Permite eliminar un médico por ID, con confirmación.
     */
    private static void eliminarMedico(JFrame parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Eliminar Médico", "Ingrese el ID del médico a eliminar:");
        if (idStr == null) {
            gestionarMedicos(parent);
            return;
        }
        idStr = idStr.trim();
        if (idStr.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> no puede estar vacío.");
            gestionarMedicos(parent);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> debe ser un número entero.");
            gestionarMedicos(parent);
            return;
        }
        try {
            Medico medico = AdministracionPersonal.buscarMedicoPorId(id);
            if (medico == null) {
                InputsPersonalizados.mostrarError(parent, "No se encontró un médico con ese ID.");
                gestionarMedicos(parent);
                return;
            }
            if (InputsPersonalizados.mostrarConfirmacionPersonalizada(parent, "¿Está seguro de eliminar este médico?")) {
                AdministracionPersonal.eliminarMedico(medico);
                InputsPersonalizados.mostrarMensajePersonalizado(parent, "Médico eliminado exitosamente.");
            }
            gestionarMedicos(parent);
        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Ocurrió un error inesperado al eliminar el médico.");
            gestionarMedicos(parent);
        }
    }

    /**
     * Muestra la lista de médicos registrados en formato HTML.
     */
    private static void listarMedicos(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Lista de Médicos", true);
        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Medico> medicos = AdministracionPersonal.getMedicos();
        StringBuilder sb = new StringBuilder("<html><body style='font-family:Segoe UI;font-size:12px;'>");
        if (medicos.isEmpty()) {
            sb.append("No hay médicos registrados.");
        } else {
            for (Medico m : medicos) {
                sb.append("<b>ID:</b> ").append(m.getId())
                  .append("<br><b>Nombre:</b> ").append(m.getNombre())
                  .append("<br><b>Teléfono:</b> ").append(m.getTelefono())
                  .append("<br><b>Dirección:</b> ").append(m.getDireccion())
                  .append("<br><b>Correo:</b> ").append(m.getCorreo())
                  .append("<br><b>Tipo:</b> Médico")
                  .append("<br><b>Especialidad:</b> ").append(m.getEspecialidad())
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

        // Cambia el estilo del scroll
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(55, 103, 229); // color barra
                this.trackColor = new Color(230, 230, 230); // color fondo
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

        // Opcional: también para el scroll horizontal
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

        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        btnCerrar.addActionListener(_ -> {
            dialog.dispose();
            gestionarMedicos(parent);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        bottomPanel.add(btnCerrar);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // ================= ENFERMEROS =================

    /**
     * Muestra el menú de gestión de enfermeros.
     * Permite registrar, actualizar, eliminar y listar enfermeros.
     */
    private static void gestionarEnfermeros(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Gestión de Enfermeros", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 10));

        JLabel title = new JLabel("Gestión de Enfermeros");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 43, 54));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(20));

        JButton btnRegistrar = InputsPersonalizados.crearBoton("Registrar Enfermero");
        JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar Enfermero");
        JButton btnEliminar = InputsPersonalizados.crearBoton("Eliminar Enfermero");
        JButton btnListar = InputsPersonalizados.crearBoton("Listar Enfermeros");

        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnActualizar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEliminar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnListar.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(btnRegistrar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnActualizar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnEliminar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnListar);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JButton btnVolver = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        bottomPanel.add(btnVolver);

        btnRegistrar.addActionListener(_ -> {
            dialog.dispose();
            registrarEnfermero(parent);
        });
        btnActualizar.addActionListener(_ -> {
            dialog.dispose();
            actualizarEnfermero(parent);
        });
        btnEliminar.addActionListener(_ -> {
            dialog.dispose();
            eliminarEnfermero(parent);
        });
        btnListar.addActionListener(_ -> {
            dialog.dispose();
            listarEnfermeros(parent);
        });
        btnVolver.addActionListener(_ -> {
            dialog.dispose();
            mostrarModulo(parent);
        });

        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Muestra el formulario para registrar un nuevo enfermero.
     * Incluye validaciones de campos.
     */
    private static void registrarEnfermero(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Registrar Enfermero", true);
        dialog.setSize(400, 470);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Registrar Enfermero");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(33, 43, 54));
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        JTextField idField = InputsPersonalizados.crearCampo(panel, "ID:");
        JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:");
        JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:");
        JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:");
        JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:");
        JTextField especialidadField = InputsPersonalizados.crearCampo(panel, "Especialidad:");
        JTextField turnoField = InputsPersonalizados.crearCampo(panel, "Turno:");

        panel.add(Box.createVerticalStrut(20));

        JButton btnRegistrar = InputsPersonalizados.crearBoton("Registrar Enfermero");
        JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.add(btnRegistrar);
        btnPanel.add(btnCancelar);

        panel.add(btnPanel);

        btnRegistrar.addActionListener(_ -> {
            // Validaciones amigables
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
            if (!Validadores.esNombreValido(nombreField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                return;
            }
            if (telefonoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                return;
            }
            if (!Validadores.esTelefonoValido(telefonoField.getText().trim())) {
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
            if (!Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                return;
            }
            if (especialidadField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Especialidad</b> no puede estar vacío.");
                return;
            }
            if (turnoField.getText().trim().isEmpty()) {
                InputsPersonalizados.mostrarError(dialog, "El campo <b>Turno</b> no puede estar vacío.");
                return;
            }
            if (AdministracionPersonal.buscarEnfermeroPorId(id) != null) {
                InputsPersonalizados.mostrarError(dialog, "Ya existe un enfermero con ese ID.");
                return;
            }
            try {
                Enfermero enfermero = new Enfermero(
                        id,
                        nombreField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        correoField.getText(),
                        especialidadField.getText(),
                        turnoField.getText()
                );
                AdministracionPersonal.registrarEnfermero(enfermero);
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Enfermero registrado exitosamente.");
                dialog.dispose();
                gestionarEnfermeros(parent);
            } catch (Exception ex) {
                InputsPersonalizados.mostrarError(dialog, "Ocurrió un error inesperado al registrar el enfermero.");
            }
        });

        btnCancelar.addActionListener(_ -> {
            dialog.dispose();
            gestionarEnfermeros(parent);
        });

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    /**
     * Permite actualizar los datos de un enfermero existente.
     * Incluye validaciones de campos.
     */
    private static void actualizarEnfermero(JFrame parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Actualizar Enfermero", "Ingrese el ID del enfermero a actualizar:");
        if (idStr == null) {
            gestionarEnfermeros(parent);
            return;
        }
        idStr = idStr.trim();
        if (idStr.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> no puede estar vacío.");
            gestionarEnfermeros(parent);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> debe ser un número entero.");
            gestionarEnfermeros(parent);
            return;
        }
        try {
            Enfermero enfermero = AdministracionPersonal.buscarEnfermeroPorId(id);
            if (enfermero == null) {
                InputsPersonalizados.mostrarError(parent, "No se encontró un enfermero con ese ID.");
                gestionarEnfermeros(parent);
                return;
            }
            JDialog dialog = new JDialog(parent, "Actualizar Enfermero", true);
            dialog.setSize(400, 470);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);

            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JLabel title = new JLabel("Actualizar Enfermero");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setForeground(new Color(33, 43, 54));
            panel.add(title);
            panel.add(Box.createVerticalStrut(15));

            JTextField nombreField = InputsPersonalizados.crearCampo(panel, "Nombre:", enfermero.getNombre());
            JTextField telefonoField = InputsPersonalizados.crearCampo(panel, "Teléfono:", enfermero.getTelefono());
            JTextField direccionField = InputsPersonalizados.crearCampo(panel, "Dirección:", enfermero.getDireccion());
            JTextField correoField = InputsPersonalizados.crearCampo(panel, "Correo:", enfermero.getCorreo());
            JTextField especialidadField = InputsPersonalizados.crearCampo(panel, "Especialidad:", enfermero.getEspecialidad());
            JTextField turnoField = InputsPersonalizados.crearCampo(panel, "Turno:", enfermero.getTurno());

            panel.add(Box.createVerticalStrut(20));

            JButton btnActualizar = InputsPersonalizados.crearBoton("Actualizar");
            JButton btnCancelar = InputsPersonalizados.crearBotonCerrarNegro("Cancelar");

            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(Color.WHITE);
            btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
            btnPanel.add(btnActualizar);
            btnPanel.add(btnCancelar);

            panel.add(btnPanel);

            btnActualizar.addActionListener(_ -> {
                // Validaciones amigables
                if (nombreField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Nombre</b> no puede estar vacío.");
                    return;
                }
                if (!Validadores.esNombreValido(nombreField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El nombre solo debe contener letras y espacios.");
                    return;
                }
                if (telefonoField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Teléfono</b> no puede estar vacío.");
                    return;
                }
                if (!Validadores.esTelefonoValido(telefonoField.getText().trim())) {
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
                if (!Validadores.esCorreoGmailValido(correoField.getText().trim())) {
                    InputsPersonalizados.mostrarError(dialog, "El correo debe ser un Gmail válido (ejemplo@gmail.com).");
                    return;
                }
                if (especialidadField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Especialidad</b> no puede estar vacío.");
                    return;
                }
                if (turnoField.getText().trim().isEmpty()) {
                    InputsPersonalizados.mostrarError(dialog, "El campo <b>Turno</b> no puede estar vacío.");
                    return;
                }
                enfermero.setNombre(nombreField.getText());
                enfermero.setTelefono(telefonoField.getText());
                enfermero.setDireccion(direccionField.getText());
                enfermero.setCorreo(correoField.getText());
                enfermero.setEspecialidad(especialidadField.getText());
                enfermero.setTurno(turnoField.getText());
                AdministracionPersonal.actualizarEnfermero(enfermero);
                InputsPersonalizados.mostrarMensajePersonalizado(dialog, "Enfermero actualizado exitosamente.");
                dialog.dispose();
                gestionarEnfermeros(parent);
            });

            btnCancelar.addActionListener(_ -> {
                dialog.dispose();
                gestionarEnfermeros(parent);
            });

            dialog.setContentPane(panel);
            dialog.setVisible(true);

        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Ocurrió un error inesperado al actualizar el enfermero.");
            gestionarEnfermeros(parent);
        }
    }

    /**
     * Permite eliminar un enfermero por ID, con confirmación.
     */
    private static void eliminarEnfermero(JFrame parent) {
        String idStr = InputsPersonalizados.mostrarInputPersonalizado(parent, "Eliminar Enfermero", "Ingrese el ID del enfermero a eliminar:");
        if (idStr == null) {
            gestionarEnfermeros(parent);
            return;
        }
        idStr = idStr.trim();
        if (idStr.isEmpty()) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> no puede estar vacío.");
            gestionarEnfermeros(parent);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            InputsPersonalizados.mostrarError(parent, "El campo <b>ID</b> debe ser un número entero.");
            gestionarEnfermeros(parent);
            return;
        }
        try {
            Enfermero enfermero = AdministracionPersonal.buscarEnfermeroPorId(id);
            if (enfermero == null) {
                InputsPersonalizados.mostrarError(parent, "No se encontró un enfermero con ese ID.");
                gestionarEnfermeros(parent);
                return;
            }
            if (InputsPersonalizados.mostrarConfirmacionPersonalizada(parent, "¿Está seguro de eliminar este enfermero?")) {
                AdministracionPersonal.eliminarEnfermero(enfermero);
                InputsPersonalizados.mostrarMensajePersonalizado(parent, "Enfermero eliminado exitosamente.");
            }
            gestionarEnfermeros(parent);
        } catch (Exception e) {
            InputsPersonalizados.mostrarError(parent, "Ocurrió un error inesperado al eliminar el enfermero.");
            gestionarEnfermeros(parent);
        }
    }

    /**
     * Muestra la lista de enfermeros registrados en formato HTML.
     */
    private static void listarEnfermeros(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Lista de Enfermeros", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Enfermero> enfermeros = AdministracionPersonal.getEnfermeros();
        StringBuilder sb = new StringBuilder("<html><body style='font-family:Segoe UI;font-size:12px;'>");
        if (enfermeros.isEmpty()) {
            sb.append("No hay enfermeros registrados.");
        } else {
            for (Enfermero e : enfermeros) {
                sb.append("<b>ID:</b> ").append(e.getId())
                  .append("<br><b>Nombre:</b> ").append(e.getNombre())
                  .append("<br><b>Teléfono:</b> ").append(e.getTelefono())
                  .append("<br><b>Dirección:</b> ").append(e.getDireccion())
                  .append("<br><b>Correo:</b> ").append(e.getCorreo())
                  .append("<br><b>Tipo:</b> Enfermero")
                  .append("<br><b>Especialidad:</b> ").append(e.getEspecialidad())
                  .append("<br><b>Turno:</b> ").append(e.getTurno())
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

        // Cambia el estilo del scroll
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(55, 103, 229); // color barra
                this.trackColor = new Color(230, 230, 230); // color fondo
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

        // Opcional: también para el scroll horizontal
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

        JButton btnCerrar = InputsPersonalizados.crearBotonCerrarNegro("Cerrar");
        btnCerrar.addActionListener(_ -> {
            dialog.dispose();
            gestionarEnfermeros(parent);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        bottomPanel.add(btnCerrar);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}