package model;

public class Medicamento {
    private int codigoEnfermedad;
    private String nombre;
    private String dosis;      // p.ej. "500 mg cada 8 horas"
    private double costoUnitario;

    public Medicamento(int codigoEnfermedad, String nombre, String dosis, double costoUnitario) {
        this.codigoEnfermedad = codigoEnfermedad;
        this.nombre = nombre;
        this.dosis = dosis;
        this.costoUnitario = costoUnitario;
    }

    public int getCodigoEnfermedad() { return codigoEnfermedad; }
    public String getNombre() { return nombre; }
    public String getDosis() { return dosis; }
    public double getCostoUnitario() { return costoUnitario; }

    @Override
    public String toString() {
        return String.format("%s - %s (%.2f)", nombre, dosis, costoUnitario);
    }
}