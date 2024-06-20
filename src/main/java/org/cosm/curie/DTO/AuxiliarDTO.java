package org.cosm.curie.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class AuxiliarDTO {

    private Integer idProducto;
    private String nombre;
    private Integer cantidad;
    private Integer stockMinimo;
    private String formato;
    private String nombreLocalizacion;
    private String nombreUbicacion;

}
