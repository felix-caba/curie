package org.cosm.curie.servicios;

import org.cosm.curie.DTO.AuxiliarDTO;
import org.cosm.curie.DTO.MaterialDTO;
import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.DTO.RiesgoDTO;
import org.cosm.curie.entidades.*;
import org.cosm.curie.repositorios.Productos_AuxiliaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuxiliarServicio {
    @Autowired
    private LocalizacionServicio localizacionServicio;
    @Autowired
    private UbicacionServicio ubicacionServicio;
    @Autowired
    private Productos_AuxiliaresRepository auxiliaresRepository;

    public List<AuxiliarDTO> obtenerTodosLosAuxiliares() {
        List<Productos_Auxiliares> auxiliares = auxiliaresRepository.findAll();
        return auxiliares.stream().map(this::convertirAAuxiliarDTO).collect(Collectors.toList());
    }



    private AuxiliarDTO convertirAAuxiliarDTO(Productos_Auxiliares auxiliar) {
        AuxiliarDTO dto = new AuxiliarDTO();
        dto.setIdProducto(auxiliar.getIdProducto());
        dto.setNombre(auxiliar.getNombre());
        dto.setCantidad(auxiliar.getCantidad());
        dto.setStockMinimo(auxiliar.getStock_minimo());
        dto.setFormato(auxiliar.getFormato());
        dto.setNombreLocalizacion(auxiliar.getLocalizacion().getNombre());
        dto.setNombreUbicacion(auxiliar.getUbicacion().getNombre());
        return dto;
    }

    public List<AuxiliarDTO> obtenerAuxiliaresPaginados(int offsetA, int limitA) {
        // paginador HOLA
        Pageable pageable = PageRequest.of(offsetA / limitA, limitA);
        Page<Productos_Auxiliares> page = auxiliaresRepository.findAll(pageable);
        List<Productos_Auxiliares> productosAuxiliares = page.getContent();
        return productosAuxiliares.stream().map(this::convertirAAuxiliarDTO).collect(Collectors.toList());

    }

    public AuxiliarDTO crearAuxiliar(AuxiliarDTO auxiliarDTO) {
        Productos_Auxiliares auxiliar = new Productos_Auxiliares();
        auxiliar.setNombre(auxiliarDTO.getNombre());
        auxiliar.setCantidad(auxiliarDTO.getCantidad());
        auxiliar.setStock_minimo(auxiliarDTO.getStockMinimo());
        auxiliar.setFormato(auxiliarDTO.getFormato());

        Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(auxiliarDTO.getNombreLocalizacion());
        if (localizacion != null) {
            auxiliar.setLocalizacion(localizacion);
        } else {
            System.out.println("No se encontro la localizacion");
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(auxiliarDTO.getNombreUbicacion(), auxiliarDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            auxiliar.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }


        auxiliaresRepository.save(auxiliar);
        return convertirAAuxiliarDTO(auxiliar);

    }

    public AuxiliarDTO actualizarAuxiliar(Integer id, AuxiliarDTO auxiliarDTO) {

        Productos_Auxiliares auxiliarExistente = auxiliaresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));

        auxiliarExistente.setNombre(auxiliarDTO.getNombre());
        auxiliarExistente.setCantidad(auxiliarDTO.getCantidad());
        auxiliarExistente.setStock_minimo(auxiliarDTO.getStockMinimo());
        auxiliarExistente.setFormato(auxiliarDTO.getFormato());

        // Actualizar localización si se proporciona
        if (auxiliarDTO.getNombreLocalizacion() != null) {
            Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(auxiliarDTO.getNombreLocalizacion());
            if (localizacion != null) {
                auxiliarExistente.setLocalizacion(localizacion);
            } else {
                System.out.println("No se encontro la localizacion");
            }
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(auxiliarDTO.getNombreUbicacion(), auxiliarDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            auxiliarExistente.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }

        Productos_Auxiliares auxiliarActualizado = auxiliaresRepository.save(auxiliarExistente);
        return convertirAAuxiliarDTO(auxiliarActualizado);

    }

    public void eliminarAuxiliar(Integer id) {
        auxiliaresRepository.deleteById(id);
    }


    public AuxiliarDTO obtenerAuxiliarPorId(Integer id) {
        Productos_Auxiliares auxiliar = auxiliaresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el auxiliar con ID: " + id));
        return convertirAAuxiliarDTO(auxiliar);
    }

    public List<AuxiliarDTO> searchAuxiliares(String query) {
        List<Productos_Auxiliares> auxiliares = auxiliaresRepository.searchProductosAuxiliares(query);
        return auxiliares.stream().map(this::convertirAAuxiliarDTO).collect(Collectors.toList());
    }


    public List<AuxiliarDTO> obtenerAuxiliaresPorCantidadMenor(Integer cantidad) {

        List<Productos_Auxiliares> auxiliares = auxiliaresRepository.findProductos_AuxiliaresByCantidadLessThan(cantidad);
        return auxiliares.stream().map(this::convertirAAuxiliarDTO).collect(Collectors.toList());

    }



}
