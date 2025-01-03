package es.upsa.bbdd2.clases;

public class Ingrediente {
    private String id;
    private String nombre;

    public Ingrediente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
}
