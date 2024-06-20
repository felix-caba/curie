package org.cosm.curie.controladores;

import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.DTO.UsuarioDTO;
import org.cosm.curie.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;


    @GetMapping("/all")
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioServicio.obtenerTodosLosUsuarios();
    }

    @PostMapping("/new")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioServicio.crearUsuario(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioServicio.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public UsuarioDTO updateDTO(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.actualizarUsuario(id, usuarioDTO);
    }





}
