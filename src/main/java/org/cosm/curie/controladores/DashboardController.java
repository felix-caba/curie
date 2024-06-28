package org.cosm.curie.controladores;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.cosm.curie.DTO.UsuarioDTO;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.*;
import org.cosm.curie.servicios.UsuarioServicio;
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
    @Autowired
    private UsuarioControlador UsuarioControlador;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;



    @GetMapping("/")
    public String dashboard(Model model, Authentication authentication, HttpServletRequest request) {




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

        UsuarioDTO usuarioDTO = UsuarioControlador.getCurrentUser();


        int id = 0;



        if (usuarioDTO != null) {
            id = usuarioDTO.getId();
            model.addAttribute("nombreUsuario", usuarioDTO.getUsername());
        }




        passwordResetTokenRepository.deleteById(id);


        model.addAttribute("currentView", "general");

        return "dashboard";
    }

    @GetMapping("/general")
    public String showGeneral(Model model, Authentication authentication) {

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

        UsuarioDTO usuarioDTO = UsuarioControlador.getCurrentUser();

        if (usuarioDTO != null) {
            model.addAttribute("nombreUsuario", usuarioDTO.getUsername());
        }

        model.addAttribute("currentView", "general");



        return "dashboard";
    }

    @GetMapping("/producto")
    public String showProductos(Model model, Authentication authentication) {


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

        UsuarioDTO usuarioDTO = UsuarioControlador.getCurrentUser();

        if (usuarioDTO != null) {
            model.addAttribute("nombreUsuario", usuarioDTO.getUsername());
        }

        model.addAttribute("currentView", "producto");

        return "dashboard";

    }

    @GetMapping("/controlCuentas")
    public String showControlCuentas(Model model, Authentication authentication) {

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

        UsuarioDTO usuarioDTO = UsuarioControlador.getCurrentUser();

        if (usuarioDTO != null) {
            model.addAttribute("nombreUsuario", usuarioDTO.getUsername());
        }

        model.addAttribute("currentView", "producto");

        return "dashboard";

    }










}