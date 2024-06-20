package org.cosm.curie.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer admin;
    private String pfp64;

}
