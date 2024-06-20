package org.cosm.curie.servicios;

import org.cosm.curie.entidades.Riesgos;
import org.cosm.curie.repositorios.RiesgosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiesgoServicio {

    @Autowired
    private RiesgosRepository riesgosRepository;

    public Riesgos obtenerRiesgoPorNombre(String descripcion) {
        return riesgosRepository.findByDescripcion(descripcion);
    }

    public Riesgos obtenerRiesgoPorId(Integer id) {
        return riesgosRepository.findById(id).orElse(null);
    }

    public List<Riesgos> obtenerTodosLosRiesgos() {
        return riesgosRepository.findAll();
    }



}
