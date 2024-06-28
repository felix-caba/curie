package org.cosm.curie.entidades;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario usuario;

    private Date expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.expiryDate = calculateExpiryDate(24 * 60); // 24 hours
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetToken that)) return false;
        return Objects.equals(getId(), that.getId());
    }



    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}