package es.upsa.bbdd2.clases;

import es.upsa.bbdd2.clases.enums.Tipo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Menu {
    private String id;
    private String nombre;
    private LocalDate desde;
    private LocalDate hasta;
    private double precio;
    private Map<Tipo, List<Plato>> platosPorTipo;

    public Menu(String id, String nombre, LocalDate desde, LocalDate hasta, double precio, Map<Tipo, List<Plato>> platosPorTipo) {
        this.id = id;
        this.nombre = nombre;
        this.desde = desde;
        this.hasta = hasta;
        this.precio = precio;
        this.platosPorTipo = platosPorTipo;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getDesde() { return desde; }
    public LocalDate getHasta() { return hasta; }
    public double getPrecio() { return precio; }
    public Map<Tipo, List<Plato>> getPlatosPorTipo() { return platosPorTipo; }
}
