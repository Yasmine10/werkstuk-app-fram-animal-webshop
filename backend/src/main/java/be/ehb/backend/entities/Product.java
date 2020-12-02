package be.ehb.backend.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String brand;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    private String image;

    @OneToOne
    @JoinColumn
    private Category category;

}
