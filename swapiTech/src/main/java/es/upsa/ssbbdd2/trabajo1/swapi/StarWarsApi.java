package es.upsa.ssbbdd2.trabajo1.swapi;

import es.upsa.ssbbdd2.trabajo1.domain.exceptions.StarWarsApiException;
import jakarta.json.JsonObject;

public interface StarWarsApi extends AutoCloseable
{
    public static final String FILMS_URL = "https://swapi.tech/api/films";
    public static final String PEOPLE_URL = "https://swapi.tech/api/people";

    public JsonObject request(String url) throws StarWarsApiException;
    public JsonObject generateCharacter(String url) throws StarWarsApiException;
}
