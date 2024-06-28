package org.cosm.curie.controladores;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.cosm.curie.servicios.CustomUserDetailsService;
import org.cosm.curie.validacion.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
public class AuthController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/login")
    public String showLoginForm(Model model) {

        model.addAttribute("user", new Usuario());



        return "login";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        String errorMessage = null;

        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

            if (ex != null) {

                errorMessage = "Usuario o contraseña incorrectos";

            }
        }
        model.addAttribute("errorMessage", errorMessage);

        return "login";

    }


    @PostMapping("/register")

    public ResponseEntity<String> registerUser(@ModelAttribute Usuario user) {

        if (usuarioRepositorio.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
        }
        user.setAdmin(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usuarioRepositorio.save(user);
        return ResponseEntity.ok("Usuario registrado con éxito");

    }

}
