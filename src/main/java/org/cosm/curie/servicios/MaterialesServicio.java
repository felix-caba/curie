package org.cosm.curie.servicios;

import org.cosm.curie.DTO.*;
import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.entidades.*;
import org.cosm.curie.repositorios.MaterialesRepository;
import org.cosm.curie.repositorios.ReactivosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialesServicio {

    @Autowired
    private MaterialesRepository materialesRepository;

    @Autowired
    private LocalizacionServicio localizacionServicio;


    @Autowired
    private UbicacionServicio ubicacionServicio;

    public List<MaterialDTO> obtenerTodosLosMateriales() {
        List<Materiales> reactivos = materialesRepository.findAll();
        return reactivos.stream().map(this::convertirAMaterialDTO).collect(Collectors.toList());
    }

    private MaterialDTO convertirAMaterialDTO(Materiales material) {

        MaterialDTO dto = new MaterialDTO();
        dto.setIdProducto(material.getIdProducto());
        dto.setNombre(material.getNombre());
        dto.setCantidad(material.getCantidad());
        dto.setStockMinimo(material.getStock_minimo());

        dto.setSubcategoria(material.getSubcategoria());
        dto.setNumero_serie(material.getNumero_serie());
        dto.setDescripcion(material.getDescripcion());

        dto.setFecha_compra(material.getFecha_compra());
        dto.setFechaCaducidad(material.getFechaCaducidad());

        dto.setNombreLocalizacion(material.getLocalizacion().getNombre());
        dto.setNombreUbicacion(material.getUbicacion().getNombre());


        return dto;
    }

    public List<MaterialDTO> obtenerMaterialesPaginados(int offsetM, int limitM) {

        Pageable pageable = PageRequest.of(offsetM / limitM, limitM);
        Page<Materiales> page = materialesRepository.findAll(pageable);
        List<Materiales> materiales = page.getContent();
        return materiales.stream().map(this::convertirAMaterialDTO).collect(Collectors.toList());

    }

    public MaterialDTO actualizarMaterial(Integer id, MaterialDTO materialDTO) {

        Materiales materialExistente = materialesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));

        materialExistente.setNombre(materialDTO.getNombre());
        materialExistente.setCantidad(materialDTO.getCantidad());
        materialExistente.setStock_minimo(materialDTO.getStockMinimo());

        materialExistente.setSubcategoria(materialDTO.getSubcategoria());
        materialExistente.setNumero_serie(materialDTO.getNumero_serie());
        materialExistente.setDescripcion(materialDTO.getDescripcion());

        materialExistente.setFecha_compra(materialDTO.getFecha_compra());
        materialExistente.setFechaCaducidad(materialDTO.getFechaCaducidad());

        // Actualizar localización si se proporciona
        if (materialDTO.getNombreLocalizacion() != null) {
            Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(materialDTO.getNombreLocalizacion());
            if (localizacion != null) {
                materialExistente.setLocalizacion(localizacion);
            } else {
                System.out.println("No se encontro la localizacion");
            }
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(materialDTO.getNombreUbicacion(), materialDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            materialExistente.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }

        Materiales materialActualizado = materialesRepository.save(materialExistente);

        return convertirAMaterialDTO(materialActualizado);


    }


    public MaterialDTO crearMaterial(MaterialDTO materialDTO) {

        Materiales materialNuevo = new Materiales();
        materialNuevo.setNombre(materialDTO.getNombre());
        materialNuevo.setCantidad(materialDTO.getCantidad());
        materialNuevo.setStock_minimo(materialDTO.getStockMinimo());


        Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(materialDTO.getNombreLocalizacion());
        if (localizacion != null) {
            materialNuevo.setLocalizacion(localizacion);
        } else {
            System.out.println("No se encontro la localizacion");
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(materialDTO.getNombreUbicacion(), materialDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            materialNuevo.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }


        materialesRepository.save(materialNuevo);
        return convertirAMaterialDTO(materialNuevo);

    }

    public List<MaterialDTO> obtenerMaterialesPorCantidadMenor(Integer cantidad) {

        List<Materiales> materiales = materialesRepository.findMaterialesByCantidadLessThan(cantidad);
        return materiales.stream().map(this::convertirAMaterialDTO).collect(Collectors.toList());

    }




    public void eliminarMaterial(Integer id) {
        materialesRepository.deleteById(id);
    }


    public MaterialDTO obtenerMaterialPorID(Integer id) {
        Materiales materiales = materialesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el materialNuevo con ID: " + id));
        return convertirAMaterialDTO(materiales);
    }

    public List<MaterialDTO> searchMateriales(String query) {
        List<Materiales> materiales = materialesRepository.searchMateriales(query);
        return materiales.stream().map(this::convertirAMaterialDTO).collect(Collectors.toList());
    }






}
