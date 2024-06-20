package org.cosm.curie.servicios;


import org.cosm.curie.entidades.Localizacion;
import org.cosm.curie.repositorios.LocalizacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalizacionServicio {

    @Autowired
    private LocalizacionRepositorio localizacionRepository;

    public Localizacion obtenerLocalizacionPorNombre(String nombre) {
        return localizacionRepository.findByNombre(nombre);
    }


}
