package org.cosm.curie.controladores;

import org.cosm.curie.DTO.AuxiliarDTO;
import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.servicios.AuxiliarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auxiliares")
public class AuxiliarControlador {

    @Autowired
    private AuxiliarServicio auxiliarService;


    @GetMapping("/cantidadMenor/{cantidad}")
    public ResponseEntity<List<AuxiliarDTO>> getReactivosPorCantidad(@PathVariable int cantidad) {
        List<AuxiliarDTO> materiales = auxiliarService.obtenerAuxiliaresPorCantidadMenor(cantidad);
        return ResponseEntity.ok(materiales);
    }


    @GetMapping("/all")
    public List<AuxiliarDTO> obtenerTodosLosReactivos() {
        return auxiliarService.obtenerTodosLosAuxiliares();
    }

    @GetMapping("/paginados")
    public List<AuxiliarDTO> obtenerReactivosPaginados(

            @RequestParam(defaultValue = "0") int offsetA,
            @RequestParam(defaultValue = "15") int limitA) {

        return auxiliarService.obtenerAuxiliaresPaginados(offsetA, limitA);

    }


    @GetMapping("/search")
    public List<AuxiliarDTO> searchAuxiliares(@RequestParam String query) {
        return auxiliarService.searchAuxiliares(query);
    }


    @PutMapping("/{id}")
    public AuxiliarDTO updateAuxiliar(@PathVariable Integer id, @RequestBody AuxiliarDTO auxiliarDTO) {
        return auxiliarService.actualizarAuxiliar(id, auxiliarDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuxiliar(@PathVariable Integer id) {
        auxiliarService.eliminarAuxiliar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public AuxiliarDTO obtenerAuxiliarPorId(@PathVariable Integer id) {
        return auxiliarService.obtenerAuxiliarPorId(id);
    }


    @PostMapping("/new")
    public ResponseEntity<AuxiliarDTO> crearReactivo(@RequestBody AuxiliarDTO auxiliarDTO) {
        AuxiliarDTO auxiliar = auxiliarService.crearAuxiliar(auxiliarDTO);
        return new ResponseEntity<>(auxiliar, HttpStatus.CREATED);
    }



}
