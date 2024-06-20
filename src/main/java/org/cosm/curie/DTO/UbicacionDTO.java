package org.cosm.curie.DTO;

import lombok.Getter;
import lombok.Setter;
import org.cosm.curie.entidades.Localizacion;

@Setter
@Getter
public class UbicacionDTO {

    private Integer idUbicacion;
    private Integer idLocalizacionFK;
    private String nombreLocalizacion;
    private String nombreUbicacion;




}
