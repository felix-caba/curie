package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.Riesgos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiesgosRepository extends JpaRepository<Riesgos, Integer> {
    Riesgos findByDescripcion(String descripcion);
    Riesgos findByIdRiesgo(Integer idRiesgo);

}