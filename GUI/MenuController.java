package GUI;

import GUI.MenuAcciones.EnfermeriaController;
import GUI.MenuAcciones.MedicoController;
import GUI.MenuAcciones.PersonalController;
import GUI.MenuAcciones.HospitalizacionController;
import repository.Hospitalizacion;
import javax.swing.*;


/**
 * Controlador central del menú principal.
 * Gestiona la navegación entre los módulos principales de la aplicación.
 */
public class MenuController {
    // Instancia centralizada de Hospitalizacion (10 camas por defecto)
    private static final Hospitalizacion hospitalizacion = new Hospitalizacion(10);

    /**
     * Abre el módulo de gestión de personal.
     * @param parent Ventana principal como referencia.
     */
    public static void abrirJefeDePersonal(JFrame parent) {
        PersonalController.mostrarModulo(parent);
    }

    /**
     * Abre el módulo de enfermería, pasando la instancia de hospitalización.
     * @param parent Ventana principal como referencia.
     */
    public static void abrirEnfermeros(JFrame parent) {
        EnfermeriaController.mostrarModulo(parent, hospitalizacion);
    }

    /**
     * Abre el módulo de médicos.
     * @param parent Ventana principal como referencia.
     */
    public static void abrirMedicos(JFrame parent) {
        MedicoController.mostrarModulo(parent);
    }

    /**
     * Abre el módulo de hospitalización, pasando la instancia de hospitalización.
     * @param parent Ventana principal como referencia.
     */
    public static void abrirHospitalizacion(JFrame parent) {
        HospitalizacionController.mostrarModulo(parent, hospitalizacion);
    }
}
