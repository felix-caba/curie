package org.cosm.curie.servicios;

import jakarta.transaction.Transactional;
import org.cosm.curie.DTO.UsuarioDTO;
import org.cosm.curie.entidades.PasswordResetToken;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.PasswordResetTokenRepository;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UsuarioServicio {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;



    public List<UsuarioDTO> obtenerTodosLosUsuarios(){

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream().map(this::convertiraUsuarioDTO).collect(Collectors.toList());

    }

    private UsuarioDTO convertiraUsuarioDTO(Usuario usuario){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(usuario.getPassword());
        dto.setAdmin(usuario.getAdmin());

        if (usuario.getPfp() != null){
            dto.setPfp64(Base64.getEncoder().encodeToString(usuario.getPfp()));
        }



        return dto;
    }


    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO){

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioDTO.getUsername());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setPassword(usuarioDTO.getPassword());
        nuevoUsuario.setAdmin(usuarioDTO.getAdmin());
        nuevoUsuario.setPfp(Base64.getDecoder().decode(usuarioDTO.getPfp64()));

        usuarioRepositorio.save(nuevoUsuario);
        return convertiraUsuarioDTO(nuevoUsuario);


    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario con ID: " + id));

        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getPassword() != null) {
            usuarioExistente.setPassword(usuarioDTO.getPassword());
        }
        if (usuarioDTO.getAdmin() != null) {
            usuarioExistente.setAdmin(usuarioDTO.getAdmin());
        }
        if (usuarioDTO.getPfp64() != null) {
            usuarioExistente.setPfp(Base64.getDecoder().decode(usuarioDTO.getPfp64()));
        }

        usuarioRepositorio.save(usuarioExistente);
        return convertiraUsuarioDTO(usuarioExistente);
    }

    public void eliminarUsuario(Integer id){
        usuarioRepositorio.deleteById(id);
    }


    public UsuarioDTO obtenerUsuarioPorNombre(String username) {

        Usuario usuario = usuarioRepositorio.findByUsername(username).orElse(null);

        if (usuario == null) {
            return null;
        }
        return convertiraUsuarioDTO(usuario);

    }


    public Usuario obtenerPorID(Integer id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        return (usuario);
    }




    public void actualizarPassword(Usuario usuario, String password) {

        usuario.setPassword(passwordEncoder.encode(password));

        usuarioRepositorio.save(usuario);


    }












}
