package com.nocountry.s12.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="listaReproduccion")

public class ListaReproduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "id_artista", referencedColumnName = "id", nullable = false)
    private Artista artista;

    //tipos de relaciones

    @ManyToMany(mappedBy = "listaReproduccion")
    private List<Usuario> usuarios;  //Relación con usuarios

    // @JoinTable es para especificar el nombre de la tabla intermedia que maneja la relación
    // entre las listas de reproducción y las canciones.
    @ManyToMany //Relación con canciones
    @JoinTable(
            name = "listaReproduccion_cancion",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    private List<Cancion> canciones;


}
