package org.cosm.curie.controladores;

import jakarta.transaction.Transactional;
import org.cosm.curie.entidades.PasswordResetToken;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.PasswordResetTokenRepository;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.cosm.curie.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassController {

    @Autowired
    private UsuarioServicio usuarioService;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {

        PasswordResetToken resetToken = usuarioService.getPasswordResetToken(token);
        if (resetToken == null) {
            model.addAttribute("error", "Token inválido");
        }
        model.addAttribute("token", token);
        return "reset-password";

    }

    @PostMapping("/reset-password")
    @Transactional
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      Model model) {

        PasswordResetToken resetToken = usuarioService.getPasswordResetToken(token);
        if (resetToken == null) {
            model.addAttribute("error", "Token inválido");
            return "reset-password"; // Retorna la vista con mensaje de error
        }


        Usuario usuario = resetToken.getUsuario();


        usuarioService.actualizarPassword(usuario, password);




        model.addAttribute("message", "Contraseña restablecida con éxito");
        return "login";

    }





}