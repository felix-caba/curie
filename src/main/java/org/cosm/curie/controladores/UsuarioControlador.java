package org.cosm.curie.controladores;

import org.cosm.curie.DTO.UsuarioDTO;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JavaMailSender mailSender;

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
    public UsuarioDTO updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.actualizarUsuario(id, usuarioDTO);
    }

    @GetMapping("/current")
    public UsuarioDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            System.out.println("Usuario autenticado");

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String currentUserName = userDetails.getUsername();

            return usuarioServicio.obtenerUsuarioPorNombre(currentUserName);
        }

        return null;
    }

    @PutMapping("/updateCurrent")
    public UsuarioDTO updateCurrentUser(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String currentUserName = userDetails.getUsername();
            UsuarioDTO currentUsuarioDTO = usuarioServicio.obtenerUsuarioPorNombre(currentUserName);

            if (currentUsuarioDTO != null) {
                UsuarioDTO updatedUsuarioDTO = usuarioServicio.actualizarUsuario(currentUsuarioDTO.getId(), usuarioDTO);

                // Crear un nuevo objeto Authentication actualizado
                UserDetails updatedUserDetails = new org.springframework.security.core.userdetails.User(
                        updatedUsuarioDTO.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedUserDetails, authentication.getCredentials(), authentication.getAuthorities());

                // Actualizar el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                return updatedUsuarioDTO;
            }
        }

        return null;
    }


    @GetMapping("/allExceptCurrent")
    public ArrayList<UsuarioDTO> obtenerTodosLosUsuariosExceptoElActual() {

        ArrayList<UsuarioDTO> usuarios = (ArrayList<UsuarioDTO>) usuarioServicio.obtenerTodosLosUsuarios();
        usuarios.removeIf(usuario -> usuario.getUsername().equals(getCurrentUser().getUsername()));

        return usuarios;

    }




    private void sendPasswordResetEmail(String email, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Restablecer Contraseña");
        message.setText("Para restablecer su contraseña, haga clic en el siguiente enlace: " + resetUrl);
        mailSender.send(message);
    }




}
