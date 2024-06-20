package org.cosm.curie.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Riesgos {
    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRiesgo;
    private String descripcion;

    @ManyToMany (mappedBy = "riesgosSet")
    @JsonBackReference
    Set<Reactivos> reactivosSet;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=16777216)
    private byte[] imagen;






}
