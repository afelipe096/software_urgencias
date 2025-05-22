package GUI;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Sistema de Triage - Hospital UMB");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configuración global de fuentes para etiquetas y botones
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Panel lateral de presentación
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(new Color(33, 43, 54));
        menuLateral.setPreferredSize(new Dimension(230, 0));

        // Logo del hospital
        JLabel logo = new JLabel("🏥 Hospital UMB", JLabel.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Información de bienvenida
        JTextArea info = new JTextArea(
            "Sistema de Triage\n\n" +
            "Brindamos atención inmediata y precisa según el nivel\n" +
            "de urgencia del paciente. \n\n" +
            "- Evaluación eficiente\n" +
            "- Tratamiento adecuado\n" +
            "- Alta calidad humana "
        );
        info.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        info.setForeground(Color.WHITE);
        info.setBackground(new Color(33, 43, 54));
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Alineación y agregado de componentes al panel lateral
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLateral.add(logo);
        menuLateral.add(info);
        panel.add(menuLateral, BorderLayout.WEST);

        // Panel central con tarjetas de acceso rápido (más pequeñas)
        JPanel contenido = new JPanel(new GridLayout(2, 2, 10, 10)); // Menos espacio entre tarjetas
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Menos margen
        contenido.setBackground(Color.WHITE);

        String[] opciones = {"Jefe de personal", "Asignar Triage", "Evalúa Triage", "Hospitalización"};
        Runnable[] acciones = {
            () -> MenuController.abrirJefeDePersonal(this),
            () -> MenuController.abrirEnfermeros(this),
            () -> MenuController.abrirMedicos(this),
            () -> MenuController.abrirHospitalizacion(this)
        };

        for (int i = 0; i < opciones.length; i++) {
            JPanel tarjeta = crearTarjeta(opciones[i], acciones[i]);
            tarjeta.setPreferredSize(new Dimension(200, 120)); // Tamaño más pequeño
            contenido.add(tarjeta);
        }

        panel.add(contenido, BorderLayout.CENTER);

        // Elimina el panel y botón de archivos, así como el menú emergente
        // Ya no se agrega nada en BorderLayout.SOUTH

        this.getContentPane().add(panel);
    }

    /**
     * Crea una tarjeta con un título y una acción asociada al botón "Abrir".
     * @param texto Texto a mostrar en la tarjeta.
     * @param accion Acción a ejecutar al presionar el botón.
     * @return JPanel representando la tarjeta.
     */
    private JPanel crearTarjeta(String texto, Runnable accion) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lbl = new JLabel(texto, JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Usar el botón personalizado para mantener la estética
        JButton btn = InputsPersonalizados.crearBoton("Abrir");
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(_ -> accion.run());

        tarjeta.add(lbl, BorderLayout.CENTER);
        tarjeta.add(btn, BorderLayout.SOUTH);
        return tarjeta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}