package model;

import java.util.*;

public class MedicamentoRepository {
    private Map<Integer, Medicamento> datos = new HashMap<>();

    public MedicamentoRepository() {
        cargarMedicamentos();
    }

    private void cargarMedicamentos() {
        // Nivel 1 (Emergencia)
        datos.put(1,  new Medicamento(1,  "Epinefrina IV",                    "1 mg cada 3-5 min",    2500.0));
        datos.put(2,  new Medicamento(2,  "Sedación y analgésicos IV",        "Según protocolo",       2000.0));
        datos.put(3,  new Medicamento(3,  "Heparina y plasma fresco congelado","Dosis según peso",      1800.0));
        datos.put(4,  new Medicamento(4,  "Anestesia tópica y analgésicos","Aplicar cada 4h",         2200.0));
        datos.put(5,  new Medicamento(5,  "Bloqueo regional y antibióticos","Única dosis prequirúrgica", 3000.0));
        datos.put(6,  new Medicamento(6,  "Manitol 20%",                       "0.5 g/kg IV",           2700.0));
        datos.put(7,  new Medicamento(7,  "Adrenalina IM",                     "0.3 mg IM",             2600.0));
        datos.put(8,  new Medicamento(8,  "Sedación profunda y monitorización","Según protocolo",       2800.0));
        datos.put(9,  new Medicamento(9,  "Amiodarona IV","300 mg IV en bolo",                   3200.0));
        datos.put(10, new Medicamento(10, "Antídoto específico (Naloxona)",    "0.4 mg IV cada 2 min", 2400.0));

        // Nivel 2 (Urgente)
        datos.put(11, new Medicamento(11, "Antibiótico de amplio espectro",    "1 g cada 8h",           1500.0));
        datos.put(12, new Medicamento(12, "Amoxicilina 500 mg",                 "500 mg cada 8h",        1200.0));
        datos.put(13, new Medicamento(13, "Analgesia opiácea",                  "Según dolor",           1100.0));
        datos.put(14, new Medicamento(14, "Paracetamol 1 g",                    "1 g cada 6h",           1000.0));
        datos.put(15, new Medicamento(15, "Antiséptico y anestésico local",     "Aplicar en herida",      1300.0));
        datos.put(16, new Medicamento(16, "Diazepam IV",                        "5 mg lento IV",         1400.0));
        datos.put(17, new Medicamento(17, "Tamsulosina",                        "0.4 mg diaria",          1600.0));
        datos.put(18, new Medicamento(18, "Solución salina IV",                "500 mL cada 4h",        900.0));
        datos.put(19, new Medicamento(19, "Hemostático tópico",                "Aplicar según necesidad",1000.0));
        datos.put(20, new Medicamento(20, "Corticoide sistémico",              "Dosis única diaria",     950.0));

        // Nivel 3 (Prioritario)
        datos.put(21, new Medicamento(21, "Antipirético oral",                 "500 mg cada 8h",        200.0));
        datos.put(22, new Medicamento(22, "Ibuprofeno 400 mg",                 "400 mg cada 6h",        250.0));
        datos.put(23, new Medicamento(23, "Dextrometorfano jarabe",            "10 mL cada 8h",         300.0));
        datos.put(24, new Medicamento(24, "Sales de rehidratación oral",       "1 sobre cada 6h",       150.0));
        datos.put(25, new Medicamento(25, "Colirio lubrificante",               "1 gota cada 6h",        180.0));
        datos.put(26, new Medicamento(26, "Paracetamol 500 mg",                "500 mg cada 6h",        200.0));
        datos.put(27, new Medicamento(27, "Loratadina 10 mg",                  "10 mg diaria",          220.0));
        datos.put(28, new Medicamento(28, "Antiséptico tópico",                "Aplicar al corte",       100.0));
        datos.put(29, new Medicamento(29, "Espasmolítico oral",                "10 mg cada 8h",         300.0));
        datos.put(30, new Medicamento(30, "Melatonina 3 mg",                   "3 mg antes de dormir",  350.0));
    }

    public Optional<Medicamento> findByCodigoEnfermedad(int codigo) {
        return Optional.ofNullable(datos.get(codigo));
    }
}