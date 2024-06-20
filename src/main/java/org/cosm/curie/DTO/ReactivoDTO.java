package org.cosm.curie.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;


@Setter
@Getter
public class ReactivoDTO {

    private Integer idProducto;
    private String nombre;
    private Integer cantidad;
    private Integer stockMinimo;
    private String formato;
    private String gradoPureza;
    private Date fechaCaducidad;
    private String nombreLocalizacion;
    private String nombreUbicacion;
    private List<RiesgoDTO> riesgos; // Nueva propiedad

}