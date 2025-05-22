package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La clase TratamientoMedico representa la contraparte al Triage. 
 * Para cada enfermedad (identificada por un código numérico) se asigna un tratamiento y un costo.
 * Además, esta clase puede generar un recibo detallado para el paciente, sumando el costo de los tratamientos aplicados.
 */
public class TratamientoMedico {
    // Mapea el código de la enfermedad a la descripción del tratamiento
    private Map<Integer, String> catalogoTratamientos;
    // Mapea el código de la enfermedad a su costo
    private Map<Integer, Double> costosTratamientos;
    // Formateador para mostrar la fecha en el recibo
    private SimpleDateFormat sdf;

    /**
     * Constructor que inicializa los mapas y carga los tratamientos.
     */
    public TratamientoMedico() {
        catalogoTratamientos = new HashMap<>();
        costosTratamientos = new HashMap<>();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        cargarTratamientos();
    }

    /**
     * Carga tratamientos y sus costos para cada código de enfermedad.
     * Se organizan de forma similar a Triage (por niveles de urgencia).
     */
    private void cargarTratamientos() {
        // Nivel 1 (Emergencia): Tratamientos intensivos y de emergencia
        agregarTratamiento(1, "Reanimación cardiopulmonar y administración de fármacos", 2500.0);
        agregarTratamiento(2, "Soporte respiratorio intensivo y ventilación mecánica", 2000.0);
        agregarTratamiento(3, "Control de hemorragia y transfusión sanguínea", 1800.0);
        agregarTratamiento(4, "Atención de quemaduras con terapia especializada", 2200.0);
        agregarTratamiento(5, "Intervención quirúrgica urgente para fractura expuesta", 3000.0);
        agregarTratamiento(6, "Tratamiento neurológico de emergencia", 2700.0);
        agregarTratamiento(7, "Tratamiento de shock anafiláctico con adrenalina", 2600.0);
        agregarTratamiento(8, "Manejo intensivo de trauma craneoencefálico", 2800.0);
        agregarTratamiento(9, "Reanimación y asistencia cardiaca avanzada", 3200.0);
        agregarTratamiento(10, "Intervención envenenamiento severo con antídotos", 2400.0);

        // Nivel 2 (Urgente): Procedimientos quirúrgicos y manejo clínico intensivo
        agregarTratamiento(11, "Cirugía de apendicitis", 1500.0);
        agregarTratamiento(12, "Tratamiento antibiótico para neumonía", 1200.0);
        agregarTratamiento(13, "Manejo del dolor abdominal intenso", 1100.0);
        agregarTratamiento(14, "Control de fiebre alta persistente", 1000.0);
        agregarTratamiento(15, "Sutura de heridas profundas", 1300.0);
        agregarTratamiento(16, "Control de convulsiones con medicación", 1400.0);
        agregarTratamiento(17, "Intervención para cálculos renales", 1600.0);
        agregarTratamiento(18, "Rehidratación intravenosa intensiva", 900.0);
        agregarTratamiento(19, "Control de hemorragia moderada", 1000.0);
        agregarTratamiento(20, "Evaluación y tratamiento de dificultad para tragar", 950.0);

        // Nivel 3 (Prioritario): Tratamientos para condiciones leves
        agregarTratamiento(21, "Reposo, líquidos y antipiréticos para resfriado común", 200.0);
        agregarTratamiento(22, "Analgésicos y antiinflamatorios para dolor de garganta", 250.0);
        agregarTratamiento(23, "Supresores de la tos", 300.0);
        agregarTratamiento(24, "Rehidratación oral para diarrea leve", 150.0);
        agregarTratamiento(25, "Colirio y reposo para molestia ocular", 180.0);
        agregarTratamiento(26, "Analgésicos para dolor de cabeza leve", 200.0);
        agregarTratamiento(27, "Antihistamínicos para alergia leve", 220.0);
        agregarTratamiento(28, "Limpieza y vendaje para corte superficial", 100.0);
        agregarTratamiento(29, "Fisioterapia para contractura muscular", 300.0);
        agregarTratamiento(30, "Asesoría y medicación para falta de sueño", 350.0);
    }

    /**
     * Método auxiliar para agregar un tratamiento y su costo al catálogo.
     * @param codigo Código de la enfermedad.
     * @param tratamiento Descripción del tratamiento.
     * @param costo Costo del tratamiento.
     */
    private void agregarTratamiento(int codigo, String tratamiento, double costo) {
        catalogoTratamientos.put(codigo, tratamiento);
        costosTratamientos.put(codigo, costo);
    }

    /**
     * Muestra el catálogo completo de tratamientos y sus costos.
     */
    public void mostrarCatalogoTratamientos() {
        System.out.println("\n📋 Catálogo de Tratamientos Médicos:");
        List<Integer> claves = new ArrayList<>(catalogoTratamientos.keySet());
        Collections.sort(claves);
        for (Integer codigo : claves) {
            String tratamiento = catalogoTratamientos.get(codigo);
            double costo = costosTratamientos.get(codigo);
            System.out.println(codigo + ". " + tratamiento + " - Costo: $" + costo);
        }
    }

