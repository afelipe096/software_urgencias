package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class InputsPersonalizados {

    /**
     * Crea un botón de cierre con fondo blanco y borde negro.
     */
    public static JButton crearBotonCerrarNegro(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setMargin(new Insets(8, 18, 8, 18));
        return btn;
    }

    /**
     * Crea un botón principal azul con texto blanco.
     */
    public static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(55, 103, 229));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setMaximumSize(new Dimension(220, 36));
        btn.setPreferredSize(new Dimension(220, 36));
        return btn;
    }

    /**
     * Crea un botón de cierre con fondo rojo.
     */
    public static JButton crearBotonCerrar(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(220, 53, 69));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true));
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setMargin(new Insets(8, 18, 8, 18));
        return btn;
    }

    /**
     * Crea una barra de desplazamiento personalizada para scrolls.
     */
    public static JScrollBar crearScrollBarPersonalizado() {
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setUI(new BasicScrollBarUI() {
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
        return scrollBar;
    }

    /**
     * Muestra un diálogo de mensaje personalizado con botón OK.
     * @param parent Componente padre para centrar el diálogo.
     * @param mensaje Mensaje HTML a mostrar.
     */
    public static void mostrarMensajePersonalizado(Component parent, String mensaje) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            "Información",
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("<html><body style='font-family:Segoe UI;font-size:15px;'><b style='color:#357a38;'>¡Éxito!</b><br><br>" + mensaje + "</body></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnOk = crearBoton("OK");
        btnOk.setPreferredSize(new Dimension(100, 36));
        btnOk.addActionListener(_ -> dialog.dispose());
        btnPanel.add(btnOk);

        panel.add(label);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 180));
        dialog.setVisible(true);
    }

    /**
     * Muestra un diálogo de error personalizado con botón Cerrar.
     * @param parent Componente padre para centrar el diálogo.
     * @param mensaje Mensaje HTML a mostrar.
     */
    public static void mostrarError(Component parent, String mensaje) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            "Error",
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("<html><body style='font-family:Segoe UI;font-size:15px;'><b style='color:#d32f2f;'>¡Error!</b><br><br>" + mensaje + "</body></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnCerrar = crearBotonCerrar("Cerrar");
        btnCerrar.addActionListener(_ -> dialog.dispose());
        btnPanel.add(btnCerrar);

        panel.add(label);
        panel.add(Box.createVerticalStrut(32));
        panel.add(btnPanel);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 200));
        dialog.setVisible(true);
    }

    /**
     * Muestra un input personalizado para capturar texto del usuario.
     * @param parent Componente padre para centrar el diálogo.
     * @param titulo Título del diálogo.
     * @param mensaje Mensaje a mostrar.
     * @return El texto ingresado o null si se cancela.
     */
    public static String mostrarInputPersonalizado(Component parent, String titulo, String mensaje) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            titulo,
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        textField.setBackground(new Color(245, 245, 245));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnAceptar = crearBoton("Aceptar");
        JButton btnCancelar = crearBotonCerrar("Cancelar");

        btnPanel.add(btnAceptar);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(btnCancelar);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        final String[] resultado = new String[1];
        btnAceptar.addActionListener(_ -> {
            resultado[0] = textField.getText();
            dialog.dispose();
        });
        btnCancelar.addActionListener(_-> {
            resultado[0] = null;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 200));
        dialog.setVisible(true);

        return resultado[0];
    }

    /**
     * Muestra un diálogo de confirmación personalizado con botones Sí y No.
     * @param parent Componente padre para centrar el diálogo.
     * @param mensaje Mensaje a mostrar.
     * @return true si el usuario acepta, false si cancela.
     */
    public static boolean mostrarConfirmacionPersonalizada(Component parent, String mensaje) {
        final boolean[] respuesta = {false};
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            "Confirmar",
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("<html><body style='font-family:Segoe UI;font-size:15px;'>" + mensaje + "</body></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnSi = crearBoton("Sí");
        JButton btnNo = crearBotonCerrar("No");

        btnPanel.add(btnSi);
        btnPanel.add(btnNo);

        panel.add(label);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        btnSi.addActionListener(_ -> {
            respuesta[0] = true;
            dialog.dispose();
        });
        btnNo.addActionListener(_ -> {
            respuesta[0] = false;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 170));
        dialog.setVisible(true);

        return respuesta[0];
    }

    /**
     * Muestra un diálogo con un combo para seleccionar una opción.
     * @param parent Componente padre para centrar el diálogo.
     * @param titulo Título del diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param opciones Opciones a mostrar en el combo.
     * @return La opción seleccionada o null si se cancela.
     */
    public static String mostrarSeleccionPersonalizada(Component parent, String titulo, String mensaje, String[] opciones) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            titulo,
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        combo.setBackground(new Color(245, 245, 245));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnOk = crearBoton("OK");
        JButton btnCancel = crearBotonCerrar("Cancelar");

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(combo);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        final String[] resultado = new String[1];
        btnOk.addActionListener(_ -> {
            resultado[0] = (String) combo.getSelectedItem();
            dialog.dispose();
        });
        btnCancel.addActionListener(_ -> {
            resultado[0] = null;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 180));
        dialog.setVisible(true);

        return resultado[0];
    }

    /**
     * Muestra un diálogo con una lista para seleccionar una opción.
     * @param parent Componente padre para centrar el diálogo.
     * @param titulo Título del diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param opciones Opciones a mostrar en la lista.
     * @return La opción seleccionada o null si se cancela.
     */
    public static String mostrarSeleccionListaPersonalizada(Component parent, String titulo, String mensaje, String[] opciones) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            titulo,
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JList<String> list = new JList<>(opciones);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(320, 160));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Scroll personalizado
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(55, 103, 229);
                this.trackColor = new Color(230, 230, 230);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnOk = crearBoton("OK");
        JButton btnCancel = crearBotonCerrar("Cancelar");

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(scroll);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        final String[] resultado = new String[1];
        btnOk.addActionListener(_ -> {
            resultado[0] = list.getSelectedValue();
            dialog.dispose();
        });
        btnCancel.addActionListener(_ -> {
            resultado[0] = null;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 260));
        dialog.setVisible(true);

        return resultado[0];
    }

    /**
     * Muestra un diálogo con un combo personalizado para seleccionar una opción.
     * @param parent Componente padre para centrar el diálogo.
     * @param titulo Título del diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param opciones Opciones a mostrar en el combo.
     * @return La opción seleccionada o null si se cancela.
     */
    public static String mostrarComboPersonalizado(Component parent, String titulo, String mensaje, String[] opciones) {
        JDialog dialog = new JDialog(
            parent instanceof Frame ? (Frame) parent : null,
            titulo,
            true
        );
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        combo.setBackground(new Color(245, 245, 245));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton btnOk = crearBoton("OK");
        JButton btnCancel = crearBotonCerrar("Cancelar");

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);

        panel.add(label);
        panel.add(Box.createVerticalStrut(12));
        panel.add(combo);
        panel.add(Box.createVerticalStrut(24));
        panel.add(btnPanel);

        final String[] resultado = new String[1];
        btnOk.addActionListener(_ -> {
            resultado[0] = (String) combo.getSelectedItem();
            dialog.dispose();
        });
        btnCancel.addActionListener(_ -> {
            resultado[0] = null;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(370, 180));
        dialog.setVisible(true);

        return resultado[0];
    }

    /**
     * Crea un campo de texto personalizado y lo agrega a un panel.
     * @param panel Panel al que se agrega el campo.
     * @param label Etiqueta del campo.
     * @return El JTextField creado.
     */
    public static JTextField crearCampo(JPanel panel, String label) {
        return crearCampo(panel, label, "");
    }

    /**
     * Crea un campo de texto personalizado con valor inicial y lo agrega a un panel.
     * @param panel Panel al que se agrega el campo.
     * @param label Etiqueta del campo.
     * @param valorInicial Valor inicial del campo.
     * @return El JTextField creado.
     */
    public static JTextField crearCampo(JPanel panel, String label, String valorInicial) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(110, 28));
        JTextField field = new JTextField(valorInicial);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        field.setBackground(new Color(245, 245, 245));
        field.setForeground(new Color(33, 43, 54));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        fieldPanel.add(lbl, BorderLayout.WEST);
        fieldPanel.add(field, BorderLayout.CENTER);
        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(8));
        return field;
    }
}