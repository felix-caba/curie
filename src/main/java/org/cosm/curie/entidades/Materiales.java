package org.cosm.curie.entidades;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
public class Materiales extends Productos{

    private String subcategoria;
    private String numero_serie;
    private String descripcion;
    private Date fecha_compra;
    private Date fechaCaducidad;

}