    /**
     * Genera y muestra un recibo detallado de tratamiento para un paciente.
     * Se asume que el paciente tiene en su lista de síntomas cadenas formateadas como:
     * "Nombre de la enfermedad (Código: X)"
     * donde X es el código numérico correspondiente.
     * @param paciente El paciente al que se le generará el recibo.
     */
    

public String generarInformeTratamientoString(Paciente paciente) {
    MedicamentoRepository medRepo = new MedicamentoRepository();
    List<String> sintomas = paciente.getSintomas();
    StringBuilder informe = new StringBuilder();
    informe.append("<html><body style='font-family:Segoe UI;font-size:12px;'>")
           .append("<div style='text-align:center;'><b style='font-size:15px;'>INFORME DE TRATAMIENTO</b></div><br>")
           .append("<b>Paciente:</b> ").append(paciente.getNombre()).append("<br>");
    // Mostrar el doctor que atendió al paciente, si existe
    if (paciente.getAtendidoPor() != null && !paciente.getAtendidoPor().isEmpty()) {
        informe.append("<b>Atendido por:</b> ").append(paciente.getAtendidoPor()).append("<br>");
    }
    informe.append("<b>Fecha:</b> ").append(sdf.format(new Date())).append("<br>")
           .append("<hr>")
           .append("<b>Detalle de Tratamientos y Medicamentos:</b><br>");

    for (String sintoma : sintomas) {
        int codigo = extraerCodigo(sintoma);
        if (codigo != -1) {
            String tratamiento = catalogoTratamientos.get(codigo);
            double costo = costosTratamientos.getOrDefault(codigo, 0.0);

            informe.append("&bull; <b>").append(sintoma).append("</b>")
                   .append(" &rarr; ").append(tratamiento)
                   .append(" : <b>$").append(String.format("%.2f", costo)).append("</b><br>");

            medRepo.findByCodigoEnfermedad(codigo).ifPresent(med -> {
                informe.append("&nbsp;&nbsp;&nbsp;&nbsp;<i>Medicamento:</i> <b>")
                       .append(med.getNombre())
                       .append("</b> (Aplicación: ")
                       .append(med.getDosis())
                       .append(")<br>");
            });
        } else {
            informe.append("&bull; <b>").append(sintoma)
                   .append("</b> : <span style='color:red;'>Código no identificado</span><br>");
        }
    }

    informe.append("<hr></body></html>");
    return informe.toString();
}

public String generarReciboString(Paciente paciente) {
    MedicamentoRepository medRepo = new MedicamentoRepository();
    List<String> sintomas = paciente.getSintomas();
    double total = 0;
    StringBuilder r = new StringBuilder();

    r.append("<html><body style='font-family:Segoe UI;font-size:12px;'>")
     .append("<div style='text-align:center;'><b style='font-size:15px;'>HOSPITAL UMB</b><br>")
     .append("<b>COMPROBANTE DE PAGO</b></div><hr>")
     .append("<b>Paciente:</b> ").append(paciente.getNombre()).append("<br>")
     .append("<b>Fecha:</b> ").append(sdf.format(new Date())).append("<br>")
     .append("<hr>")
     .append("<table width='100%' style='font-size:12px;'>")
     .append("<tr><th align='left'>COD</th><th align='left'>Descripción</th><th>Cant.</th><th>Subtotal</th></tr>");

    for (String sintoma : sintomas) {
        int codigo = extraerCodigo(sintoma);
        if (codigo != -1) {
            String desc = catalogoTratamientos.get(codigo);
            double costoT = costosTratamientos.getOrDefault(codigo, 0.0);
            total += costoT;

            r.append("<tr>")
             .append("<td>").append(codigo).append("</td>")
             .append("<td>").append(desc).append("</td>")
             .append("<td align='center'>1</td>")
             .append("<td align='right'><b>$").append(String.format("%.2f", costoT)).append("</b></td>")
             .append("</tr>");

            medRepo.findByCodigoEnfermedad(codigo).ifPresent(med -> {
                r.append("<tr><td></td><td colspan='3' style='font-size:11px;'>&nbsp;&nbsp;&nbsp;&rarr; <i>Medicamento:</i> <b>")
                 .append(med.getNombre()).append("</b> (").append(med.getDosis()).append(")</td></tr>");
            });
        }
    }

    r.append("</table>")
     .append("<hr>")
     .append("<div style='text-align:right;'><b>TOTAL: $").append(String.format("%.2f", total)).append("</b></div>")
     .append("<div style='text-align:center;'>Gracias por confiar en nosotros.<br>")
     .append("Tel: (01) 234-5678 &nbsp; www.hospitalumb.com</div>")
     .append("</body></html>");

    return r.toString();
}

    /**
     * Método auxiliar para extraer el código numérico de un síntoma.
     * Se espera que el síntoma tenga el formato "Nombre (Código: X)".
     * @param sintoma La cadena del síntoma.
     * @return El código extraído o -1 si no se pudo extraer.
     */
    private int extraerCodigo(String sintoma) {
        try {
            int indexInicio = sintoma.indexOf("(Código:");
            if (indexInicio == -1) return -1;
            int indexFin = sintoma.indexOf(")", indexInicio);
            String codigoStr = sintoma.substring(indexInicio + 8, indexFin).trim();
            return Integer.parseInt(codigoStr);
        } catch(Exception e) {
            return -1;
        }


    }  

    /**
     * Calcula el total a pagar por los tratamientos del paciente.
     * @param paciente El paciente a consultar.
     * @return El monto total.
     */
    public double calcularTotal(Paciente paciente) {
        double total = 0;
        if (paciente.getSintomas() != null) {
            for (String sintoma : paciente.getSintomas()) {
                int codigo = extraerCodigo(sintoma);
                if (codigo != -1) {
                    total += costosTratamientos.getOrDefault(codigo, 0.0);
                }
            }
        }
        return total;
    }
}