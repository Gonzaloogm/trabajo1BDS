package es.upsa.ssbbdd2.trabajo1.domain.entities;

import lombok.*;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Personajes
{
    private String nombre;
    private String altura;
    private String url;
    private List<String> genero;
}
