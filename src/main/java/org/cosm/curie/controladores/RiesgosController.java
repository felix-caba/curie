package org.cosm.curie.controladores;

import org.cosm.curie.entidades.Localizacion;
import org.cosm.curie.entidades.Riesgos;
import org.cosm.curie.repositorios.LocalizacionRepositorio;
import org.cosm.curie.repositorios.RiesgosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/riesgos")
public class RiesgosController {

    @Autowired
    private RiesgosRepository riesgosRepository;

    @GetMapping
    public List<Riesgos> getAllRiesgos() {
        return riesgosRepository.findAll();
    }





}
