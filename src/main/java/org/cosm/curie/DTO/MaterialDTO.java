package org.cosm.curie.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MaterialDTO {

    private Integer idProducto;
    private String nombre;
    private Integer cantidad;
    private Integer stockMinimo;

    private String subcategoria;
    private String numero_serie;
    private String descripcion;
    private Date fecha_compra;
    private Date fechaCaducidad;

    private String nombreLocalizacion;
    private String nombreUbicacion;





}
