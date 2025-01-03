package es.upsa.ssbbdd2.trabajo1.domain.entities;

import lombok.*;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Peliculas
{
    private String titulo;
    private int episodio;
    private String url;
}
