package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.Ubicacion;
import org.cosm.curie.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

}