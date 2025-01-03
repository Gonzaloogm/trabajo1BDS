package es.upsa.bbdd2;

import es.upsa.bbdd2.clases.CantidadIngrediente;
import es.upsa.bbdd2.clases.Ingrediente;
import es.upsa.bbdd2.clases.Menu;
import es.upsa.bbdd2.clases.Plato;
import es.upsa.bbdd2.clases.enums.Tipo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Dao {

    Plato insertPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws Exception;

    Plato insertPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws Exception;

    Plato insertPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws Exception;

    Menu insertMenu(String nombre, LocalDate desde, LocalDate hasta, List<String> platos) throws Exception;

    List<Menu> buscarMenu(LocalDate fecha) throws Exception;

    List<Plato> buscarPlato(Tipo tipo, List<String> ingredientes) throws Exception;

    void subirPrecioPlato(String nombre, double porcentaje) throws Exception;

    Ingrediente ensureIngrediente(String nombre) throws Exception;

    Optional<Plato> fetchPlatoByName(String nombre) throws Exception;

    Optional<Menu> fetchMenuByName(String nombre) throws Exception;
}

