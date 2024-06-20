package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.Materiales;
import org.cosm.curie.entidades.Productos;
import org.cosm.curie.entidades.Reactivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialesRepository extends JpaRepository<Materiales, Integer> {


    @Query("SELECT r FROM Materiales r " +
            "WHERE LOWER(r.nombre) LIKE %:query% " +
            "OR LOWER(CAST(r.cantidad AS string)) LIKE %:query% " +
            "OR CAST(r.stock_minimo AS string) LIKE %:query% " +
            "OR LOWER(r.localizacion.nombre) LIKE %:query% " +
            "OR LOWER(r.ubicacion.nombre) LIKE %:query% ")
    List<Materiales> searchMateriales(@Param("query") String query);

    List<Materiales> findMaterialesByCantidadLessThan(Integer cantidad);

}
