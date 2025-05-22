package utils;

public class Validadores {

    // Validador: ¿es un correo válido de Gmail?
    public static boolean esCorreoGmailValido(String correo) {
        if (correo == null) return false;
        return correo.matches("^[\\w.+\\-]+@gmail\\.com$");
    }

    // Validador: ¿es un nombre solo letras (puede incluir espacios, tildes y puntos)?
    public static boolean esNombreValido(String nombre) {
        if (nombre == null) return false;
        return nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ. ]+$");
    }

    // Validador: ¿es un número de teléfono válido? (10 dígitos, solo números)
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null) return false;
        return telefono.matches("^\\d{10}$");
    }
}