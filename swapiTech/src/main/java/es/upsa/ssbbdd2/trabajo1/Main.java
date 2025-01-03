package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.swapi.StarWarsApi;
import es.upsa.ssbbdd2.trabajo1.swapi.impl.StarWarsApiImpl;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        try (StarWarsApi swapi = new StarWarsApiImpl()) {
            generateFilms(swapi);
            generatePeople(swapi);
        }
    }

    static void generateFilms(StarWarsApi swapi) throws Exception {
        JsonObject jsonFilm = swapi.request( StarWarsApi.FILMS_URL );


    }

    static void generatePeople(StarWarsApi swapi) throws Exception {
        JsonObject jsonObject = swapi.request( StarWarsApi.PEOPLE_URL );
        System.out.println(jsonObject);
    }

}