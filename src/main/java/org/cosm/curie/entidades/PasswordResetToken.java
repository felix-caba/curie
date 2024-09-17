package org.cosm.curie.entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
@Getter
@Setter

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // Getters y setters

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }


}