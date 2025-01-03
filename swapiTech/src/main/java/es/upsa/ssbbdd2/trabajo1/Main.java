package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.domain.entities.Peliculas;
import es.upsa.ssbbdd2.trabajo1.domain.entities.Personajes;
import es.upsa.ssbbdd2.trabajo1.swapi.StarWarsApi;
import es.upsa.ssbbdd2.trabajo1.swapi.impl.StarWarsApiImpl;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (StarWarsApi swapi = new StarWarsApiImpl()) {
            generateFilms(swapi);
            //generatePeople(swapi);
        }
    }

    static void generateFilms(StarWarsApi swapi) throws Exception {
        JsonObject jsonObject = swapi.request(StarWarsApi.FILMS_URL);
        System.out.println(jsonObject);
        JsonArray filmsArray = jsonObject.getJsonArray("result");

        List<Peliculas> peliculasList = new ArrayList<>();
        for (JsonObject filmObj : filmsArray.getValuesAs(JsonObject.class)) {
            JsonObject properties = filmObj.getJsonObject("properties");
            String title = properties.getString("title");
            int episode = properties.getInt("episode_id");
            String url = properties.getString("url");
            JsonArray personajesList = properties.getJsonArray("characters");
            ArrayList<Personajes> personajes = new ArrayList<>();
            for (JsonString urlJson : personajesList.getValuesAs(JsonString.class))
            {
                JsonObject personajeJson = swapi.generateCharacter(urlJson.toString());
                System.out.println("PERSONAJE JSON: " + personajeJson);
                /*
               String nombre = personajeJson.getString("nombre");
               String altura = personajeJson.getString("altura");
               String url1 = personajeJson.getString("url");
               Personajes personajes1 = Personajes.builder()
                       .withNombre(nombre)
                       .withAltura(altura)
                       .withUrl(url1)
                       .build();
               personajes.add(personajes1);
                 */

            }

            Peliculas pelicula = Peliculas.builder()
                    .withTitulo(title)
                    .withEpisodio(episode)
                    .withUrl(url)
                    .withPersonajes(personajes)
                    .build();
            peliculasList.add(pelicula);
        }

        writeJsonToFile(peliculasList, "films.json");
    }

    private static <T> void writeJsonToFile(List<T> data, String fileName) {
        JsonbConfig config = new JsonbConfig().withFormatting(true);
        try (FileWriter writer = new FileWriter(fileName);
             Jsonb jsonb = JsonbBuilder.create()) {
            jsonb.toJson(data, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void generatePeople(StarWarsApi swapi) throws Exception {
        JsonObject jsonObject = swapi.request( StarWarsApi.PEOPLE_URL );
        System.out.println(jsonObject);
    }

}