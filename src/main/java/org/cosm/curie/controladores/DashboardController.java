package org.cosm.curie.controladores;

import org.cosm.curie.repositorios.MaterialesRepository;
import org.cosm.curie.repositorios.ProductosRepository;
import org.cosm.curie.repositorios.Productos_AuxiliaresRepository;
import org.cosm.curie.repositorios.ReactivosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    @Autowired
    private ReactivosRepository reactivoRepository;

    @Autowired
    private Productos_AuxiliaresRepository productoAuxiliarRepository;

    @Autowired
    private MaterialesRepository materialRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/")
    public String dashboard(Model model, Authentication authentication) {

        long countReactivos = reactivoRepository.count();
        long countProductosAuxiliares = productoAuxiliarRepository.count();
        long countMateriales = materialRepository.count();
        long countProductos = productosRepository.count();

        model.addAttribute("countReactivos", countReactivos);
        model.addAttribute("countProductosAuxiliares", countProductosAuxiliares);
        model.addAttribute("countMateriales", countMateriales);
        model.addAttribute("countProductos", countProductos);

        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "dashboard";

    }

    @GetMapping("/general")
    public String general() {

        return "general";

    }

    @GetMapping("/productos")
    public String productos() {
        return "productos";
    }

    @GetMapping("/impresion")
    public String impresion() {
        return "/impresion";
    }




}