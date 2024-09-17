package org.cosm.curie.controladores;

import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.cosm.curie.servicios.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UsuarioRepositorio userRepository;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        Usuario user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "forgot-password";
        }

        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);
        passwordResetService.sendPasswordResetEmail(email, token);

        model.addAttribute("message", "Password reset email sent");
        // changes the url to again login
        return "redirect:/login";


    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        String result = passwordResetService.validatePasswordResetToken(token);
        if (!"valid".equals(result)) {
            model.addAttribute("error", "Ha expirado su Token...");
            return "error";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                Model model) {
        String result = passwordResetService.validatePasswordResetToken(token);
        if (!"valid".equals(result)) {
            model.addAttribute("error", "Invalid or expired token");
            return "error";
        }

        Usuario user = passwordResetService.getUserByPasswordResetToken(token);
        passwordResetService.changeUserPassword(user, password);
        passwordResetService.deletePasswordResetToken(token);

        model.addAttribute("message", "Password has been reset successfully");
        return "redirect:/login";

    }
}