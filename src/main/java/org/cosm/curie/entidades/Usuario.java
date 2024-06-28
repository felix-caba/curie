package org.cosm.curie.entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer admin;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=16777216)
    private byte[] pfp;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PasswordResetToken passwordResetToken;


}
