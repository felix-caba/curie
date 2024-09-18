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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Usuario user = userRepository.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Email not found");
            return "redirect:/forgot-password";
        } else {
            String token = UUID.randomUUID().toString();
            passwordResetService.createPasswordResetTokenForUser(user, token);
            passwordResetService.sendPasswordResetEmail(email, token);
            redirectAttributes.addFlashAttribute("sucessMessage", "Password reset email sent");
            return "redirect:/login";

        }

    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {

        String result = passwordResetService.validatePasswordResetToken(token);

        if (!"valid".equals(result)) {

            redirectAttributes.addFlashAttribute("errorMessage", "Ha expirado su Token...");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("token", token);
        return "reset-password";

    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                RedirectAttributes redirectAttributes) {
        String result = passwordResetService.validatePasswordResetToken(token);
        if (!"valid".equals(result)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ha expirado su Token...");
            return "error";
        }

        Usuario user = passwordResetService.getUserByPasswordResetToken(token);
        passwordResetService.changeUserPassword(user, password);
        passwordResetService.deletePasswordResetToken(token);

        redirectAttributes.addFlashAttribute("successMessage", "Contraseña restablecida con éxito");
        return "redirect:/login";

    }
}