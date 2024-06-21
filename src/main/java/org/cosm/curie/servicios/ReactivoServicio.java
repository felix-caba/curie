package org.cosm.curie.servicios;

import org.cosm.curie.DTO.ReactivoDTO;
import org.cosm.curie.DTO.RiesgoDTO;
import org.cosm.curie.entidades.*;
import org.cosm.curie.repositorios.ReactivosRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReactivoServicio {

    @Autowired
    private ReactivosRepository reactivoRepository;
    @Autowired
    private LocalizacionServicio localizacionServicio;
    @Autowired
    private UbicacionServicio ubicacionServicio;
    @Autowired
    private RiesgoServicio riesgosServicio;


    public List<ReactivoDTO> obtenerTodosLosReactivos() {

        List<Reactivos> reactivos = reactivoRepository.findAll();
        return reactivos.stream().map(this::convertirAReactivoDTO).collect(Collectors.toList());

    }

    private ReactivoDTO convertirAReactivoDTO(Reactivos reactivo) {
        ReactivoDTO dto = new ReactivoDTO();
        dto.setIdProducto(reactivo.getIdProducto());
        dto.setNombre(reactivo.getNombre());
        dto.setCantidad(reactivo.getCantidad());
        dto.setStockMinimo(reactivo.getStock_minimo());
        dto.setFormato(reactivo.getFormato());
        dto.setGradoPureza(reactivo.getGradoPureza());
        dto.setFechaCaducidad(reactivo.getFechaCaducidad());
        dto.setNombreLocalizacion(reactivo.getLocalizacion().getNombre());
        dto.setNombreUbicacion(reactivo.getUbicacion().getNombre());

        List<RiesgoDTO> riesgos = reactivo.getRiesgosSet().stream().map(riesgo -> {
            RiesgoDTO riesgoDTO = new RiesgoDTO();
            riesgoDTO.setIdRiesgo(riesgo.getIdRiesgo());
            riesgoDTO.setDescripcion(riesgo.getDescripcion());
            if (riesgo.getImagen() != null) {
                riesgoDTO.setImagenBase64(Base64.getEncoder().encodeToString(riesgo.getImagen()));
            }
            return riesgoDTO;
        }).collect(Collectors.toList());

        dto.setRiesgos(riesgos);

        return dto;
    }

    public List<ReactivoDTO> obtenerReactivosPaginados(int offset, int limit) {

        // paginador HOLA
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Reactivos> page = reactivoRepository.findAll(pageable);
        List<Reactivos> reactivos = page.getContent();
        return reactivos.stream().map(this::convertirAReactivoDTO).collect(Collectors.toList());

    }
    public List<ReactivoDTO> searchReactivos(String query) {

        List<Reactivos> reactivos = reactivoRepository.searchReactivos(query);
        return reactivos.stream().map(this::convertirAReactivoDTO).collect(Collectors.toList());



    }

    public ReactivoDTO crearReactivo(ReactivoDTO reactivoDTO) {
        Reactivos nuevoReactivo = new Reactivos();
        nuevoReactivo.setNombre(reactivoDTO.getNombre());
        nuevoReactivo.setCantidad(reactivoDTO.getCantidad());

        nuevoReactivo.setStock_minimo(reactivoDTO.getStockMinimo());
        nuevoReactivo.setFormato(reactivoDTO.getFormato());
        nuevoReactivo.setGradoPureza(reactivoDTO.getGradoPureza());
        nuevoReactivo.setFechaCaducidad(reactivoDTO.getFechaCaducidad());

        Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(reactivoDTO.getNombreLocalizacion());

        if (localizacion != null) {
            nuevoReactivo.setLocalizacion(localizacion);
        } else {
            System.out.println("No se encontro la localizacion");
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(reactivoDTO.getNombreUbicacion(), reactivoDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            nuevoReactivo.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }



        Set<Riesgos> riesgosSet = new HashSet<>();
        for (RiesgoDTO riesgoDTO : reactivoDTO.getRiesgos()) {
            Riesgos riesgo = riesgosServicio.obtenerRiesgoPorNombre(riesgoDTO.getDescripcion());
            if (riesgo != null) {
                riesgosSet.add(riesgo);
            } else {
                System.out.println("No se encontro el riesgo");
            }
        }
        nuevoReactivo.setRiesgosSet(riesgosSet);



        Reactivos reactivoGuardado = reactivoRepository.save(nuevoReactivo);
        return convertirAReactivoDTO(reactivoGuardado);

    }

    public ReactivoDTO obtenerReactivoPorId(Integer id) {
        Reactivos reactivo = reactivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));
        return convertirAReactivoDTO(reactivo);
    }



    public ReactivoDTO actualizarReactivo(Integer id, ReactivoDTO reactivoDTO) {

        Reactivos reactivoExistente = reactivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));

        reactivoExistente.setNombre(reactivoDTO.getNombre());
        reactivoExistente.setCantidad(reactivoDTO.getCantidad());
        reactivoExistente.setStock_minimo(reactivoDTO.getStockMinimo());
        reactivoExistente.setFormato(reactivoDTO.getFormato());
        reactivoExistente.setGradoPureza(reactivoDTO.getGradoPureza());
        reactivoExistente.setFechaCaducidad(reactivoDTO.getFechaCaducidad());


        // Actualizar localización si se proporciona
        if (reactivoDTO.getNombreLocalizacion() != null) {
            Localizacion localizacion = localizacionServicio.obtenerLocalizacionPorNombre(reactivoDTO.getNombreLocalizacion());
            if (localizacion != null) {
                reactivoExistente.setLocalizacion(localizacion);
            } else {
                // USUARIO ME HA DADO UN NULL
            }
        }

        Optional<Ubicacion> ubicacionOpt = ubicacionServicio.obtenerUbicacionPorNombreYLocalizacion(reactivoDTO.getNombreUbicacion(), reactivoDTO.getNombreLocalizacion());
        if (ubicacionOpt.isPresent()) {
            reactivoExistente.setUbicacion(ubicacionOpt.get());
        } else {
            System.out.println("No se encontró la ubicación");
        }



        // Convertir los RiesgoDTO a entidades Riesgos y actualizar el conjunto de riesgos
        Set<Riesgos> riesgosSet = null;

        if (reactivoDTO.getRiesgos() != null && !reactivoDTO.getRiesgos().isEmpty()) {
            riesgosSet = new HashSet<>();
            for (RiesgoDTO riesgoDTO : reactivoDTO.getRiesgos()) {
                Riesgos riesgo = riesgosServicio.obtenerRiesgoPorNombre(riesgoDTO.getDescripcion());
                if (riesgo != null) {
                    riesgosSet.add(riesgo);
                } else {
                    System.out.println("No se encontro el riesgo");
                }
            }
            reactivoExistente.setRiesgosSet(riesgosSet);
        } else {
            reactivoExistente.getRiesgosSet().clear();
        }


        // Guardar y devolver el reactivo actualizado
        Reactivos reactivoActualizado = reactivoRepository.save(reactivoExistente);
        return convertirAReactivoDTO(reactivoActualizado);

    }

    public void eliminarReactivo(Integer id) {
        Reactivos reactivo = reactivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reactivo con ID: " + id));
        reactivoRepository.delete(reactivo);
    }


    public List<ReactivoDTO> obtenerReactivosPorCantidadMenor(Integer cantidad) {

        List<Reactivos> reactivos = reactivoRepository.findReactivosByCantidadLessThan(cantidad);
        return reactivos.stream().map(this::convertirAReactivoDTO).collect(Collectors.toList());

    }

    public List<ReactivoDTO> obtenerReactivosPorFechaCaducidadMenor(Date fecha) {

        List<Reactivos> reactivos = reactivoRepository.findReactivosByFechaCaducidadLessThan(fecha);
        return reactivos.stream().map(this::convertirAReactivoDTO).collect(Collectors.toList());

    }


}
