package es.upsa.bbdd2.clases;

import es.upsa.bbdd2.clases.enums.UnidadMedida;

public class Compuesto {
    private Ingrediente ingrediente;
    private int cantidad;
    private UnidadMedida unidad;

    public Compuesto(Ingrediente ingrediente, int cantidad, UnidadMedida unidad) {
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public Ingrediente getIngrediente() { return ingrediente; }
    public int getCantidad() { return cantidad; }
    public UnidadMedida getUnidad() { return unidad; }
}
