package org.cosm.curie.repositorios;


import org.cosm.curie.entidades.Localizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacionRepositorio extends JpaRepository<Localizacion, Integer> {

    Localizacion findByNombre(String nombre);

}