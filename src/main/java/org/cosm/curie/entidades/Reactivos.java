package org.cosm.curie.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@Entity
public class Reactivos extends Productos {
    // Getters y Setters

    private String formato;
    private String gradoPureza;
    private Date fechaCaducidad;

    @ManyToMany
    @JoinTable(
            name = "producto_riesgo",
            joinColumns = @JoinColumn(name = "idProducto"),
            inverseJoinColumns = @JoinColumn(name = "idRiesgo")
    )

    @JsonManagedReference
    private Set<Riesgos> riesgosSet;


}