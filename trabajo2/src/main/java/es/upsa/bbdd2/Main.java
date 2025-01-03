package es.upsa.bbdd2;

import es.upsa.bbdd2.clases.CantidadIngrediente;
import es.upsa.bbdd2.clases.Menu;
import es.upsa.bbdd2.clases.Plato;
import es.upsa.bbdd2.clases.enums.Tipo;
import es.upsa.bbdd2.clases.enums.UnidadMedida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/restaurant", "user", "password");
            Dao dao = new DaoImpl(connection);

            // Create Ingredients
            List<CantidadIngrediente> ingredientes = Arrays.asList(
                    new CantidadIngrediente("Tomate", 100, UnidadMedida.GRAMOS),
                    new CantidadIngrediente("Queso", 200, UnidadMedida.GRAMOS)
            );

            // Create Platos
            Plato plato1 = dao.insertPlato("Ensalada Caprese", "Fresca ensalada italiana", 8.50, Tipo.ENTRANTE, ingredientes);
            Plato plato2 = dao.insertPlato("Filete de Res", "Filete a la parrilla", 15.00, Tipo.PRINCIPAL, ingredientes);

            // Create Menus
            Menu menu1 = dao.insertMenu("Menú Ejecutivo", LocalDate.now(), LocalDate.now().plusDays(10), Arrays.asList("Ensalada Caprese", "Filete de Res"));

            // Search Menus by Date
            List<Menu> menus = dao.buscarMenu(LocalDate.now());
            for (Menu menu : menus) {
                System.out.println("Menú: " + menu.getNombre());
                System.out.println("Precio: " + menu.getPrecio());
                System.out.println("Platos: " + menu.getPlatosPorTipo());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
