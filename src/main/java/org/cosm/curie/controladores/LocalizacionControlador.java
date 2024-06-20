package org.cosm.curie.controladores;

import org.cosm.curie.entidades.Localizacion;
import org.cosm.curie.repositorios.LocalizacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/localizacion/")
public class LocalizacionControlador {


    @Autowired
    private LocalizacionRepositorio localizacionRepositorio;


    @GetMapping
    public List<Localizacion> getAllLocalizaciones() {
        return localizacionRepositorio.findAll();
    }


    @PostMapping
    public Localizacion createLocalizacion(@RequestBody Localizacion localizacion) {
        return localizacionRepositorio.save(localizacion);
    }


    @GetMapping("/{id}")
    public Localizacion getLocalizacionById(@PathVariable Integer id) {
        return localizacionRepositorio.findById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public Localizacion updateLocalizacion(@PathVariable Integer id, @RequestBody Localizacion localizacionDetails) {
        Localizacion localizacion = localizacionRepositorio.findById(id).orElse(null);
        if (localizacion != null) {
            localizacion.setNombre(localizacionDetails.getNombre());
            return localizacionRepositorio.save(localizacion);
        }
        return null;
    }


    @DeleteMapping("/{id}")
    public void deleteLocalizacion(@PathVariable Integer id) {
        localizacionRepositorio.deleteById(id);
    }


}
