package es.upsa.bbdd2.clases;

import es.upsa.bbdd2.clases.enums.Tipo;

import java.util.List;

public class Plato {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Tipo tipo;
    private List<Compuesto> componentes;

    public Plato(String id, String nombre, String descripcion, double precio, Tipo tipo, List<Compuesto> componentes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.componentes = componentes;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public Tipo getTipo() { return tipo; }
    public List<Compuesto> getComponentes() { return componentes; }
}
