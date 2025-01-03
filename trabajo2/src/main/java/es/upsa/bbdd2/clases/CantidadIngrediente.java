package es.upsa.bbdd2.clases;

import es.upsa.bbdd2.clases.enums.UnidadMedida;

public class CantidadIngrediente {
    private String nombreIngrediente;
    private int cantidad;
    private UnidadMedida unidad;

    public CantidadIngrediente(String nombreIngrediente, int cantidad, UnidadMedida unidad) {
        this.nombreIngrediente = nombreIngrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getNombreIngrediente() { return nombreIngrediente; }
    public int getCantidad() { return cantidad; }
    public UnidadMedida getUnidad() { return unidad; }
}
