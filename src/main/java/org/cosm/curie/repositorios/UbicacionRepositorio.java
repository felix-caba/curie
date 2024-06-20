package org.cosm.curie.repositorios;
import org.cosm.curie.entidades.Localizacion;
import org.cosm.curie.entidades.Reactivos;
import org.cosm.curie.entidades.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepositorio extends JpaRepository<Ubicacion, Integer> {

        List<Ubicacion> findByLocalizacionIdLocalizacion(Integer idLocalizacion);

        List<Ubicacion> findByLocalizacionNombre(String nombre);

        Optional<Ubicacion> findByNombreAndLocalizacionNombre(String nombreUbicacion, String nombreLocalizacion);


        @Query("SELECT r FROM Ubicacion r " +
                "WHERE LOWER(r.nombre) LIKE %:query% " +
                "OR LOWER(r.localizacion.nombre) LIKE %:query% "
               )

        List<Ubicacion> searchUbicacion(@Param("query") String query);

        Optional<Ubicacion> findByNombre(String nombre);

}