package model;

import GUI.InputsPersonalizados;
import java.awt.*;

public class SistemaPagos {

    public static boolean pagarEfectivo(Component parent, double monto) {
        String input = InputsPersonalizados.mostrarInputPersonalizado(parent, "Pago en Efectivo", "Entregar $" + monto);
        if (input == null) return false;
        try {
            double entregado = Double.parseDouble(input);
            if (entregado < monto) {
                InputsPersonalizados.mostrarError(parent, "El monto entregado es insuficiente.");
                return false;
            }
            InputsPersonalizados.mostrarMensajePersonalizado(parent, "Pago realizado. Cambio: $" + String.format("%.2f", (entregado - monto)));
            return true;
        } catch (NumberFormatException e) {
            InputsPersonalizados.mostrarError(parent, "Ingrese un valor numérico válido.");
            return false;
        }
    }

    public static boolean pagarTarjeta(Component parent, double monto) {
        String[] tipos = {"Bancolombia", "Banco de Bogota", "BBVA", "Otra"};
        String tipo = InputsPersonalizados.mostrarSeleccionPersonalizada(parent, "Tipo de Tarjeta", "Seleccione el tipo de tarjeta:", tipos);
        if (tipo == null) return false;
        if ("Otra".equals(tipo)) {
            String otro = InputsPersonalizados.mostrarInputPersonalizado(parent, "Tipo de Tarjeta", "Especifique el banco o tipo de tarjeta:");
            if (otro == null || otro.trim().isEmpty()) return false;
            tipo = otro.trim();
        }
        String input = InputsPersonalizados.mostrarInputPersonalizado(parent, "Pago con Tarjeta", "Entregar $" + monto + " con tarjeta " + tipo);
        if (input == null) return false;
        try {
            double entregado = Double.parseDouble(input);
            if (entregado < monto) {
                InputsPersonalizados.mostrarError(parent, "El monto es insuficiente.");
                return false;
            }
            InputsPersonalizados.mostrarMensajePersonalizado(parent, "Pago realizado con " + tipo + ". Cambio: $" + String.format("%.2f", (entregado - monto)));
            return true;
        } catch (NumberFormatException e) {
            InputsPersonalizados.mostrarError(parent, "Ingrese un valor numérico válido.");
            return false;
        }
    }

    public static boolean pagarSeguro(Component parent, double monto) {
        String[] companias = {"AXA", "Compensar EPS", "SaludTotal EPS", "Colsanitas", "Otra"};
        String compania = InputsPersonalizados.mostrarSeleccionPersonalizada(parent, "Seguro Médico", "Seleccione su compañía de seguros:", companias);
        if (compania == null) return false;
        if ("Otra".equals(compania)) {
            String otro = InputsPersonalizados.mostrarInputPersonalizado(parent, "Seguro Médico", "Especifique la compañía de seguros:");
            if (otro == null || otro.trim().isEmpty()) return false;
            compania = otro.trim();
        }
        InputsPersonalizados.mostrarMensajePersonalizado(parent, "Excelente, la compañía <b>" + compania + "</b> cubrirá los costos.");
        return true;
    }
}
