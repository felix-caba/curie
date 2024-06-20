package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {

}