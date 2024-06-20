package org.cosm.curie.repositorios;

import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.entidades.Reactivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface ReactivosRepository extends JpaRepository<Reactivos, Integer> {

    @Query("SELECT r FROM Reactivos r " +
            "WHERE LOWER(r.nombre) LIKE %:query% " +
            "OR LOWER(r.formato) LIKE %:query% " +
            "OR LOWER(CAST(r.cantidad AS string)) LIKE %:query% " +
            "OR CAST(r.stock_minimo AS string) LIKE %:query% " +
            "OR LOWER(r.gradoPureza) LIKE %:query% " +
            "OR LOWER(r.localizacion.nombre) LIKE %:query% " +
            "OR LOWER(r.ubicacion.nombre) LIKE %:query% ")


    List<Reactivos> searchReactivos(@Param("query") String query);

    List<Reactivos> findReactivosByCantidadLessThan(Integer cantidad);

    List<Reactivos> findReactivosByFechaCaducidadLessThan(Date fechaCaducidad);

}