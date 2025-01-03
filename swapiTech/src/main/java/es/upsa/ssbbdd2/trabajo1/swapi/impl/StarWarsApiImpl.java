package es.upsa.ssbbdd2.trabajo1.swapi.impl;

import es.upsa.ssbbdd2.trabajo1.swapi.StarWarsApi;
import es.upsa.ssbbdd2.trabajo1.domain.exceptions.StarWarsApiException;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StarWarsApiImpl implements StarWarsApi
{
    HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public JsonObject request(String url) throws StarWarsApiException
    {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .uri( URI.create( url ) )
                                             .header("Accept", "application/json")
                                             .GET()
                                             .build();
        try
        {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String json = httpResponse.body();
            return Json.createReader(new StringReader(json)).readObject();
        } catch (IOException | InterruptedException  exception)
          {
              throw new StarWarsApiException(exception);
          }
    }

    @Override
    public JsonObject generateCharacter(String url) throws StarWarsApiException {
        return request(url);
    }

    @Override
    public void close() throws Exception
    {
        if (httpClient != null)
        {
            httpClient.close();
            httpClient = null;
        }

    }
}
