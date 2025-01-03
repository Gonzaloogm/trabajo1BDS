package es.upsa.bbdd2;

import es.upsa.bbdd2.clases.*;
import es.upsa.bbdd2.clases.enums.Tipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

class DaoImpl implements Dao {

    private Connection connection;

    public DaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Plato insertPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws Exception {
        String insertPlatoQuery = "INSERT INTO platos (id, nombre, descripcion, precio, tipo) VALUES (nextval('SEQ_PLATOS'), ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(insertPlatoQuery)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setString(4, tipo.name());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String platoId = rs.getString("id");
                List<Compuesto> componentes = new ArrayList<>();
                for (CantidadIngrediente ci : cantidadesIngredientes) {
                    Ingrediente ingrediente = ensureIngrediente(ci.getNombreIngrediente());
                    String insertCompuestoQuery = "INSERT INTO compuestos (id_plato, id_ingrediente, cantidad, unidad) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement compStmt = connection.prepareStatement(insertCompuestoQuery)) {
                        compStmt.setString(1, platoId);
                        compStmt.setString(2, ingrediente.getId());
                        compStmt.setInt(3, ci.getCantidad());
                        compStmt.setString(4, ci.getUnidad().name());
                        compStmt.executeUpdate();
                    }
                    componentes.add(new Compuesto(ingrediente, ci.getCantidad(), ci.getUnidad()));
                }
                return new Plato(platoId, nombre, descripcion, precio, tipo, componentes);
            }
        }
        throw new Exception("Failed to insert Plato.");
    }

    @Override
    public Menu insertMenu(String nombre, LocalDate desde, LocalDate hasta, List<String> platos) throws Exception {
        String insertMenuQuery = "INSERT INTO menus (id, nombre, desde, hasta, precio) VALUES (nextval('SEQ_MENUS'), ?, ?, ?, ?) RETURNING id";
        double precioTotal = 0;
        Map<Tipo, List<Plato>> platosPorTipo = new HashMap<>();

        for (String platoNombre : platos) {
            Optional<Plato> platoOpt = fetchPlatoByName(platoNombre);
            if (platoOpt.isEmpty()) {
                throw new Exception("Plato not found: " + platoNombre);
            }
            Plato plato = platoOpt.get();
            precioTotal += plato.getPrecio();
            platosPorTipo.computeIfAbsent(plato.getTipo(), k -> new ArrayList<>()).add(plato);
        }

        double precioFinal = precioTotal * 0.85;

        try (PreparedStatement stmt = connection.prepareStatement(insertMenuQuery)) {
            stmt.setString(1, nombre);
            stmt.setObject(2, desde);
            stmt.setObject(3, hasta);
            stmt.setDouble(4, precioFinal);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String menuId = rs.getString("id");
                return new Menu(menuId, nombre, desde, hasta, precioFinal, platosPorTipo);
            }
        }
        throw new Exception("Failed to insert Menu.");
    }

    @Override
    public List<Menu> buscarMenu(LocalDate fecha) throws Exception {
        String searchMenuQuery = "SELECT * FROM menus WHERE ? BETWEEN desde AND hasta";
        String fetchPlatosQuery = "SELECT p.id, p.nombre, p.descripcion, p.precio, p.tipo FROM platos p JOIN menu_platos mp ON p.id = mp.id_plato WHERE mp.id_menu = ?";
        List<Menu> menus = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(searchMenuQuery)) {
            stmt.setObject(1, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                LocalDate desde = rs.getObject("desde", LocalDate.class);
                LocalDate hasta = rs.getObject("hasta", LocalDate.class);
                double precio = rs.getDouble("precio");

                Map<Tipo, List<Plato>> platosPorTipo = new HashMap<>();

                try (PreparedStatement platosStmt = connection.prepareStatement(fetchPlatosQuery)) {
                    platosStmt.setString(1, id);
                    ResultSet platosRs = platosStmt.executeQuery();
                    while (platosRs.next()) {
                        String platoId = platosRs.getString("id");
                        String platoNombre = platosRs.getString("nombre");
                        String descripcion = platosRs.getString("descripcion");
                        double platoPrecio = platosRs.getDouble("precio");
                        Tipo tipo = Tipo.valueOf(platosRs.getString("tipo"));

                        Plato plato = new Plato(platoId, platoNombre, descripcion, platoPrecio, tipo, null);
                        platosPorTipo.computeIfAbsent(tipo, k -> new ArrayList<>()).add(plato);
                    }
                }

                menus.add(new Menu(id, nombre, desde, hasta, precio, platosPorTipo));
            }
        }
        return menus;
    }

    @Override
    public List<Plato> buscarPlato(Tipo tipo, List<String> ingredientes) throws Exception {
        String searchPlatoQuery = "SELECT * FROM platos WHERE tipo = ? AND id NOT IN ( SELECT DISTINCT id_plato FROM compuestos c JOIN ingredientes i ON c.id_ingrediente = i.id WHERE i.nombre = ANY(?))";
        List<Plato> platos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(searchPlatoQuery)) {
            stmt.setString(1, tipo.name());
            stmt.setArray(2, connection.createArrayOf("VARCHAR", ingredientes.toArray()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                platos.add(new Plato(id, nombre, descripcion, precio, tipo, null));
            }
        }
        return platos;
    }

    @Override
    public void subirPrecioPlato(String nombre, double porcentaje) throws Exception {
        String updatePlatoQuery = "UPDATE platos SET precio = precio * (1 + ?) WHERE nombre = ?";
        String updateMenuQuery = "UPDATE menus SET precio = ( SELECT SUM(p.precio) * 0.85 FROM platos p JOIN menu_platos mp ON p.id = mp.id_plato WHERE mp.id_menu = menus.id)";

        try (PreparedStatement stmt = connection.prepareStatement(updatePlatoQuery)) {
            stmt.setDouble(1, porcentaje);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
        }

        try (PreparedStatement updateMenuStmt = connection.prepareStatement(updateMenuQuery)) {
            updateMenuStmt.executeUpdate();
        }
    }

    @Override
    public Ingrediente ensureIngrediente(String nombre) throws Exception {
        String checkIngredienteQuery = "SELECT id FROM ingredientes WHERE nombre = ?";
        String insertIngredienteQuery = "INSERT INTO ingredientes (id, nombre) VALUES (nextval('SEQ_INGREDIENTES'), ?) RETURNING id";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkIngredienteQuery)) {
            checkStmt.setString(1, nombre);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return new Ingrediente(rs.getString("id"), nombre);
            }
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(insertIngredienteQuery)) {
            insertStmt.setString(1, nombre);
            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                return new Ingrediente(rs.getString("id"), nombre);
            }
        }
        throw new Exception("Failed to ensure Ingrediente.");
    }

    @Override
    public Optional<Plato> fetchPlatoByName(String nombre) throws Exception {
        String fetchPlatoQuery = "SELECT * FROM platos WHERE nombre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(fetchPlatoQuery)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                Tipo tipo = Tipo.valueOf(rs.getString("tipo"));
                return Optional.of(new Plato(id, nombre, descripcion, precio, tipo, null));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Menu> fetchMenuByName(String nombre) throws Exception {
        String fetchMenuQuery = "SELECT * FROM menus WHERE nombre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(fetchMenuQuery)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                LocalDate desde = rs.getObject("desde", LocalDate.class);
                LocalDate hasta = rs.getObject("hasta", LocalDate.class);
                double precio = rs.getDouble("precio");
                return Optional.of(new Menu(id, nombre, desde, hasta, precio, null));
            }
        }
        return Optional.empty();
    }
}
