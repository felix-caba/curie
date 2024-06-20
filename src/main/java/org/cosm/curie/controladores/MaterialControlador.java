package org.cosm.curie.controladores;

import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.entidades.Localizacion;
import org.cosm.curie.entidades.Materiales;
import org.cosm.curie.entidades.Productos_Auxiliares;
import org.cosm.curie.entidades.Ubicacion;
import org.cosm.curie.repositorios.MaterialesRepository;
import org.cosm.curie.servicios.AuxiliarServicio;
import org.cosm.curie.servicios.LocalizacionServicio;
import org.cosm.curie.servicios.MaterialesServicio;
import org.cosm.curie.servicios.UbicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materiales")
public class MaterialControlador {

    @Autowired
    private MaterialesServicio materialesServicio;


    @GetMapping("/search")
    public List<MaterialDTO> searchMateriales(@RequestParam String query) {
        return materialesServicio.searchMateriales(query);
    }

    @GetMapping("/cantidadMenor/{cantidad}")
    public ResponseEntity<List<MaterialDTO>> getMaterialPorCantidad(@PathVariable int cantidad) {
        List<MaterialDTO> materiales = materialesServicio.obtenerMaterialesPorCantidadMenor(cantidad);
        return ResponseEntity.ok(materiales);
    }


    @GetMapping("/all")

    public List<MaterialDTO> obtenerTodosLosMateriales() {
        return materialesServicio.obtenerTodosLosMateriales();
    }

    @GetMapping("/paginados")
    public List<MaterialDTO> obtenerMaterialesPaginados(
            @RequestParam(defaultValue = "0") int offsetM,
            @RequestParam(defaultValue = "15") int limitM) {
        return materialesServicio.obtenerMaterialesPaginados(offsetM, limitM);

    }

    @PutMapping("/{id}")
    public MaterialDTO updateAuxiliar(@PathVariable Integer id, @RequestBody MaterialDTO materialDTO) {
        return materialesServicio.actualizarMaterial(id, materialDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuxiliar(@PathVariable Integer id) {
        materialesServicio.eliminarMaterial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public MaterialDTO obtenerAuxiliarPorId(@PathVariable Integer id) {
        return materialesServicio.obtenerMaterialPorID(id);
    }


    @PostMapping("/new")
    public ResponseEntity<MaterialDTO> crearReactivo(@RequestBody MaterialDTO materialDTO) {
        MaterialDTO material = materialesServicio.crearMaterial(materialDTO);
        return new ResponseEntity<>(material, HttpStatus.CREATED);
    }












}