package org.cosm.curie.controladores;

import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.DTO.UbicacionDTO;
import org.cosm.curie.entidades.Ubicacion;
import org.cosm.curie.repositorios.LocalizacionRepositorio;
import org.cosm.curie.repositorios.UbicacionRepositorio;
import org.cosm.curie.servicios.UbicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/ubicacion/")
public class UbicacionControlador {

    @Autowired
    private UbicacionRepositorio ubicacionRepositorio;

    @Autowired
    private UbicacionServicio ubicacionServicio;

    @GetMapping("/getFrom/{id}")
    public List<Ubicacion> getUbicacionesByLocalizacionId(@PathVariable Integer id) {
        return ubicacionRepositorio.findByLocalizacionIdLocalizacion(id);
    }

    @GetMapping("/getFromNombre/{nombre}")
    public List<Ubicacion> getUbicacionesByLocalizacionNombre(@PathVariable String nombre) {
        return ubicacionRepositorio.findByLocalizacionNombre(nombre);
    }

    @GetMapping
    public List<Ubicacion> getAllUbicaciones() {
        return ubicacionRepositorio.findAll();
    }

    @PostMapping
    public Ubicacion createUbicacion(@RequestBody Ubicacion ubicacion) {
        return ubicacionRepositorio.save(ubicacion);
    }

    @GetMapping("/{id}")
    public UbicacionDTO getUbicacionById(@PathVariable Integer id) {
        return ubicacionServicio.obtenerUbicacionPorId(id);
    }


    @DeleteMapping("/{id}")
    public void deleteUbicacion(@PathVariable Integer id) {
        ubicacionRepositorio.deleteById(id);
    }

    @GetMapping("/search")
    public List<UbicacionDTO> searchReactivos(@RequestParam String query) {
        return ubicacionServicio.searchUbicacion(query);
    }



    @PostMapping("/new")
    public ResponseEntity<UbicacionDTO> crearReactivo(@RequestBody UbicacionDTO ubicacionDTO) {
        UbicacionDTO nuevoReactivo = ubicacionServicio.crearUbicacion(ubicacionDTO);
        return new ResponseEntity<>(nuevoReactivo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public UbicacionDTO updateReactivo(@PathVariable Integer id, @RequestBody UbicacionDTO ubicacionDTO) {
        return ubicacionServicio.actualizarUbicacion(id, ubicacionDTO);
    }


    @GetMapping("/paginados")
    public List<UbicacionDTO> obtenerUbicacionesPaginadas(

            @RequestParam(defaultValue = "0") int offsetU,
            @RequestParam(defaultValue = "15") int limitU) {

        return ubicacionServicio.obtenerUbicacionesPaginados(offsetU, limitU);

    }

    @GetMapping("/all")
    public List<UbicacionDTO> obtenerUbicaciones() {
        return ubicacionServicio.obtenerTodasUbicaciones();
    }












}

