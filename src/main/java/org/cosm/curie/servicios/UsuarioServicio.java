package org.cosm.curie.servicios;

import org.cosm.curie.DTO.UsuarioDTO;
import org.cosm.curie.entidades.Reactivos;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio {


    @Autowired
    UsuarioRepositorio usuarioRepositorio;

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
        dto.setPfp64(Base64.getEncoder().encodeToString(usuario.getPfp()));
        return dto;
    }

    public UsuarioDTO obtenerUsuarioPorId(Integer id){
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if(usuario == null){
            return null;
        }
        return convertiraUsuarioDTO(usuario);
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

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO){

        Usuario usuarioExistente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));

        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setPassword(usuarioDTO.getPassword());
        usuarioExistente.setAdmin(usuarioDTO.getAdmin());
        usuarioExistente.setPfp(Base64.getDecoder().decode(usuarioDTO.getPfp64()));

        usuarioRepositorio.save(usuarioExistente);
        return convertiraUsuarioDTO(usuarioExistente);

    }

    public void eliminarUsuario(Integer id){
        usuarioRepositorio.deleteById(id);
    }






}