package es.upsa.ssbbdd2.trabajo1.domain.entities;

import jakarta.json.JsonArray;
import lombok.*;

import java.util.ArrayList;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Peliculas
{
    private String titulo;
    private int episodio;
    private String url;
    private ArrayList<Personajes> personajes;
}
