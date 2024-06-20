package org.cosm.curie.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Localizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocalizacion")
    private Integer idLocalizacion;

    @Column(name = "nombre")
    private String nombre;


    @OneToMany(mappedBy = "localizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Ubicacion> ubicaciones;

}
