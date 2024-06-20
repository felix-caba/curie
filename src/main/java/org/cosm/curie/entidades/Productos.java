package org.cosm.curie.entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Productos {
    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private String nombre;
    private Integer cantidad;
    private Integer stock_minimo;

    @ManyToOne
    @JoinColumn(name = "idLocalizacion")
    private Localizacion localizacion;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    private Ubicacion ubicacion;






}