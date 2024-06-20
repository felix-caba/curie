package org.cosm.curie.entidades;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Productos_Auxiliares extends Productos{

    private String formato;

}
