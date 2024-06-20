package org.cosm.curie.controladores;

import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.entidades.Reactivos;
import org.cosm.curie.repositorios.ReactivosRepository;
import org.cosm.curie.servicios.ReactivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/reactivos")
public class ReactivoControlador {

    @Autowired
    private ReactivoServicio reactivoService;

    @GetMapping("/all")
    public List<ReactivoDTO> obtenerTodosLosReactivos() {
        return reactivoService.obtenerTodosLosReactivos();
    }

    @GetMapping("/paginados")
    public List<ReactivoDTO> obtenerReactivosPaginados(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "15") int limit) {
        return reactivoService.obtenerReactivosPaginados(offset, limit);

    }

    @GetMapping("/search")
    public List<ReactivoDTO> searchReactivos(@RequestParam String query) {
        return reactivoService.searchReactivos(query);
    }


    @PutMapping("/{id}")
    public ReactivoDTO updateReactivo(@PathVariable Integer id, @RequestBody ReactivoDTO reactivoDTO) {
        return reactivoService.actualizarReactivo(id, reactivoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReactivo(@PathVariable Integer id) {
        reactivoService.eliminarReactivo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ReactivoDTO obtenerReactivoPorId(@PathVariable Integer id) {
        return reactivoService.obtenerReactivoPorId(id);
    }

    @PostMapping("/new")
    public ResponseEntity<ReactivoDTO> crearReactivo(@RequestBody ReactivoDTO reactivoDTO) {
        ReactivoDTO nuevoReactivo = reactivoService.crearReactivo(reactivoDTO);
        return new ResponseEntity<>(nuevoReactivo, HttpStatus.CREATED);
    }


    @GetMapping("/cantidadMenor/{cantidad}")
    public ResponseEntity<List<ReactivoDTO>> getReactivosPorCantidad(@PathVariable int cantidad) {
        List<ReactivoDTO> reactivos = reactivoService.obtenerReactivosPorCantidadMenor(cantidad);
        return ResponseEntity.ok(reactivos);
    }

    @GetMapping("/fechaCaducidadMenor/{fecha}")
    public ResponseEntity<List<ReactivoDTO>> getReactivosPorFechaCaducidad(@PathVariable Date fecha) {


        List<ReactivoDTO> reactivos = reactivoService.obtenerReactivosPorFechaCaducidadMenor(fecha);
        return ResponseEntity.ok(reactivos);


    }






}
