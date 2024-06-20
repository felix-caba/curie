package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.Productos;
import org.cosm.curie.entidades.Productos_Auxiliares;
import org.cosm.curie.entidades.Reactivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface Productos_AuxiliaresRepository extends JpaRepository<Productos_Auxiliares, Integer> {

    @Query("SELECT r FROM Productos_Auxiliares r " +
            "WHERE LOWER(r.nombre) LIKE %:query% " +
            "OR LOWER(r.formato) LIKE %:query% " +
            "OR LOWER(CAST(r.cantidad AS string)) LIKE %:query% " +
            "OR CAST(r.stock_minimo AS string) LIKE %:query% " +
            "OR LOWER(r.localizacion.nombre) LIKE %:query% " +
            "OR LOWER(r.ubicacion.nombre) LIKE %:query% ")
    List<Productos_Auxiliares> searchProductosAuxiliares(@Param("query") String query);

    List<Productos_Auxiliares> findProductos_AuxiliaresByCantidadLessThan(Integer cantidad);




}
