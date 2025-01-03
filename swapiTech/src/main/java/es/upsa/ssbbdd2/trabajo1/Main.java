package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.swapi.StarWarsApi;
import es.upsa.ssbbdd2.trabajo1.swapi.impl.StarWarsApiImpl;

// COMENTARIO

public class Main
{
    public static void main(String[] args) throws Exception
    {
        try ( StarWarsApi swapi = new StarWarsApiImpl() )
        {
           generateFilms(swapi);
           generatePeople(swapi);
        }
    }

    static void generateFilms(StarWarsApi swapi) throws Exception
    {
        // swapi.request( StarWarsApi.FILMS_URL )

    }

    static void generatePeople(StarWarsApi swapi) throws Exception
    {
        // swapi.request( StarWarsApi.PEOPLE_URL )

    }

}
