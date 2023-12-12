package com.nocountry.s12.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="publicaciones")

public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @OneToOne
    @JoinColumn(name = "id_imagen")
    private Imagen imagen;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;


    /*
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Reaccion> reacciones = new ArrayList<>();
    */

    /*
    @Column(name = "me_gusta")
    private Integer meGusta = 0;
     */

}
