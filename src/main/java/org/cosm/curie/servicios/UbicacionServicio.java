package org.cosm.curie.servicios;


import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.DTO.UbicacionDTO;
import org.cosm.curie.entidades.Reactivos;
import org.cosm.curie.entidades.Ubicacion;
import org.cosm.curie.repositorios.LocalizacionRepositorio;
import org.cosm.curie.repositorios.UbicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UbicacionServicio {

    @Autowired
    private UbicacionRepositorio ubicacionRepository;

    @Autowired
    private LocalizacionRepositorio localizacionRepositorio;

    public Optional<Ubicacion> obtenerUbicacionPorNombreYLocalizacion(String nombreUbicacion, String nombreLocalizacion) {
        return ubicacionRepository.findByNombreAndLocalizacionNombre(nombreUbicacion, nombreLocalizacion);
    }

    public List<UbicacionDTO> obtenerUbicacionesPaginados(int offsetU, int limitU) {

        // paginador HOLA
        Pageable pageable = PageRequest.of(offsetU / limitU, limitU);
        Page<Ubicacion> page = ubicacionRepository.findAll(pageable);
        List<Ubicacion> ubicacions = page.getContent();
        return ubicacions.stream().map(this::convertirAUbicacionDTO).collect(Collectors.toList());

    }

    private UbicacionDTO convertirAUbicacionDTO(Ubicacion ubicacion) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setIdUbicacion(ubicacion.getIdUbicacion());
        dto.setNombreUbicacion(ubicacion.getNombre());
        dto.setNombreLocalizacion(ubicacion.getLocalizacion().getNombre());
        return dto;
    }

    public List<UbicacionDTO> searchUbicacion(String query) {
        List<Ubicacion> ubicaciones = ubicacionRepository.searchUbicacion(query);
        return ubicaciones.stream().map(this::convertirAUbicacionDTO).collect(Collectors.toList());
    }

    public UbicacionDTO actualizarUbicacion(Integer id, UbicacionDTO ubicacionDTO) {


        Ubicacion ubicacion = ubicacionRepository.findById(id).orElse(null);
        if (ubicacion == null) {
            return null;
        }
        ubicacion.setNombre(ubicacionDTO.getNombreUbicacion());
        ubicacion.setLocalizacion(localizacionRepositorio.findByNombre(ubicacionDTO.getNombreLocalizacion()));
        ubicacionRepository.save(ubicacion);
        return convertirAUbicacionDTO(ubicacion);


    }

    public void eliminarUbicacion(Integer id) {
        ubicacionRepository.deleteById(id);
    }



    public UbicacionDTO crearUbicacion(UbicacionDTO ubicacionDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombre(ubicacionDTO.getNombreUbicacion());
        ubicacion.setLocalizacion(ubicacionRepository.findByNombre(ubicacionDTO.getNombreLocalizacion()).orElse(null).getLocalizacion());
        ubicacionRepository.save(ubicacion);
        return convertirAUbicacionDTO(ubicacion);
    }

    public List<UbicacionDTO> obtenerTodasUbicaciones() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        return ubicaciones.stream().map(this::convertirAUbicacionDTO).collect(Collectors.toList());
    }

    public UbicacionDTO obtenerUbicacionPorId(Integer id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id).orElse(null);
        if (ubicacion == null) {
            return null;
        }
        return convertirAUbicacionDTO(ubicacion);
    }









}
